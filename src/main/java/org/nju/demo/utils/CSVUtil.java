package org.nju.demo.utils;

import org.nju.demo.config.Constants;
import org.nju.demo.pojo.vo.ViolationCodeVO;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CSVUtil {

    public static void getCsv(String username,String fileName,List<ViolationCodeVO> violationCodeList){
        try {
            File file = new File(Constants.ROOT_PATH+"data/"+username+"/"+fileName+".csv");
            //打开输出文件流
            FileOutputStream fos = new FileOutputStream(file,false); // true 表示在后面追加，不加 true 默认表示覆盖原来的数据
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            //创建字符串缓存
            BufferedWriter bw = new BufferedWriter(osw);
//            String header = "id,snippet,label\r\n"; //（文件标题）
//            bw.write(header);
            //添加要写入的数据
            for (ViolationCodeVO violationCodeVO : violationCodeList){
                String str = violationCodeVO.getId() +
                        "," +
                        formatSnippet(violationCodeVO.getSnippet()) +
                        "," +
                        violationCodeVO.getState() +
                        "\r\n";
                bw.write(str);
            }
            //关闭文件流
            bw.flush();
            osw.flush();
            fos.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String formatSnippet(String snippet){
        if (snippet.contains("\"")){
            snippet = snippet.replaceAll("\"","\"\"");
        }
        snippet = "\"" + snippet + "\"";
        return snippet;
    }

}
