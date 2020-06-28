/**
 * Project Name:learning-spring
 * File Name:CommonResult.java
 * Package Name:me.kany.project.learning.spring.common
 * Date:2020年06月28日 11:30
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:CommonResult<br/>
 * Function: 通用返回对象<br/>
 * Date:2020年06月28日 11:30<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {
    private long code;
    private String message;
    private T data;

    /**
     * success: 成功返回结果<br/>
     *
     * @param data 获取的数据
     * @author Jason.Wang
     * @createTime 2020/6/28 11:35
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * success: 成功返回结果<br/>
     *
     * @param data    获取的数据
     * @param message 提示信息
     * @author Jason.Wang
     * @createTime 2020/6/28 11:35
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * failed: 失败返回结果<br/>
     *
     * @param errorCode 错误码
     * @author Jason.Wang
     * @createTime 2020/6/28 11:35
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * failed: 失败返回结果<br/>
     *
     * @param errorCode 错误码
     * @param message   错误信息
     * @author Jason.Wang
     * @createTime 2020/6/28 11:36
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode, String message) {
        return new CommonResult<T>(errorCode.getCode(), message, null);
    }

    /**
     * failed: 失败返回结果<br/>
     *
     * @param message 提示信息
     * @author Jason.Wang
     * @createTime 2020/6/28 11:36
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * failed: 失败返回结果<br/>
     *
     * @author Jason.Wang
     * @createTime 2020/6/28 11:36
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * validateFailed: 参数验证失败返回结果<br/>
     *
     * @author Jason.Wang
     * @createTime 2020/6/28 11:36
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * validateFailed: 参数验证失败返回结果<br/>
     *
     * @param message 提示信息
     * @author Jason.Wang
     * @createTime 2020/6/28 11:36
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * unauthorized: 未登录返回结果<br/>
     *
     * @author Jason.Wang
     * @createTime 2020/6/28 11:36
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * forbidden: 未授权返回结果<br/>
     *
     * @author Jason.Wang
     * @createTime 2020/6/28 11:32
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }
}
