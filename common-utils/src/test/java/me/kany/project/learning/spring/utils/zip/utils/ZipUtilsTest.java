/**
 * Project Name:ethereum-parent
 * File Name:ZipUtilsTest.java
 * Package Name:me.kany.project.learning.spring.utils.zip.utils
 * Date:2019年06月20日 19:09
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.zip.utils;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:ZipUtilsTest<br/>
 * Function: ZipUtils的测试类<br/>
 * Date:2019年06月20日 19:09<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public class ZipUtilsTest {

    /**
     * 验证压缩文件是不是正常的
     */
    @Test
    public void createZip() {
        List<String> files = Arrays.asList();
        files = files.stream().filter(e -> (e.endsWith("xlsx") || e.endsWith("xls"))).collect(Collectors.toList());
        try {
            ZipUtils.makeFileToZip(files, new File("./232.zip"), "好名字", 9);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
