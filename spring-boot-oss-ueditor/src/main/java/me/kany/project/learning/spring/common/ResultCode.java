/**
 * Project Name:learning-spring
 * File Name:ResultCode.java
 * Package Name:me.kany.project.learning.spring.common
 * Date:2020年06月28日 11:32
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.common;

/**
 * ClassName:ResultCode<br/>
 * Function: 返回的错误编码<br/>
 * Date:2020年06月28日 11:32<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200L, "操作成功"),
    FAILED(500L, "操作失败"),
    VALIDATE_FAILED(404L, "参数检验失败"),
    UNAUTHORIZED(401L, "暂未登录或token已经过期"),
    FORBIDDEN(403L, "没有相关权限");

    ResultCode(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    private Long code;
    private String message;

    @Override
    public Long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
