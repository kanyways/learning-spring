/**
 * Project Name:ethereum-parent
 * File Name:ExcelUtils.java
 * Package Name:me.kany.project.learning.spring.utils.poi.excel
 * Date:2019年06月19日 22:55
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.poi.excel;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ExcelUtils<br/>
 * Function: ExcelUtils的工具类<br/>
 * Date:2019年06月19日 22:55<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public interface ExcelUtils<T> {

    Logger logger = LoggerFactory.getLogger(ExcelUtils.class.getCanonicalName());

    /**
     * generateCollegeExcel: 根据当前的模版生成导出院校专业的信息<br/>
     *
     * @param templateFilePath
     * @param currentYear
     * @param provinceName
     * @param startNum
     * @param cellCount
     * @param list
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @createTime 2019/6/20 10:06
     */
    default byte[] generateCollegeExcel(String templateFilePath, Integer currentYear, String provinceName, Integer startNum, Integer cellCount, List<T> list) throws Exception {
        if (StringUtils.isBlank(templateFilePath)) {
            if (logger.isDebugEnabled()) {
                logger.debug("模版文件为空");
            }
            throw new RuntimeException("template file is not exits");
        }
        if (CollectionUtils.isEmpty(list)) {
            if (logger.isDebugEnabled()) {
                logger.debug("传入集合为空");
            }
            throw new RuntimeException("list is empty");
        }
        File templateFile = new File(templateFilePath);
        ByteArrayOutputStream out = null;
        XSSFWorkbook workBook = null;
        try {
            //使用POI的文件系统
            workBook = new XSSFWorkbook(new FileInputStream(templateFile));
            // 读取了模板内所有sheet内容
            Sheet sheet = workBook.getSheetAt(0);

            String currentTitle = String.format("%s年%s志愿填报院校及专业初选方案", currentYear, provinceName);
            String currentPlannedQuantity = String.format("%s计划数", currentYear);
            String previousMinScore = String.format("%s年最低分数", currentYear - 1);
            String previousMinRank = String.format("%s年最低位次", currentYear - 1);
            String currentMajors = String.format("%s年招生专业", currentYear - 1);

            // 获取订单号所在行列,并设置值ֵ
            sheet.getRow(0).getCell(0).setCellValue(currentTitle);
            sheet.getRow(1).getCell(5).setCellValue(currentPlannedQuantity);
            sheet.getRow(1).getCell(6).setCellValue(previousMinScore);
            sheet.getRow(1).getCell(7).setCellValue(previousMinRank);
            sheet.getRow(1).getCell(8).setCellValue(currentMajors);
            //修改新生成的sheet页名称
            workBook.setSheetName(0, currentTitle);

            //当前行的高度
            short rowHight = sheet.getRow(startNum).getHeight();
            //具体三行的样式
            List<CellStyle> punchingList = new ArrayList<>();
            List<CellStyle> stableList = new ArrayList<>();
            List<CellStyle> protectList = new ArrayList<>();
            //开始获取当前行的样式
            getRowsStyle(punchingList, sheet, startNum, cellCount);
            getRowsStyle(stableList, sheet, startNum + 1, cellCount);
            getRowsStyle(protectList, sheet, startNum + 2, cellCount);

            int totalCount = list.size();

            // 通过移动模版最后一行创建最后一行
            sheet.shiftRows(7, 9, totalCount - startNum, true, false);
            for (int i = 0; i < totalCount; i++) {
                // 创建新的行
                Row row = sheet.createRow(startNum + i);
                createCell(i, list.get(i), row, cellCount, punchingList, stableList, protectList);
                row.setHeight(rowHight);
            }
            /*输出为一个新的Excel，也就是动态修改完之后的excel*/
            out = new ByteArrayOutputStream();
            workBook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * generateCollegeExcel: 创建的时候顺便创建一个文件<br/>
     *
     * @param templateFilePath
     * @param destFile
     * @param currentYear
     * @param provinceName
     * @param startNum
     * @param cellCount
     * @param list
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @createTime 2019/6/20 11:17
     */
    default byte[] generateCollegeExcel(String templateFilePath, File destFile, Integer currentYear, String provinceName, Integer startNum, Integer cellCount, List<T> list) throws Exception {
        byte[] bytes = generateCollegeExcel(templateFilePath, currentYear, provinceName, startNum, cellCount, list);
        FileOutputStream fileOutputStream = new FileOutputStream(destFile);
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();
        return bytes;
    }


    /**
     * generateCollegeExcelNoReturn: 创建的时候顺便创建一个文件<br/>
     *
     * @param templateFilePath
     * @param destFile
     * @param currentYear
     * @param provinceName
     * @param startNum
     * @param cellCount
     * @param list
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @createTime 2019/6/20 11:17
     */
    default void generateCollegeExcelNoReturn(String templateFilePath, File destFile, Integer currentYear, String provinceName, Integer startNum, Integer cellCount, List<T> list) throws Exception {
        byte[] bytes = generateCollegeExcel(templateFilePath, currentYear, provinceName, startNum, cellCount, list);
        FileOutputStream fileOutputStream = new FileOutputStream(destFile);
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /**
     * getRowsStyle: 获取当前行的Style<br/>
     *
     * @param cellStyleList
     * @param sheet
     * @param rowNum
     * @param cellCount
     * @author Jason.Wang
     * @createTime 2019/6/20 0:35
     */
    default void getRowsStyle(List<CellStyle> cellStyleList, Sheet sheet, int rowNum, int cellCount) {
        for (int i = 0; i < cellCount; i++) {
            Cell cell = sheet.getRow(rowNum).getCell(i);
            CellStyle cellStyle = cell.getCellStyle();
            cellStyleList.add(cellStyle);
        }
    }

    /**
     * createCell: 创建行，这个要实现的方法<br/>
     *
     * @param index
     * @param t
     * @param row
     * @param cellCount
     * @param punchingList
     * @param stableList
     * @param protectList
     * @author Jason.Wang
     * @createTime 2019/6/20 10:04
     */
    void createCell(int index, T t, Row row, Integer cellCount, List<CellStyle> punchingList, List<CellStyle> stableList, List<CellStyle> protectList);
}
