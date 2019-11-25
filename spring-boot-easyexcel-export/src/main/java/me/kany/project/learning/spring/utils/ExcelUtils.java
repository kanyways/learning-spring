/**
 * Project Name:learning-spring
 * File Name:ExcelUtils.java
 * Package Name:me.kany.project.learning.spring.utils
 * Date:2019年11月25日 14:05
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * ClassName:ExcelUtils<br/>
 * Function: ExcelUtils工具类<br/>
 * Date:2019年11月25日 14:05<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public class ExcelUtils {
    /**
     * writeExcel: 导出数据<br/>
     *
     * @param response
     * @param list
     * @param fileName
     * @param sheetName
     * @throws Exception
     * @author Jason.Wang
     * @createTime 2019/11/25 14:19
     */
    public static void writeExcel(HttpServletResponse response, List<?> list, String fileName, Class head, String sheetName) throws Exception {
        ExcelWriterBuilder excelWriterBuilder = EasyExcelFactory.write(getOutputStream(fileName, response));
        if (head != null) {
            excelWriterBuilder.head(head);
        }
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 18);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        //导出数据
        excelWriterBuilder.registerWriteHandler(horizontalCellStyleStrategy).sheet(sheetName).doWrite(list);
    }

    /**
     * getOutputStream: 导出文件时为Writer生成OutputStream<br/>
     *
     * @param fileName
     * @param response
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @createTime 2019/11/25 14:08
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
        //创建本地文件
        fileName = fileName + ".xlsx";
        try {
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.addHeader("Content-Disposition", "filename=" + fileName);
            return response.getOutputStream();
        } catch (Exception e) {
            throw new Exception("导出异常！");
        }
    }
}
