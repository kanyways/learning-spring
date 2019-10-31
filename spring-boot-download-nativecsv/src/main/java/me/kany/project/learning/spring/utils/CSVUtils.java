/**
 * Project Name:learning-spring
 * File Name:CSVUtils.java
 * Package Name:me.kany.project.learning.spring.utils
 * Date:2019年10月31日 11:36
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * ClassName:CSVUtils<br/>
 * Function: CSV文件工具类<br/>
 * Date:2019年10月31日 11:36<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
public class CSVUtils {
    public static String TAB = "\r\n";

    //判断平台
    static {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name").toLowerCase();
        if (os.startsWith("win")) {
            TAB = "\r\n";
        } else if (os.startsWith("linux") || os.startsWith("unix")) {
            TAB = "\n";
        } else if (os.startsWith("mac")) {
            TAB = "\r";
        }
    }

    /**
     * exportCsv: 导出csv<br/>
     *
     * @param response
     * @param titles
     * @param data
     * @param fileName
     * @author Jason.Wang
     * @createTime 2019/10/31 11:40
     */
    public static void exportCsv(HttpServletResponse response, List<String> titles, List<List<String>> data, String fileName) {
        StringBuilder sb = new StringBuilder();
        OutputStream outputStream = null;
        for (int i = 0; i < titles.size(); i++) {
            if (i != titles.size() - 1) {
                sb.append(titles.get(i)).append(",");
            } else {
                sb.append(titles.get(i)).append(TAB);
            }
        }
        for (int i = 0; i < data.size(); i++) {
            List<String> row = data.get(i);
            for (int j = 0; j < row.size(); j++) {
                if (j != row.size() - 1) {
                    sb.append(row.get(j)).append(",");
                } else {
                    sb.append(row.get(j)).append(TAB);
                }
            }
        }

        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            //response.addHeader("Content-Length", "" + sb.length());  
            //response.setContentType("application/csv;charset=UTF-8");
            //设置文件ContentType类型，这样设置，会自动判断下载文件类型 
            response.setContentType("multipart/form-data");
            outputStream = response.getOutputStream();
            //加上bom头，才不会中文乱码
            outputStream.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
            outputStream.write(sb.toString().getBytes("UTF-8"));
            outputStream.flush();
        } catch (IOException e) {
            log.error("CSVUtils.exportCsv error:", e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                log.error("CSVUtils.exportCsv close OutputStream error:", e);
            }
        }
    }

    /**
     * importCsv: 导入csv<br/>
     *
     * @param file
     * @return
     * @author Jason.Wang
     * @createTime 2019/10/31 11:39
     */
    public static List<String> importCsv(File file) {
        List<String> data = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        } catch (Exception e) {
            log.error("CSVUtils.importCsv error:", e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                log.error("CSVUtils.importCsv close BufferedReader error:", e);
            }
        }

        return data;
    }

    /**
     * importCsv: 导入csv<br/>
     *
     * @param inputStream
     * @return
     * @author Jason.Wang
     * @createTime 2019/10/31 11:39
     */
    public static List<String> importCsv(InputStream inputStream) {
        List<String> data = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = br.readLine()) != null) {
                data.add(line);
            }

        } catch (Exception e) {
            log.error("CSVUtils.importCsv error:", e);
        } finally {
            try {
                br.close();
                inputStream.close();
            } catch (IOException e) {
                log.error("CSVUtils.importCsv close BufferedReader or InputStream error:", e);
            }

        }
        return data;
    }

}
