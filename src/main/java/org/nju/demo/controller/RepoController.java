package org.nju.demo.controller;

import org.nju.demo.entity.*;
import org.nju.demo.pojo.vo.KnowledgeVO;
import org.nju.demo.pojo.vo.PatternVO;
import org.nju.demo.service.KnowledgeService;
import org.nju.demo.service.PatternService;
import org.nju.demo.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class RepoController {

    @Autowired
    private PatternService patternService;

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private HttpSession session;

    @RequestMapping("/view/repo")
    public String viewRepo(){
        return "pattern_list";
    }

    @RequestMapping("/view/knowledge/{patternId}")
    public String viewKnowledge(@PathVariable("patternId") int patternId){
        Pattern pattern = patternService.getPattern(patternId);
        session.setAttribute("pattern",pattern);
        return "knowledge_list";
    }

    @ResponseBody
    @RequestMapping("/patterns")
    public List<PatternVO> getPatterns(@RequestParam(value = "keyword",required = false) String keyword){
        if (keyword==null) keyword = "";
        List<Pattern> patternList = patternService.getPatternListByKeyword(keyword);
        List<PatternVO> patternVOList = new ArrayList<>();
        for(Pattern pattern:patternList){
            PatternVO patternVO = new PatternVO();
            patternVO.setPatternId(pattern.getId());
            patternVO.setPatternName(pattern.getPatternName());

            double likelihood;
            int trueNum = pattern.gettNum();
            int falseNum = pattern.getfNum();
            if (trueNum==0&&falseNum==0) likelihood = 0;
            else likelihood = trueNum*1.0/(trueNum+falseNum);

            patternVO.setLikelihood(likelihood);
            patternVOList.add(patternVO);
        }
        return patternVOList;
    }

    @ResponseBody
    @RequestMapping("/knowledgeVOs")
    public List<KnowledgeVO> getKnowledgeVOs(){
        Pattern pattern = (Pattern) session.getAttribute("pattern");
        List<Knowledge> knowledgeList = knowledgeService.getKnowledgeList(pattern.getId());
        List<KnowledgeVO> knowledgeVOList = new ArrayList<>();
        for(Knowledge knowledge : knowledgeList){
            KnowledgeVO knowledgeVO = new KnowledgeVO();
            knowledgeVO.setKnowledgeId(knowledge.getKnowledgeId());
            knowledgeVO.setKnowledgeName(knowledge.getKnowledgeName());
            knowledgeVO.setPatternName(pattern.getPatternName());
            knowledgeVO.setCreateTime(knowledge.getCreateTime());
            knowledgeVOList.add(knowledgeVO);
        }
        return knowledgeVOList;
    }

    @ResponseBody
    @RequestMapping("/knowledge/{id}")
    public Knowledge getKnowLedge(@PathVariable("id") String id){
        return knowledgeService.getKnowledge(id);
    }

    @RequestMapping("/addKnowledge")
    public String addKnowledge(@RequestParam("knowledgeName") String knowledgeName,
                               @RequestParam("content") String content){
        Pattern pattern = (Pattern) session.getAttribute("pattern");

        Knowledge knowledge = new Knowledge();
        knowledge.setKnowledgeId(StringUtil.generateStringId());
        knowledge.setKnowledgeName(knowledgeName);
        knowledge.setContent(content);
        knowledge.setCreateTime(new Date());
        knowledge.setPatternId(pattern.getId());

        knowledgeService.addKnowledge(knowledge);
        return "redirect:/view/knowledge/"+pattern.getId();
    }

    @ResponseBody
    @RequestMapping("/deleteKnowledge/{id}")
    public int deleteKnowledge(@PathVariable("id") String id){
        return knowledgeService.deleteKnowledge(id);
    }

}
