package org.nju.demo.controller;

import net.sf.json.JSONArray;
import org.nju.demo.config.Constants;
import org.nju.demo.entity.AVersion;
import org.nju.demo.entity.Violation;
import org.nju.demo.pojo.dto.PatternStatisticsDTO;
import org.nju.demo.pojo.vo.PatternStatisticsVO;
import org.nju.demo.service.StatisticsService;
import org.nju.demo.service.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class ChartController {

    @Autowired
    private HttpSession session;

    @Autowired
    private ViolationService violationService;

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/view/violations/charts")
    public String viewViolationCharts(){
        return "violation_charts";
    }

    @GetMapping("/view/violations/list")
    public String viewViolationList(){
        return "violation_list";
    }

    @ResponseBody
    @GetMapping("/violations/category")
    public String getViolationsType(){
        AVersion version = (AVersion) session.getAttribute("version");
        List<Violation> violationList = violationService.getViolationsByVersionId(version.getVersionId());
        int[] count = {0,0,0,0,0,0,0,0,0};
        for(Violation violation:violationList){
            String category = violation.getCategory();
            for(int i = 0; i < Constants.Type.findBugs.length; ++i){
                if (category.equals(Constants.Type.findBugs[i])){
                    count[i]++;
                    break;
                }
            }
        }
        List<Map> outputs = new ArrayList<>();
        for(int i=0;i<count.length;++i){
            Map output = new TreeMap();
            output.put("value",count[i]);
            output.put("name", Constants.Type.findBugs[i]);
            outputs.add(output);
        }
        JSONArray jsonArray = JSONArray.fromObject(outputs);
        return jsonArray.toString();
    }

    @ResponseBody
    @GetMapping("/violations/priority")
    public int[] getViolationsPriority(){
        AVersion version = (AVersion) session.getAttribute("version");
        List<Violation> violationList = violationService.getViolationsByVersionId(version.getVersionId());
        int[] count = {0,0,0,0};
        for(Violation violation:violationList){
            int p = violation.getPriority();
            if (p>=4) count[3]++;
            else count[p-1]++;
        }
        return count;
    }

    @ResponseBody
    @GetMapping("/violations/pattern")
    public List<PatternStatisticsVO> countViolationsByPattern(){
        AVersion version = (AVersion) session.getAttribute("version");
        List<PatternStatisticsVO> patternStatisticsVOList = new ArrayList<>();
        List<PatternStatisticsDTO> patternStatisticsDTOList = statisticsService.getPatternStatisticsByVersionId(version.getVersionId());
        for(PatternStatisticsDTO data : patternStatisticsDTOList){
            PatternStatisticsVO patternStatisticsVO = new PatternStatisticsVO();
            patternStatisticsVO.setPatternName(data.getPatternName());
            patternStatisticsVO.setPreViolationNum(data.getViolationNum());
            int nowNum = violationService.countTrueViolationByPattern(data.getVersionId(),data.getPatternName());
            patternStatisticsVO.setNowViolationNum(nowNum);
            patternStatisticsVOList.add(patternStatisticsVO);
        }
        return patternStatisticsVOList;
    }

}
