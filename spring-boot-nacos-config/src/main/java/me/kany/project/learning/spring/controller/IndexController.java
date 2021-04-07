/**
 * Project Name:learning-spring
 * File Name:IndexController.java
 * Package Name:me.kany.project.learning.spring.controller
 * Date:2021年04月07日 14:29
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:IndexController<br/>
 * Function: Index Controller<br/>
 * Date:2021年04月07日 14:29<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@RefreshScope
@RestController
@RequestMapping("/")
public class IndexController {

    @Value(value = "${nacos.name}")
    String name;

    /**
     * execute : 默认执行返回<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2021/1/21 16:24
     */
    @GetMapping("")
    public Object execute() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        return jsonObject;
    }
}