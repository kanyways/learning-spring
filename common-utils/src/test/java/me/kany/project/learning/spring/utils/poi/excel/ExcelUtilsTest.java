/**
 * Project Name:ethereum-parent
 * File Name:ExcelUtilsTest.java
 * Package Name:me.kany.project.learning.spring.utils.poi.excel
 * Date:2019年06月20日 19:20
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.poi.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ExcelUtilsTest<br/>
 * Function: ExcelUtils的测试类<br/>
 * Date:2019年06月20日 19:20<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public class ExcelUtilsTest implements ExcelUtils<String> {

    @Test
    public void generateFile() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(System.currentTimeMillis() + "");
        }
        try {
            new ExcelUtilsTest().generateCollegeExcelNoReturn("college.xlsx", new File("text.xlsx"), 2019, "广东省", 3, 14, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * createCell: 创建行，这个要实现的方法<br/>
     *
     * @param index
     * @param s
     * @param row
     * @param cellCount
     * @param punchingList
     * @param stableList
     * @param protectList
     * @author Jason.Wang
     * @createTime 2019/6/20 10:04
     */
    @Override
    public void createCell(int index, String s, Row row, Integer cellCount, List<CellStyle> punchingList, List<CellStyle> stableList, List<CellStyle> protectList) {
        for (int i = 0; i < cellCount; i++) {
            Cell cell = row.createCell(i);
            switch (i) {
                case 0:
                    cell.setCellValue(String.valueOf(index + 1));
                    break;
                default:
                    cell.setCellValue(s);
                    break;
            }
            setCurrentCellStyle(i, i % 3, cell, punchingList, stableList, protectList);
        }
    }

    /**
     * setCurrentCellStyle: 设置当前行的样式等<br/>
     *
     * @param index
     * @param enrollmentLevel
     * @param cell
     * @param punchingList
     * @param stableList
     * @param protectList
     * @author Jason.Wang
     * @createTime 2019/6/20 10:55
     */
    private void setCurrentCellStyle(int index, Integer enrollmentLevel, Cell cell, List<CellStyle> punchingList, List<CellStyle> stableList, List<CellStyle> protectList) {
        CellStyle cellStyle = null;
        switch (enrollmentLevel) {
            case 1:
                cellStyle = punchingList.get(index);
                break;
            case 2:
                cellStyle = stableList.get(index);
                break;
            default:
                cellStyle = protectList.get(index);
                break;
        }
        cell.setCellStyle(cellStyle);
    }
}
