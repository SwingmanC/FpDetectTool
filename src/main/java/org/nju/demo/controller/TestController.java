package org.nju.demo.controller;

import org.nju.demo.config.Constants;
import org.nju.demo.entity.*;
import org.nju.demo.pojo.vo.ViolationClusterVO;
import org.nju.demo.pojo.vo.ViolationVO;
import org.nju.demo.service.FeatureService;
import org.nju.demo.service.PatternService;
import org.nju.demo.service.ViolationService;
import org.nju.demo.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static org.nju.demo.config.Constants.FIXED_LENGTH;
import static org.nju.demo.config.Constants.testProjectName;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private PatternService patternService;

    @Autowired
    private ViolationService violationService;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private HttpSession session;

    private List<ViolationVO> violationVOList = new ArrayList<>();

    private List<ViolationClusterVO> violationClusterVOList = new ArrayList<>();

    private List<ViolationClusterVO> resultList = new ArrayList<>();

    @ResponseBody
    @RequestMapping("/updatePattern")
    public int updatePattern(){
        List<Violation> violationList = violationService.getClassifiedViolations();
        for (Violation violation : violationList){
            String type = violation.getType();
            int state = violation.getState();
            Pattern pattern = patternService.getPatternByPatternName(type);
            if (pattern == null){
//                violationService.deleteViolation(violation.getId());
                pattern = new Pattern();
                pattern.setPatternName(type);
                pattern.setCategoryId(1);
                pattern.settNum(0);
                pattern.setfNum(0);
                patternService.addPattern(pattern);
//                continue;
            }
            if (state == Constants.ViolationState.FALSE){
                pattern.setfNum(pattern.getfNum()+1);
            }else{
                pattern.settNum(pattern.gettNum()+1);
            }
            patternService.updatePattern(pattern);
        }
        return 1;
    }

    @ResponseBody
    @RequestMapping("/extractCode")
    public int extractCode(){
        List<Violation> violationList = violationService.getClassifiedViolations();
        for (Violation violation : violationList){
            String sourcePath = violation.getSourcePath();
            int startLine = violation.getStartLine();
            int endLine = violation.getEndLine();
            try{
                InputStream inputStream = Files.newInputStream(Paths.get("/Users/sunchen/data/"+testProjectName+"/"+testProjectName+"-"+violation.getVersionId()+"/"+ sourcePath));
                String snippet = CodeUtil.readCodeFromData(inputStream,startLine,endLine);
                if (!snippet.equals("")){
                    ViolationCode violationCode = new ViolationCode();
                    violationCode.setViolationId(violation.getId());
                    violationCode.setSnippet(snippet);
                    violationService.addViolationCode(violationCode);
                }
            }catch (Exception e){
//                System.out.println(e);
            }
        }
        return 1;
    }

    @ResponseBody
    @RequestMapping("/violations")
    public List<ViolationVO> getViolations(@RequestParam(value = "priority",required = false) String strPriority,
                                           @RequestParam(value = "patternId",required = false) String strPatternId,
                                           @RequestParam(value = "state",required = false) String strState){
        List<Violation> violationList = null;
        int priority=0,state=-2;
        String type = "";
        if (strPriority !=null && !strPriority.equals("")){
            priority = Integer.parseInt(strPriority);
        }
        if (strState != null && !strState.equals("")){
            state = Integer.parseInt(strState);
        }
        if (strPatternId != null && !strPatternId.equals("")){
            Pattern p = patternService.getPattern(Integer.parseInt(strPatternId));
            type = p.getPatternName();
        }
        violationList = violationService.getViolationList("",priority,type,state);

        List<ViolationVO> violationVOList = new ArrayList<>();
        for(Violation violation : violationList){
            ViolationVO violationVO = new ViolationVO();
            Pattern pattern = patternService.getPatternByPatternName(violation.getType());
            if (pattern == null){
                continue;
            }
            violationVO.setId(violation.getId());
            violationVO.setType(violation.getType());
            violationVO.setSourcePath(violation.getSourcePath());
            violationVO.setCategory(violation.getCategory());
            violationVO.setClassName(violation.getClassName());
            violationVO.setMethodName(violation.getMethodName());
            violationVO.setPriority(violation.getPriority());
            violationVO.setStartLine(violation.getStartLine());
            violationVO.setEndLine(violation.getEndLine());

            int trueNum = pattern.gettNum();
            int falseNum = pattern.getfNum();

            if (trueNum == 0 && falseNum == 0){
                violationVO.setLikelihood(0);
                violationVO.setVariance(1);
            }
            else{
                long n = patternService.countByCategoryId(pattern.getCategoryId());
                double likelihood = trueNum*1.0/(trueNum+falseNum);
                double variance = likelihood*(1-likelihood)/n;

                violationVO.setLikelihood(likelihood);
                violationVO.setVariance(variance);
                violationVO.setState(violation.getState());
            }

            violationVOList.add(violationVO);
        }
        return violationVOList;
    }

    @ResponseBody
    @RequestMapping("/predict")
    public List<ViolationVO> predictViolation(){
        AUser user = (AUser) session.getAttribute("user");
        List<Violation> testDataList = getTestDataList(FIXED_LENGTH,violationService.getClassifiedViolations()) ;
        List<ViolationVO> violationVOList = new ArrayList<>();
        try {
            DataUtil.generateTestArff(user.getUsername(), featureService.getFeatureList(testDataList));
            File trainFile = new File(Constants.ROOT_PATH+"data/"+user.getUsername()+"/train.arff");
            File testFile = new File(Constants.ROOT_PATH+"data/"+user.getUsername()+"/test.arff");
            double[] res = WekaUtil.predict(trainFile,testFile);
            int index = 0;
            for (Violation violation : testDataList){
                ViolationVO violationVO = new ViolationVO();
                violationVO.setId(violation.getId());
                violationVO.setType(violation.getType());
                violationVO.setCategory(violation.getCategory());
                violationVO.setClassName(violation.getClassName());
                violationVO.setPriority(violation.getPriority());
                violationVO.setSourcePath(violation.getSourcePath());
                violationVO.setMethodName(violation.getMethodName());
                violationVO.setStartLine(violation.getStartLine());
                violationVO.setEndLine(violation.getEndLine());
                violationVO.setLikelihood(res[index]);
                if (res[index]<0.5) violationVO.setState(Constants.ViolationState.FALSE);
                else violationVO.setState(Constants.ViolationState.TRUE);
                index++;
                violationVOList.add(violationVO);
            }
            this.violationVOList.clear();
            this.violationVOList.addAll(violationVOList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return violationVOList;
    }

    @ResponseBody
    @RequestMapping("/cluster")
    public List<ViolationClusterVO> cluster(){
        List<ViolationClusterVO> violationList = new ArrayList<>();
        try {
            CmdUtil.callScriptForCluster();
            KMeans.readTable(KMeans.filePath);
            ArrayList<ArrayList<Float>> rlist = KMeans.randomList();
            KMeans.eudistance(rlist);
            KMeans.kmeans();
            for (ArrayList<Float> vector : KMeans.alist){
                ViolationClusterVO violationClusterVO = new ViolationClusterVO();
                float f = vector.get(0);
                int id = (int) f;
                Violation violation = violationService.getViolation(id);
                violationClusterVO.setId(violation.getId());
                violationClusterVO.setProjectName(testProjectName);
                violationClusterVO.setVersionName(violation.getVersionId());
                violationClusterVO.setType(violation.getType());
                String sourcePath = violation.getSourcePath();
                violationClusterVO.setFileName(sourcePath.substring(sourcePath.lastIndexOf('/')+1));
                violationClusterVO.setState(Constants.ViolationState.FALSE);
                violationList.add(violationClusterVO);
            }
            for (ArrayList<Float> vector : KMeans.blist){
                ViolationClusterVO violationClusterVO = new ViolationClusterVO();
                float f = vector.get(0);
                int id = (int) f;
                Violation violation = violationService.getViolation(id);
                violationClusterVO.setId(violation.getId());
                violationClusterVO.setProjectName(testProjectName);
                violationClusterVO.setVersionName(violation.getVersionId());
                violationClusterVO.setType(violation.getType());
                String sourcePath = violation.getSourcePath();
                violationClusterVO.setFileName(sourcePath.substring(sourcePath.lastIndexOf('/')+1));
                violationClusterVO.setState(Constants.ViolationState.TRUE);
                violationList.add(violationClusterVO);
            }
            violationList.sort(Comparator.comparingInt(ViolationClusterVO::getId));
            List<ViolationClusterVO> resultList = dealTestClusterData(violationList,30,234);
//            for (ViolationClusterVO v:resultList){
//                System.out.println(v.getState());
//            }
            this.violationClusterVOList.clear();
            this.resultList.clear();
            this.violationClusterVOList.addAll(violationList);
            this.resultList.addAll(resultList);
            computeCluster();
        }catch (Exception e){
            System.out.println("kmeans聚类失败:"+e.getMessage());
        }
        return violationList;
    }

    private List<Violation> getTestDataList(int fixedLength,List<Violation> violationList){
        List<Violation> testDataList = new ArrayList<>();
        int l = fixedLength/2,size = violationList.size();
        int r = (int) ((int)violationList.size()*0.3);

        for (int i=l;i<r;++i){
            testDataList.add(violationList.get(i));
        }
        System.out.println("The number of Test data is:"+testDataList.size());
        System.out.println("start index:"+l);
        System.out.println("end index:"+r);
        return testDataList;
    }

    private List<ViolationClusterVO> dealTestClusterData(List<ViolationClusterVO> violationList,int start,int end){
        List<ViolationClusterVO> resultList = new ArrayList<>();
        int i = 0,j = start;
        while(j<=end){
            ViolationClusterVO violationClusterVO = violationList.get(i);
            if (violationClusterVO.getId()==j){
                resultList.add(violationClusterVO);
                i++;
                j++;
            }
            else if (violationClusterVO.getId()>j){
                ViolationClusterVO res = new ViolationClusterVO();
                res.setId(j);
                res.setState(-1);
                resultList.add(res);
                j++;
            }
            else{
                i++;
            }
        }
        return resultList;
    }

    private void computeSummary(int start,int end){
        int tp = 0,fp = 0,fn = 0,tn = 0;
        List<Violation> violationList = violationService.getViolationByRange(start,end);
        for (int i=0;i<violationList.size();++i){
            ViolationVO violationVO = violationVOList.get(i);
            ViolationClusterVO violationClusterVO = resultList.get(i);
            Violation violation = violationList.get(i);
            int st1 = violationVO.getState(),st2 = violationClusterVO.getState(),label = violation.getState();
            if (st2 == -1){
                if (st1 == 1 && label == 1) tp++;
                if (st1 == 1 && label == 0) fp++;
                if (st1 == 0 && label == 1) fn++;
                if (st1 == 0 && label == 0) tn++;
            }
            else{
                if ((st1 == 1||st2 == 1) && label == 1) tp++;
                if ((st1 == 1&&st2 == 1) && label == 0) fp++;
                if ((st1 == 0&&st2 == 0) && label == 1) fn++;
                if ((st1 == 0||st2 == 0) && label == 0) tn++;
            }
        }
        System.out.println("-------Summary-------");
        System.out.println("True positive:"+tp);
        System.out.println("False positive:"+fp);
        System.out.println("False negative:"+fn);
        System.out.println("True negative:"+tn);
    }

    private void computeCluster(){
        int tp = 0,fp = 0,fn = 0,tn = 0;
        for (ViolationClusterVO violationClusterVO : violationClusterVOList){
            Violation violation = violationService.getViolation(violationClusterVO.getId());
            int result = violationClusterVO.getState(),label = violation.getState();
            if (result == 1 && label == 1) tp++;
            if (result == 1 && label == 0) fp++;
            if (result == 0 && label == 1) fn++;
            if (result == 0 && label == 0) tn++;
        }
        System.out.println("-------Cluster-------");
        System.out.println("True positive:"+tp);
        System.out.println("False positive:"+fp);
        System.out.println("False negative:"+fn);
        System.out.println("True negative:"+tn);
    }

    @ResponseBody
    @RequestMapping("/evaluation")
    public int evaluation(){
        computeSummary(30,234);
        return 1;
    }

}
