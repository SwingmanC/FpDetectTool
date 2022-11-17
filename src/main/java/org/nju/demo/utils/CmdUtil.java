package org.nju.demo.utils;

import org.nju.demo.config.Constants;
import sun.font.CStrike;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CmdUtil {

    public static String generateFindBugsXML(String projectName,String version,String path) throws IOException {
        String strDir = Constants.ROOT_PATH +"/tmp/"+projectName;
        File dir = new File(strDir);
        if (!dir.exists()) dir.mkdirs();
        String filePath = Constants.ROOT_PATH+"/tmp/"+projectName+"/"+version+".xml";
        String abPath = Constants.ROOT_PATH+"/"+path;
        String command = "java -jar "+Constants.ROOT_PATH+"/tools/findbugs-3.0.1/lib/findbugs.jar -textui -low -progress -xml -output "+filePath+" "+abPath;
        return getCmd(filePath, command);
    }

    public static String generateSourceFileReport(String projectName,String version,String javaFilePath,String sourceFilePath) throws IOException {
        String strDirectory = Constants.ROOT_PATH + "report/" + projectName;
        File dir = new File(strDirectory);
        if (!dir.exists()) dir.mkdirs();

        String[] str = sourceFilePath.split("/");
        String className = str[str.length-1].split("\\.")[0];

        String reportFilePath = strDirectory + "/" + version + "_" + className + ".txt";
        File file = new File(reportFilePath);
        if (file.exists()) return reportFilePath;
        else {
            file.createNewFile();
        }
        String scriptPath = "/Users/sunchen/PycharmProjects/lizard/lizard.py";
        String targetFilePath = Constants.ROOT_PATH + javaFilePath + "/" + sourceFilePath;
        String command = "python3 " +  scriptPath + " " + targetFilePath;
        System.out.println(command);

        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        Process process = Runtime.getRuntime().exec(command);
        Scanner scanner = new Scanner(process.getInputStream());
        while(scanner.hasNextLine()){
            bufferedWriter.write(scanner.nextLine());
            bufferedWriter.write("\n");
        }
        bufferedWriter.close();
        scanner.close();

        return reportFilePath;
    }

    private static String getCmd(String filePath, String command) throws IOException {
        Process process = Runtime.getRuntime().exec(command);
        Scanner scanner = new Scanner(process.getInputStream());
        while(scanner.hasNextLine()) System.out.println(scanner.nextLine());
        scanner.close();
        return filePath;
    }

}
