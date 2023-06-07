package org.nju.demo.other;

import java.io.*;

import static org.nju.demo.other.Info.RESULT_ROOT_PATH;
import static org.nju.demo.other.Info.projects;

public class WordSplit {

    public static boolean isValid(char c){
        if (c>='a'&&c<='z') return true;
        if (c>='A'&&c<='Z') return true;
        if (c>='0'&&c<='9') return true;
        return false;
    }

    public static String format(String word){
        StringBuilder res = new StringBuilder();
        for (int i = 0;i<word.length();++i){
            char c = word.charAt(i);
            if (isValid(c)){
                if (c>='a'&&c<='z'){
                    res.append(c);
                }
                else{
                    if (c>='A'&&c<='Z'){
                        if (i==0) res.append(c);
                        else{
                            char lastCharacter = word.charAt(i-1);
                            if (lastCharacter>='a'&&lastCharacter<='z') res.append(" ").append(c);
                            else res.append(c);
                        }
                    }
                    else{
                        res.append(" ").append(c).append(" ");
                    }
                }
            }
            else if (c == '_'){
                res.append(" ");
            }
            else{
                res.append(" ").append(c).append(" ");
            }
        }
        return res.toString();
    }

    public static void splitWord(File[] listFiles,String inputDirPath,String outputDirPath){
        if (listFiles == null) return;
        for (File f : listFiles){
            try{
                String fileName = f.getName();
//                System.out.println("文件："+fileName+"解析开始");
                String outputFilePath = outputDirPath + fileName;
                File outputFile = new File(outputFilePath);
//                outputFile.createNewFile();
                InputStream input = new FileInputStream(inputDirPath+fileName);
                StringBuilder resultSb = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(input))){
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] words = line.split(" ");
                        for(String word : words){
                            resultSb.append(format(word)).append(" ");
                        }
                        resultSb.append("\n");
                    }
                }
                String res = resultSb.toString();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
                bufferedWriter.write(res);
                bufferedWriter.flush();
                bufferedWriter.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (String projectName : projects){
            try{
                System.out.println("项目："+projectName+"解析开始");
                String inputDirPath1 = RESULT_ROOT_PATH + "warning line numbers/" + projectName + "/";
                String inputDirPath2 = RESULT_ROOT_PATH + "ten line arroud the warning line numbers/" + projectName + "/";
                //创建输出目录
                String outputDirPath1 = RESULT_ROOT_PATH + "warning line numbers abstract/" + projectName + "/";
                String outputDirPath2 = RESULT_ROOT_PATH + "ten line arroud the warning line numbers abstract/" + projectName + "/";
                File outputDir1 = new File(outputDirPath1);
                File outputDir2 = new File(outputDirPath2);
//                outputDir1.mkdirs();
//                outputDir2.mkdirs();
                File inputDirectory1 = new File(inputDirPath1);
                File inputDirectory2 = new File(inputDirPath1);
                //abstract 所在行号
                System.out.println("项目："+projectName+"抽象化所在行号代码");
                splitWord(inputDirectory1.listFiles(),inputDirPath1,outputDirPath1);
                //abstract 上下十行
                System.out.println("项目："+projectName+"抽象化上下十行代码");
                splitWord(inputDirectory2.listFiles(),inputDirPath2,outputDirPath2);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
