package org.nju.demo.other;

import com.csvreader.CsvReader;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import org.nju.demo.config.Constants;
import org.nju.demo.utils.joanacore.JoanaSlicer;
import org.nju.demo.utils.joanacore.datastructure.Func;
import org.nju.demo.utils.joanacore.datastructure.Location;
import org.nju.demo.utils.joanacore.exception.SlicerException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.nju.demo.other.Info.*;

public class CodeParser {

    public static String getLineCode(String codeFilePath, int startLine, int endLine,File outputFile) throws IOException {
        InputStream inputStream = new FileInputStream(codeFilePath);
        StringBuilder resultStringBuilder = new StringBuilder();
        if (startLine < 0) startLine = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int num = 0;
            while ((line = br.readLine()) != null) {
                num++;
                if (num >= startLine && num <= endLine) {
                    resultStringBuilder.append(line.trim()).append("\n");
                } else if (num < startLine) continue;
                else break;
            }
        }
        String code = resultStringBuilder.toString();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
        bufferedWriter.write(code);
        bufferedWriter.flush();
        bufferedWriter.close();
        return code;
    }

    public static String getTenLineCode(String codeFilePath, int startLine, int endLine,File outputFile) throws IOException {
        InputStream inputStream = new FileInputStream(codeFilePath);
        List<String> content = new ArrayList<>();
        content.add("#");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        }
        String[] codes = content.toArray(new String[0]);
        if (startLine == -1||startLine > codes.length) {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
            bufferedWriter.write("");
            bufferedWriter.flush();
            bufferedWriter.close();
            return "";
        }
        StringBuilder result = new StringBuilder();
        int left,right;
        int cnt = 1,index = 1;
        while(cnt < 10 && startLine-index > 0){
            String line = codes[startLine-index].trim();
            if (!line.startsWith("/*")&&!line.startsWith("*/")&&!line.startsWith("*")&&!line.startsWith("//")){
                cnt++;
            }
            index++;
        }
        left = startLine-index <= 0 ? 1 : startLine-index;
        cnt = 1;
        index = 1;
        while(cnt < 10 && endLine+index < codes.length){
            String line = codes[endLine+index].trim();
            if (!line.startsWith("/*")&&!line.startsWith("*/")&&!line.startsWith("*")&&!line.startsWith("//")){
                cnt++;
            }
            index++;
        }
        right = endLine+index == codes.length ? codes.length - 1 : endLine+index;
        for (int i = left;i<=right;++i){
            String line = codes[i].trim();
            if (!line.startsWith("/*")&&!line.startsWith("*/")&&!line.startsWith("*")&&!line.startsWith("//")){
                if (line.contains("//")){
                    String[] splitLine = line.split("//");
                    result.append(splitLine[0]).append("\n");
                }
                else {
                    result.append(line).append("\n");
                }
            }
        }
        String code = result.toString();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
        bufferedWriter.write(code);
        bufferedWriter.flush();
        bufferedWriter.close();
        return code;
    }

    public static int getSlice(String projectPath,String className,String method,String filePath,int startLine,int endLine,File outputFile){
        String targetPath = projectPath + "/target";
        File directory = new File(targetPath);
        if (!directory.exists()) return 0;
        File jarFile = null;
        for (File f : directory.listFiles()){
            String fileName = f.getName();
            if (fileName.endsWith("SNAPSHOT.jar")) {
                jarFile = f;
            }
        }
        if (jarFile == null) return 0;
        System.out.println("fileName: "+jarFile.getName());
        //处理className
        int dotLastIndex = className.lastIndexOf('.');
        className = className.substring(0,dotLastIndex);
        System.out.println("className: "+className);
        //处理methodName和signature
        String signature;
        int leftBracketIndex = method.indexOf('(');
        String methodName = method.substring(0,leftBracketIndex);
//        if (method.charAt(leftBracketIndex+1)!=')') {
//            signature = method.substring(leftBracketIndex);
//        }else{
//            signature = method.substring(leftBracketIndex+2);
//        }
        //处理filePath
        String[] filePaths = filePath.split("/");
        StringBuilder fileSb = new StringBuilder();
        for (String str : filePaths){
            if (str.equals("src")||str.equals("main")||str.equals("java")){
                continue;
            }
            else {
                fileSb.append(str).append("/");
            }
        }
        fileSb.deleteCharAt(fileSb.length()-1);
        signature = method.substring(leftBracketIndex);
        System.out.println("methodName: " + methodName);
        System.out.println("signature: " + signature);
        System.out.println("filePath: " + fileSb.toString());
        List<File> apps = new ArrayList<>();
        apps.add(jarFile);
        JoanaSlicer slicer = new JoanaSlicer();
        try {
            slicer.config(apps, null, null);
            List<Integer> lineList = slicer.computeSlice(new Func(className, methodName, signature),
                    new Location(fileSb.toString(), startLine, endLine));
//            System.out.println(lineList);
            int max = 0;
            List<Integer> res = new ArrayList<>();
            for (Integer i : lineList) {
                if (i == 0 || i == max) continue;
                if (i < max) break;
                max = i;
                res.add(i);
            }
//            StringBuilder sb = new StringBuilder();
//            String codeFilePath = projectPath + "/" + filePath;
//            int cnt = 0, index = 0;
//            InputStream inputStream = Files.newInputStream(Paths.get(codeFilePath));
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
//                String line;
//                while (index < res.size() && (line = br.readLine()) != null) {
//                    cnt++;
//                    if (cnt == res.get(index)) {
//                        sb.append(line).append("\n");
//                        index++;
//                    }
//                }
//            }
//            String code = sb.toString();
            String finalCode = SliceHandler.sliceFile(new File(projectPath+"/"+filePath),methodName,res);
//            System.out.println(finalCode);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
            bufferedWriter.write(finalCode);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (SlicerException e) {
            throw new RuntimeException(e);
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassHierarchyException e){
            e.printStackTrace();
        }
        return 1;
    }

    public static void main(String[] args) {
        Set<String> failSliceCommits = new HashSet<>();
        Set<Integer> failSliceWarnings = new HashSet<>();
        for (String projectName : projects){
            try{
                //创建输出目录
                String outputDirPath1 = RESULT_ROOT_PATH + "warning line numbers/" + projectName + "/";
                String outputDirPath2 = RESULT_ROOT_PATH + "ten line arroud the warning line numbers/" + projectName + "/";
                String outputDirPath3 = RESULT_ROOT_PATH + "warning slicing/" + projectName + "/";
//                File outputDir1 = new File(outputDirPath1);
//                File outputDir2 = new File(outputDirPath2);
//                File outputDir3 = new File(outputDirPath3);
//                outputDir1.mkdirs();
//                outputDir2.mkdirs();
//                outputDir3.mkdirs();
                CsvReader csvReader = new CsvReader(LABEL_ROOT_PATH + projectName + ".csv", ',', Charset.forName("utf-8"));
                csvReader.readHeaders();
                int index = 1;
                //数据集读取
                while (csvReader.readRecord()){
                    //创建输出txt文件
                    String outputFilePath1 = outputDirPath1 + "warning_" + index + ".txt";
                    String outputFilePath2 = outputDirPath2 + "warning_" + index + ".txt";
                    String outputFilePath3 = outputDirPath3 + "warning_" + index + ".txt";
//                    File outputFile1 = new File(outputFilePath1);
//                    File outputFile2 = new File(outputFilePath2);
                    File outputFile3 = new File(outputFilePath3);
//                    outputFile1.createNewFile();
//                    outputFile2.createNewFile();
//                    outputFile3.createNewFile();

                    String buggyCommitId = csvReader.get("buggy commit");
                    String filePath = csvReader.get("buggy path");
                    String fileName = filePath.substring(filePath.lastIndexOf('/')+1,filePath.lastIndexOf('.'));
                    String codeFilePath = CODE_ROOT_PATH + projectName + "_warnings/warning_" + index + "/" + buggyCommitId + "-" + fileName + ".txt";
                    int startLine = Integer.parseInt(csvReader.get("buggy start"));
                    int endLine = Integer.parseInt(csvReader.get("buggy end"));
                    String kind = csvReader.get("Kind");

                    File codeFile = new File(codeFilePath);
                    if (codeFile.exists()){
//                        String lineCode = getLineCode(codeFilePath,startLine,endLine,outputFile1);
//                        String tenLinesCode = getTenLineCode(codeFilePath,startLine,endLine,outputFile2);
                        if (kind.equals("Public Method")) {
                            String className = csvReader.get("Name");
                            String method = csvReader.get("method");
                            String dataName;
                            if (!projectName.equals("mavendp")) {
                                dataName = "commons-" + projectName;
                            }else {
                                dataName = "maven-dependency-plugin";
                            }
                            String projectPath = "/Users/sunchen/data/"+dataName+"/"+dataName+"-"+buggyCommitId;
                            try {
                                int res = getSlice(projectPath,className,method,filePath,startLine,endLine,outputFile3);
                                if (res == 0){
                                    failSliceCommits.add(buggyCommitId);
                                    failSliceWarnings.add(index);
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    index++;
                }
                StringBuilder failCommitStr = new StringBuilder();
                StringBuilder failWarningStr = new StringBuilder();
                for (String commitId : failSliceCommits){
                    failCommitStr.append(commitId).append(",");
                }
                for (Integer warningId : failSliceWarnings){
                    failWarningStr.append(warningId).append(",");
                }
                failSliceCommits.clear();
                failSliceWarnings.clear();
                String fail_commit_path = RESULT_ROOT_PATH + "warning slicing/" + projectName + "_commits" + ".txt";
                String fail_warning_path = RESULT_ROOT_PATH + "warning slicing/" + projectName + "_warnings" + ".txt";
                File fail_commit_file = new File(fail_commit_path);
                File fail_warning_file = new File(fail_warning_path);
                fail_commit_file.createNewFile();
                fail_warning_file.createNewFile();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fail_commit_file));
                bufferedWriter.write(failCommitStr.toString());
                bufferedWriter.flush();
                bufferedWriter.close();

                bufferedWriter = new BufferedWriter(new FileWriter(fail_warning_file));
                bufferedWriter.write(failWarningStr.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
