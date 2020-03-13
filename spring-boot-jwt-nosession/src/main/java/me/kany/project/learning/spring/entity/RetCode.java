/**
 * Project Name:learning-spring
 * File Name:RetCode.java
 * Package Name:me.kany.project.learning.spring.entity
 * Date:2020年03月13日 14:23
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ClassName:RetCode<br/>
 * Function: RetCode<br/>
 * Date:2020年03月13日 14:23<br/>
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
