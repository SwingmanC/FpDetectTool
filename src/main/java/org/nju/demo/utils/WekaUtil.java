package org.nju.demo.utils;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Random;

public class WekaUtil {

    public static double[] predict(File trainFile,File testFile){
        double[] res = null;
        try {
            J48 j48Classifier = new J48();

            ArffLoader arffLoader = new ArffLoader();
            arffLoader.setFile(trainFile);
            Instances trainInstances = arffLoader.getDataSet();

            arffLoader.setFile(testFile);
            Instances testInstances = arffLoader.getDataSet();

            testInstances.setClassIndex(testInstances.numAttributes()-1);
            double sum = testInstances.numInstances();
            res = new double[(int)sum];
            int right = 0;
            trainInstances.setClassIndex(trainInstances.numAttributes()-1);
            j48Classifier.buildClassifier(trainInstances);

            int tp = 0,fp = 0,fn = 0,tn = 0;
            for (int i=0;i<sum;++i){
                res[i] = j48Classifier.classifyInstance(testInstances.instance(i));
//                res[i] = j48Classifier.distributionForInstance(testInstances.instance(i))[1];
                double label = testInstances.instance(i).classValue();
                if (res[i] == 1 && label == 1) tp++;
                if (res[i] == 1 && label == 0) fp++;
                if (res[i] == 0 && label == 1) fn++;
                if (res[i] == 0 && label == 0) tn++;
//                System.out.println(res[i]);
            }
            System.out.println("J48 classification precision:"+((tp+tn)*1.0/sum));
            System.out.println("True positive:"+tp);
            System.out.println("False positive:"+fp);
            System.out.println("False negative:"+fn);
            System.out.println("True negative:"+tn);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

//    public static void main(String[] args) {
//        try {
//            J48 j48Classifier = new J48();
//
//            File trainFile = new File(UPLOADED_FOLDER+"/data/feature.arff");
//            ArffLoader arffLoader = new ArffLoader();
//            arffLoader.setFile(trainFile);
//            Instances trainInstances = arffLoader.getDataSet();
//
//            File testFile = new File(UPLOADED_FOLDER+"/data/test.arff");
//            arffLoader.setFile(testFile);
//            Instances testInstances = arffLoader.getDataSet();
//
//            testInstances.setClassIndex(testInstances.numAttributes()-1);
//            double sum = testInstances.numInstances();
//            int right = 0;
//            trainInstances.setClassIndex(trainInstances.numAttributes()-1);
//            j48Classifier.buildClassifier(trainInstances);
//
//            for (int i=0;i<sum;++i){
//                System.out.println(j48Classifier.classifyInstance(testInstances.instance(i)));
//                System.out.println(Arrays.toString(j48Classifier.distributionForInstance(testInstances.instance(i))));
//                if (j48Classifier.classifyInstance(testInstances.instance(i))==testInstances.instance(i).classValue()) right++;
//            }
//            System.out.println("J48 classification precision:"+(right*1.0/sum));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
