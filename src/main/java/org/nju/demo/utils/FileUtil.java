package org.nju.demo.utils;

import org.nju.demo.pojo.vo.ViolationFeature;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class FileUtil {

    private static String UPLOADED_FOLDER = System.getProperty("user.dir");
    public static void generateArff(List<ViolationFeature> violationFeatureList, File file){
        try{
            if(!file.exists()) file.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));



            bufferedWriter.flush();
            bufferedWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void generateTrainArff(String username,List<ViolationFeature> violationFeatureList){
        File arffFile = new File(UPLOADED_FOLDER+"/data/"+username+"/train.arff");
        generateArff(violationFeatureList,arffFile);
    }
    public static void generateTestArff(String username,List<ViolationFeature> violationFeatureList){
        File arffFile = new File(UPLOADED_FOLDER+"/data/"+username+"/test.arff");
        generateArff(violationFeatureList,arffFile);
    }
}
