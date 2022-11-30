package org.nju.demo.utils;

import org.nju.demo.config.Constants;
import org.nju.demo.pojo.dto.Feature;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class DataUtil {

    public static void generateArff(List<Feature> featureList, File file){
        try{
            if(!file.exists()) file.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("@relation feature");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute slice_code_line numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute slice_branch_num numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute slice_violation_num numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute method_code_line numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute method_branch_num numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute method_cyc_complexity numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute method_token_num numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute method_param_num numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute method_violation_num numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute file_code_line numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute file_cyc_complexity numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute file_token_num numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute file_method_num numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute file_violation_num numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute type numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute category numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute priority numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute code_line numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute likelihood numeric");
            bufferedWriter.newLine();
            bufferedWriter.write("@attribute label {0,1}");
            bufferedWriter.newLine();
            bufferedWriter.write("@data");
            for (Feature feature : featureList){
                bufferedWriter.newLine();
                bufferedWriter.write(feature.getSliceFeature().getCodeLine()+",");
                bufferedWriter.write(feature.getSliceFeature().getBranchNum()+",");
                bufferedWriter.write(feature.getSliceFeature().getViolationNum()+",");
                bufferedWriter.write(feature.getMethodFeature().getCodeLine()+",");
                bufferedWriter.write(feature.getMethodFeature().getBranchNum()+",");
                bufferedWriter.write(feature.getMethodFeature().getCycNum()+",");
                bufferedWriter.write(feature.getMethodFeature().getTokenNum()+",");
                bufferedWriter.write(feature.getMethodFeature().getParamNum()+",");
                bufferedWriter.write(feature.getMethodFeature().getViolationNum()+",");
                bufferedWriter.write(feature.getFileFeature().getCodeLine()+",");
                bufferedWriter.write(feature.getFileFeature().getCycNum()+",");
                bufferedWriter.write(feature.getFileFeature().getTokenNum()+",");
                bufferedWriter.write(feature.getFileFeature().getMethodNum()+",");
                bufferedWriter.write(feature.getFileFeature().getViolationNum()+",");
                bufferedWriter.write(feature.getViolationFeature().getType()+",");
                bufferedWriter.write(feature.getViolationFeature().getCategory()+",");
                bufferedWriter.write(feature.getViolationFeature().getPriority()+",");
                bufferedWriter.write(feature.getViolationFeature().getCodeLine()+",");
                bufferedWriter.write(feature.getViolationFeature().getLikelihood()+",");
                bufferedWriter.write(String.valueOf(feature.getViolationFeature().getLabel()));
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void generateTrainArff(String username,List<Feature> featureList){
        File arffFile = new File(Constants.ROOT_PATH+"data/"+username+"/train.arff");
        generateArff(featureList,arffFile);
    }
    public static void generateTestArff(String username,List<Feature> featureList){
        File arffFile = new File(Constants.ROOT_PATH+"data/"+username+"/test.arff");
        generateArff(featureList,arffFile);
    }
}
