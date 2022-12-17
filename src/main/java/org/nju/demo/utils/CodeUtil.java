package org.nju.demo.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CodeUtil {

    public static String readCodeFromData(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public static String readCodeFromData(InputStream inputStream,int startLine,int endLine)
            throws IOException {
        if (startLine == -1) return "";
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int num = 0;
            while ((line = br.readLine()) != null) {
                num++;
                if (num >= startLine && num <= endLine) {
                    resultStringBuilder.append(line).append("\n");
                } else if (num < startLine) continue;
                else break;
            }
        }
        return resultStringBuilder.toString();
    }

    public static List<String> getDataFromLizard(InputStream inputStream) throws IOException{
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int flag = 0;
            while ((line = bufferedReader.readLine()) != null){
                if (line.startsWith("-") || line.equals("1 file analyzed.")) flag++;
                else if (flag == 1 || flag == 3){
                    sb.append(line).append("\n");
                    if (flag == 3){
                        break;
                    }
                }
            }
        }
//        System.out.println(sb.toString());
        String[] str = sb.toString().split("\n");
        String className = "";

        for (int i=0;i<str.length;++i){
            String[] infos = str[i].split(" ");
            StringBuilder tmp = new StringBuilder();
            if ("".equals(className)){
                className = infos[infos.length-1].substring(infos[infos.length-1].lastIndexOf('/')+1,infos[infos.length-1].lastIndexOf('.'));
            }
            if (i != str.length - 1){
                String methodName = infos[infos.length-1].substring(infos[infos.length-1].lastIndexOf(':')+1,infos[infos.length-1].indexOf('@'));
                tmp.append(className).append(":").append(methodName);
            }else {
                tmp.append(className);
            }

            for (int j=0;j<infos.length-1;++j){
                if (!"".equals(infos[j].trim())){
                    tmp.append(":").append(infos[j]);
                }
            }
            res.add(tmp.toString());
        }
        return res;
    }

    public static int[] getMethodLocationFromReport(InputStream inputStream,String methodName) throws IOException {
        int[] res = new int[2];
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            int flag = 0;
            String line;
            while ((line = bufferedReader.readLine()) != null){
                if (flag > 1) break;
                if (line.startsWith("-")||line.equals("1 file analyzed.")) flag++;
                else if (flag == 1){
                    String[] infos = line.split(" ");
                    String info = infos[infos.length-1];
                    String strMethod = info.substring(info.lastIndexOf(':')+1,info.indexOf('@'));
                    if (strMethod.equals(methodName)){
                        String strLocation = info.substring(info.indexOf('@')+1,info.indexOf('/')-1);
                        String[] location = strLocation.split("-");
                        res[0] = Integer.parseInt(location[0]);
                        res[1] = Integer.parseInt(location[1]);
                    }
                }
            }
        }
        return res;
    }

    public static int countCodeLineOfFile(String[] snippets) throws IOException {
        return snippets.length;
    }

    public static int countMethodNumOfFile(String[] snippets) throws IOException{
        int cnt = 0;
        for (String line : snippets){
            line = formatStr(line);
            //检验该行代码是否为")...{或);"的形式
            int i = line.lastIndexOf(')');
            if (i==-1) continue;
            boolean flag = false;
            while(i<line.length()&&!flag){
                if (line.charAt(i) == '{' || line.charAt(i) == ';'){
                    flag = true;
                }
                i++;
            }
            //如果没有")...{或);"，那么此行代码一定不是一个完整的方法声明
            if (!flag) continue;
            //形参定义有换行的情况
            if (line.indexOf('(')==-1){
                cnt++;
                continue;
            }
            //排除类成员变量初始化的情况
            if (line.contains("=")) continue;

            String[] str = line.split(" ");
            i = 0;
            while(i<str.length && str[i].trim().equals("")) i++;
            if(i == str.length) continue;
            if(str[i].equals("public")||str[i].equals("private")||str[i].equals("protected")||str[i].equals("static")){
                cnt++;
            }
            else if (str[i].equals("int")||str[i].equals("double")||str[i].equals("float")||str[i].equals("long")||
                    str[i].equals("char")||str[i].equals("byte")||str[i].equals("short")||str[i].equals("boolean")||str[i].equals("void")){
                cnt++;
            }
            else if(str[i].charAt(0)>='A'&&str[i].charAt(0)<='Z'){
                cnt++;
            }
        }
        return cnt;
    }

    public static int countBranchNum(String snippet){
        int cnt = 0;
        String[] str = snippet.split("\n");
        for(String s : str){
            s = formatStr(s);
            if (s.equals("")) continue;
            if (s.startsWith("case")) cnt++;
            else if(s.startsWith("if")){
                cnt++;
            }
            else if (s.startsWith("else")){
                cnt++;
            }
            else if (s.startsWith("for")){
                cnt++;
            }
            else if (s.startsWith("while")){
                cnt++;
            }
            else if (s.contains("break")){
                cnt++;
            }
            else if (s.contains("continue")){
                cnt++;
            }
            else if (s.startsWith("switch")){
                cnt++;
            }
            else if (s.startsWith("default")){
                cnt++;
            }
            else if (s.startsWith("return")){
                cnt++;
            }
            else if (s.contains("else")){
                cnt++;
            }
        }

        return cnt;
    }

    public static int countCycNum(String snippet){
        int cnt = 0;
        String[] str = snippet.split("\n");
        for (String s : str){
            s = formatStr(s);
            if (s.equals("")) continue;
            if (s.startsWith("case")) cnt++;
            else if (s.contains("catch")) cnt++;
            else {
                int index = 0;
                if (s.startsWith("if")){
                    cnt++;
                    index = 2;
                }
                else if (s.startsWith("for")){
                    cnt++;
                    index = 3;
                }
                else if (s.startsWith("while")){
                    cnt++;
                    index = 5;
                }
                else if (s.contains("if")){
                    cnt++;
                }
                while(index<s.length()-1){
                    if (s.substring(index,index+2).equals("&&")||s.substring(index,index+2).equals("||")||s.charAt(index) == '?') cnt++;
                    index++;
                }
            }
        }
        return cnt+1;
    }

    public static String formatStr(String s){
        int index = 0;
        while(index < s.length() && s.charAt(index) == '\t') index++;
        if (index == s.length()) return "";
        return s.substring(index);
    }

}
