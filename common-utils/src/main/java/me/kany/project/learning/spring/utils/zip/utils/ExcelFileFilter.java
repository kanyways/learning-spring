/**
 * Project Name:ethereum-parent
 * File Name:ExcelFileFilter.java
 * Package Name:me.kany.project.learning.spring.utils.zip.utils
 * Date:2019年06月20日 15:53
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.zip.utils;

import java.io.File;
import java.io.FileFilter;

/**
 * ClassName:ExcelFileFilter<br/>
 * Function: Excel文件的过滤器<br/>
 * Date:2019年06月20日 15:53<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public class ExcelFileFilter implements FileFilter {
    /**
     * Tests whether or not the specified abstract pathname should be
     * included in a pathname list.
     *
     * @param pathname The abstract pathname to be tested
     * @return <code>true</code> if and only if <code>pathname</code>
     * should be included
     */
    @Override
    public boolean accept(File pathname) {
        String fileName = pathname.getName();
        if (fileName.endsWith("xls") || fileName.endsWith("xlsx")) {
            return true;
        }
        return false;
    }
}
