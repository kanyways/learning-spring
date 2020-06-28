/**
 * Project Name:learning-spring
 * File Name:Weblog.java
 * Package Name:me.kany.project.learning.spring.module
 * Date:2020年06月28日 11:38
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.module;

import lombok.Data;

/**
 * ClassName:WebLog<br/>
 * Function: 日志对象<br/>
 * Date:2020年06月28日 11:38<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Data
public class WebLog {

    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作时间
     */
    private Long startTime;

    /**
     * 消耗时间
     */
    private Integer spendTime;

    /**
     * 根路径
     */
    private String basePath;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * IP地址
     */
    private String ip;
    /**
     * 请求参数
     */
    private Object parameter;

    /**
     * 返回结果
     */
    private Object result;
}
