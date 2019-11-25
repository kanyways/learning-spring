/**
 * Project Name:learning-spring
 * File Name:CustomLongNumberDateConverter.java
 * Package Name:me.kany.project.learning.spring.utils
 * Date:2019年11月25日 14:57
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils;

import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;

import java.util.Date;

/**
 * ClassName:CustomLongNumberDateConverter<br/>
 * Function: 将数值变成日期<br/>
 * Date:2019年11月25日 14:57<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public class CustomLongNumberDateConverter extends LongStringConverter {
    @Override
    public CellData convertToExcelData(Long value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        Date date = new Date(value);
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return new CellData(DateUtils.format(date, null));
        } else {
            return new CellData(DateUtils.format(date, contentProperty.getDateTimeFormatProperty().getFormat()));
        }
    }
}
