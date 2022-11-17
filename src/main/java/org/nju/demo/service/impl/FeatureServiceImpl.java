package org.nju.demo.service.impl;

import org.nju.demo.config.Constants;
import org.nju.demo.dao.*;
import org.nju.demo.entity.*;
import org.nju.demo.pojo.dto.*;
import org.nju.demo.service.FeatureService;
import org.nju.demo.utils.CmdUtil;
import org.nju.demo.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private PatternMapper patternMapper;

    @Autowired
    private ViolationCodeMapper violationCodeMapper;

    @Autowired
    private ViolationMapper violationMapper;

    @Autowired
    private AVersionMapper versionMapper;

    @Autowired
    private ProjectMapper projectMapper;

    private Map<String,String> featureInfo = new HashMap<>();

    @Override
    public List<Feature> getFeatureList(List<Violation> violationList) throws IOException {
        List<Feature> featureList = new ArrayList<>();
        Map<String,String> hm = new HashMap<>();

        for (Violation violation : violationList){
            if (!hm.containsKey(violation.getVersionId())) {
                AVersion version = versionMapper.selectByPrimaryKey(violation.getVersionId());
                hm.put(version.getVersionId(),version.getJavaFilePath());
            }
            String javaFilePath = hm.get(violation.getVersionId());
            if (javaFilePath != null && !javaFilePath.equals("")){
                featureList.add(getFeature(javaFilePath,violation));
            }
        }
        featureInfo.clear();

        return featureList;
    }

    @Override
    public Feature getFeature(String javaFilePath,Violation violation) throws IOException {
        Feature feature = new Feature();

        String sourceFilePath = violation.getSourcePath();
        String className = sourceFilePath.substring(sourceFilePath.lastIndexOf('/')+1,sourceFilePath.lastIndexOf('.'));
        if (!featureInfo.containsKey(violation.getVersionId()+":"+className)){
            AVersion version = versionMapper.selectByPrimaryKey(violation.getVersionId());
            Project project = projectMapper.selectByPrimaryKey(version.getProjectId());
            String reportPath = CmdUtil.generateSourceFileReport(project.getProjectName(),version.getVersionName(),javaFilePath,violation.getSourcePath());
            List<String> featureInfoList = CodeUtil.getDataFromLizard(new FileInputStream(reportPath));
            int n = featureInfoList.size();
            for (int i=0;i<n-1;++i){
               String s = featureInfoList.get(i);
               String[] infos = s.split(":");
               String methodName = infos[1];
               featureInfo.put(version.getVersionId()+":"+className+":"+methodName,infos[2]+":"+infos[3]+":"+infos[4]+":"+infos[5]);
            }
            String fileInfo = featureInfoList.get(n-1);
            featureInfo.put(version.getVersionId()+":"+className,fileInfo.substring(fileInfo.indexOf(':')+1));
        }

        ViolationFeature violationFeature = getViolationFeature(violation);
        SliceFeature sliceFeature = getSliceFeature(violation);
        MethodFeature methodFeature = getMethodFeature(className,violation);
        FileFeature fileFeature = getFileFeature(className,violation);

        feature.setViolationFeature(violationFeature);
        feature.setSliceFeature(sliceFeature);
        feature.setMethodFeature(methodFeature);
        feature.setFileFeature(fileFeature);

        return feature;
    }

    @Override
    public ViolationFeature getViolationFeature(Violation violation) {
        ViolationFeature violationFeature = new ViolationFeature();

        PatternExample example = new PatternExample();
        PatternExample.Criteria criteria = example.createCriteria();
        criteria.andPatternNameEqualTo(violation.getType());
        Pattern pattern = patternMapper.selectByExample(example).get(0);

        int categoryId = 0;
        for(int i=0;i<Constants.Type.findBugs.length;++i){
            if (Constants.Type.findBugs[i].equals(violation.getCategory())){
                categoryId = i;
                break;
            }
        }
        violationFeature.setType(pattern.getId());
        violationFeature.setCategory(categoryId);
        violationFeature.setPriority(violation.getPriority());
        if (violation.getStartLine() != -1){
            violationFeature.setCodeLine(violation.getEndLine()-violation.getStartLine()+1);
        }else{
            violationFeature.setCodeLine(0);
        }

        if (pattern.gettNum() == 0){
            violationFeature.setLikelihood(0);
        }else{
            int trueNum = pattern.gettNum();
            int falseNum = pattern.getfNum();
            violationFeature.setLikelihood(trueNum*1.0/(trueNum+falseNum));
        }

        if(violation.getState() == Constants.ViolationState.TRUE){
            violationFeature.setLabel(1);
        }else{
            violationFeature.setLabel(0);
        }

        return violationFeature;
    }

    @Override
    public SliceFeature getSliceFeature(Violation violation) {
        SliceFeature sliceFeature = new SliceFeature();

        //Todo

        return sliceFeature;
    }

    @Override
    public MethodFeature getMethodFeature(String className,Violation violation) {
        MethodFeature methodFeature = new MethodFeature();
        ViolationCode violationCode = violationCodeMapper.selectCodeByViolationId(violation.getId());
        if (violationCode == null) return methodFeature;

        String key = violation.getVersionId()+":"+className+":"+violation.getMethodName();
        if (featureInfo.containsKey(key)){
            String value = featureInfo.get(key);
            String[] infos = value.split(":");
            methodFeature.setCodeLine(Integer.parseInt(infos[0]));
            methodFeature.setCycNum(Integer.parseInt(infos[1]));
            methodFeature.setTokenNum(Integer.parseInt(infos[2]));
            methodFeature.setParamNum(Integer.parseInt(infos[3]));
        }
        else {
            methodFeature.setCodeLine(violation.getEndLine()-violation.getStartLine()+1);
            methodFeature.setCycNum(CodeUtil.countCycNum(violationCode.getSnippet()));
        }

        methodFeature.setBranchNum(CodeUtil.countBranchNum(violationCode.getSnippet()));
        methodFeature.setViolationNum(countViolationNumByMethod(violation));

        return methodFeature;
    }

    @Override
    public FileFeature getFileFeature(String className,Violation violation) {
        FileFeature fileFeature = new FileFeature();

        String key = violation.getVersionId()+":"+className;
        String value = featureInfo.get(key);
        String[] infos = value.split(":");

        fileFeature.setCodeLine(Integer.parseInt(infos[0]));
        fileFeature.setCycNum(Double.parseDouble(infos[2]));
        fileFeature.setTokenNum((int) Double.parseDouble(infos[3])*Integer.parseInt(infos[4]));
        fileFeature.setMethodNum(Integer.parseInt(infos[4]));
        fileFeature.setViolationNum(countViolationNumByClass(violation));

        return fileFeature;
    }

    public int countViolationNumByMethod(Violation violation){
        ViolationExample example = new ViolationExample();
        ViolationExample.Criteria criteria = example.createCriteria();

        criteria.andVersionIdEqualTo(violation.getVersionId());
        List<Violation> violationList = violationMapper.selectByExample(example);

        int cnt = 0;
        for(Violation v : violationList){
            if (v.getMethodName().equals(violation.getMethodName()) && v.getClassName().equals(violation.getClassName())){
                cnt++;
            }
        }
        return cnt;
    }

    public int countViolationNumByClass(Violation violation){
        ViolationExample example = new ViolationExample();
        ViolationExample.Criteria criteria = example.createCriteria();

        criteria.andVersionIdEqualTo(violation.getVersionId());
        List<Violation> violationList = violationMapper.selectByExample(example);

        int cnt = 0;
        for(Violation v : violationList){
            if (v.getClassName().equals(violation.getClassName())){
                cnt++;
            }
        }
        return cnt;
    }
}
