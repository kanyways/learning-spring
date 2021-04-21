/**
 * Project Name:learning-spring
 * File Name:EchoController.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.provider.controller
 * Date:2021年04月21日 11:34
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:EchoController<br/>
 * Function: 输出提供服务方<br/>
 * Date:2021年04月21日 11:34<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@RestController
public class EchoController {

    @Value("${server.port}")
    private String port;

    /**
     * execute: 首页程序<br/>
     *
     * @param message
     * @return
     * @author Jason.Wang
     * @date 2021/4/20 19:25
     */
    @GetMapping(value = "/echo/{message}")
    public String execute(@PathVariable String message) {
        return " Nacos Discovery " + message + " i am from port " + port;
    }
}