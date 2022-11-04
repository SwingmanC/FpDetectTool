package org.nju.demo.utils;

import java.io.*;

public class CodeUtil {

    private static String UPLOADED_FOLDER = System.getProperty("user.dir");

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

}
