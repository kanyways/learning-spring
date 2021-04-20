/**
 * Project Name:learning-spring
 * File Name:IndexController.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.provider.controller
 * Date:2021年04月20日 19:24
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:IndexController<br/>
 * Function: 首页控制类<br/>
 * Date:2021年04月20日 19:24<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
@RestController
public class IndexController {

    /**
     * execute: 首页程序<br/>
     *
     * @param message
     * @return
     * @author Jason.Wang
     * @date 2021/4/20 19:25
     */
    @GetMapping(value = "/{message}")
    public String execute(@PathVariable String message) {
        return "Hello Nacos Discovery " + message;
    }
}