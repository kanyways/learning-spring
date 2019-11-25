/**
 * Project Name:learning-spring
 * File Name:CustomLongStringConverter.java
 * Package Name:me.kany.project.learning.spring.utils
 * Date:2019年11月25日 14:45
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils;

import com.alibaba.excel.converters.longconverter.LongNumberConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * ClassName:CustomLongStringConverter<br/>
 * Function: 自定义的Excel的String转换器<br/>
 * Date:2019年11月25日 14:45<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public class CustomLongStringConverter extends LongNumberConverter {
    @Override
    public CellData convertToExcelData(Long value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return new CellData(String.format("%020d", value));
    }
}
