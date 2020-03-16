/**
 * Project Name:learning-spring
 * File Name:RetCode.java
 * Package Name:me.kany.project.learning.spring.entity
 * Date:2020年03月16日 10:42
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ClassName:RetCode<br/>
 * Function: 错误编码类<br/>
 * Date:2020年03月16日 10:42<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Data
@AllArgsConstructor
public class RetCode {
    private Integer code;
    private String message;

    public static RetCode mark(int code, String message) {
        return new RetCode(code, message);
    }

    public static RetCode mark(Integer code) {
        return new RetCode(code, null);
    }
}
