package org.nju.demo.other;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.nju.demo.other.Info.RESULT_ROOT_PATH;
import static org.nju.demo.other.Info.projects;

public class WordAbstract {

    public static void abstractWord(File[] listFiles,String dirPath){
        if (listFiles == null) return;
        Map<String,Integer> hm = new HashMap<>();
        for (File f : listFiles){
            try{
                String fileName = f.getName();
                String filePath = dirPath + fileName;
                InputStream input = new FileInputStream(filePath);
                try (BufferedReader br = new BufferedReader(new InputStreamReader(input))){
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] words = line.split(" ");
                        for(String word : words){
                            if (word.equals(" ")) continue;
                            else {
                                hm.put(word,hm.getOrDefault(word,0)+1);
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        for (File f : listFiles){
            try{
                String fileName = f.getName();
                String filePath = dirPath + fileName;
                File file = new File(filePath);
                InputStream input = new FileInputStream(filePath);
                StringBuilder resultSb = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(input))){
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] words = line.split(" ");
                        for(String word : words){
                            if (word.equals(" ")) continue;
                            else {
                                if (hm.get(word)<10){
                                    resultSb.append("UNK").append(" ");
                                }
                                else {
                                    resultSb.append(word).append(" ");
                                }
                            }
                        }
                        resultSb.append("\n");
                    }
                }
                String res = resultSb.toString();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
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
                String dirPath1 = RESULT_ROOT_PATH + "warning line numbers abstract/" + projectName + "/";
                String dirPath2 = RESULT_ROOT_PATH + "ten line arroud the warning line numbers abstract/" + projectName + "/";
                File directory1 = new File(dirPath1);
                File directory2 = new File(dirPath2);
                //abstract 所在行号
                System.out.println("项目："+projectName+"抽象化所在行号代码");
                abstractWord(directory1.listFiles(),dirPath1);
                //abstract 上下十行
                System.out.println("项目："+projectName+"抽象化上下十行代码");
                abstractWord(directory2.listFiles(),dirPath2);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
