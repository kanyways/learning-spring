/**
 * Project Name:ethereum-parent
 * File Name:ZipUtils.java
 * Package Name:me.kany.project.learning.spring.utils.zip.utils
 * Date:2019年06月20日 15:41
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.zip.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ClassName:ZipUtils<br/>
 * Function: ZipUtils的工具类<br/>
 * Date:2019年06月20日 15:41<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public class ZipUtils {

    /**
     * makeFileToZip: 将指定的文件变成Zip<br/>
     *
     * @param files         要添加的目录
     * @param zipFile       保存的 ZIP 文件名
     * @param zipFolderName ZIP 中的路径名
     * @param level         压缩级别(0~9)
     * @throws IOException
     * @author Jason.Wang
     * @createTime 2019/6/20 16:05
     */
    public static void makeFileToZip(List<String> files, File zipFile, String zipFolderName, int level) throws IOException {
        makeFileToZip(files, new FileOutputStream(zipFile), zipFolderName, level);
    }

    /**
     * makeFileToZip: 将指定的文件变成Zip<br/>
     *
     * @param files         要添加的目录
     * @param outputStream  保存的文件流
     * @param zipFolderName ZIP中的路径名
     * @param level         压缩级别(0~9)
     * @throws IOException
     * @author Jason.Wang
     * @createTime 2019/6/20 16:22
     */
    public static void makeFileToZip(List<String> files, OutputStream outputStream, String zipFolderName, int level) throws IOException {
        level = (level < 0 || level > 9) ? 7 : level;
        if (zipFolderName == null) {
            zipFolderName = "";
        }
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        zipOutputStream.setLevel(level);
        if (null != files && files.size() > 0) {
            String finalZipFolderName = zipFolderName;
            for (String filePath : files) {
                recurseFiles(zipOutputStream, new File(filePath), finalZipFolderName);
            }
        }
        zipOutputStream.close();
    }

    /**
     * makeDirectoryToZip: 将指定的文件夹变成Zip<br/>
     *
     * @param directory     要添加的目录
     * @param zipFile       保存的 ZIP 文件名
     * @param zipFolderName ZIP 中的路径名
     * @param level         压缩级别(0~9)
     * @throws IOException
     * @author Jason.Wang
     * @createTime 2019/6/20 16:00
     */
    public static void makeDirectoryToZip(File directory, File zipFile, String zipFolderName, int level) throws IOException {
        level = (level < 0 || level > 9) ? 7 : level;
        if (zipFolderName == null) {
            zipFolderName = "";
        }
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
        zipOutputStream.setLevel(level);
        String[] fileNames = directory.list();
        if (fileNames != null) {
            for (int i = 0; i < fileNames.length; i++) {
                recurseFiles(zipOutputStream, new File(directory, fileNames[i]), zipFolderName);
            }
        }
        zipOutputStream.close();
    }

    /**
     * recurseFiles: 循环文件对象<br/>
     *
     * @param zipOutputStream
     * @param file
     * @param pathName
     * @throws IOException
     * @throws FileNotFoundException
     * @author Jason.Wang
     * @createTime 2019/6/20 15:59
     */
    private static void recurseFiles(ZipOutputStream zipOutputStream, File file, String pathName) throws IOException, FileNotFoundException {
        byte[] buf = new byte[1024];
        if (file.isDirectory()) {
            pathName = pathName + file.getName() + "/";
            zipOutputStream.putNextEntry(new ZipEntry(pathName));
            String[] fileNames = file.list();
            if (fileNames != null) {
                for (int i = 0; i < fileNames.length; i++) {
                    recurseFiles(zipOutputStream, new File(file, fileNames[i]), pathName);
                }
            }
        } else {
            if (StringUtils.isNotBlank(pathName)) {
                pathName = pathName + "/";
            }
            ZipEntry zipEntry = new ZipEntry(pathName + file.getName());
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            zipOutputStream.putNextEntry(zipEntry);
            int len;
            while ((len = bufferedInputStream.read(buf)) >= 0) {
                zipOutputStream.write(buf, 0, len);
            }//46fb847a7daf78ee11
            bufferedInputStream.close();
            zipOutputStream.closeEntry();
        }
    }
}
