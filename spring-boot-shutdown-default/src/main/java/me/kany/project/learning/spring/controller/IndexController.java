/**
 * Project Name:learning-spring
 * File Name:IndexController.java
 * Package Name:me.kany.project.learning.spring.controller
 * Date:2021年06月10日 14:47
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName:IndexController<br/>
 * Function: 首页控制<br/>
 * Date:2021年06月10日 14:35<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@Log4j2
@RestController
public class IndexController {
    @Autowired
    HttpServletRequest request;

    /**
     * execute: 默认执行的方式<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2021/6/10 14:40
     */
    @RequestMapping("")
    public Object execute() {
        log.info("模拟业务处理1分钟");
        Long serverTime = System.currentTimeMillis();
        log.info("正在处理业务，开始时间：{}", serverTime);
        while (System.currentTimeMillis() < serverTime + (60 * 1000)) {
        }
        log.info("正在处理业务，结束时间：{}", System.currentTimeMillis());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ip", request.getRemoteAddr());
        jsonObject.put("timestamp", System.currentTimeMillis());
        log.info("模拟业务处理1分钟，响应参数：{}", jsonObject.toJSONString());
        return jsonObject;
    }
}