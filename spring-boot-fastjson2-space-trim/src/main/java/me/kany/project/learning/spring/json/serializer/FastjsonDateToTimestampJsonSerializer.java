/**
 * Project Name:learning-spring
 * File Name:FastjsonDateToTimestampJsonSerializer.java
 * Package Name:me.kany.project.learning.spring.json.serializer
 * Date:2022-12-16 11:17
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.json.serializer;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * ClassName: FastjsonDateToTimestampJsonSerializer<br/>
 * Function: 日期序列化<br/>
 * Date: 2022-12-16 11:17<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class FastjsonDateToTimestampJsonSerializer implements ObjectWriter<Object> {
    @Override
    public void write(JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {
        if (null == object) {
            jsonWriter.writeNull();
            return;
        }
        // 格式换日期
        if (object instanceof Date) {
            jsonWriter.writeInt64(((Date)object).getTime());
        } else if (object instanceof LocalDate) {
            jsonWriter.writeInt64(((LocalDate)object).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        } else if (object instanceof LocalDateTime) {
            jsonWriter.writeInt64(Timestamp.valueOf((LocalDateTime)object).getTime());
        }
    }
}
