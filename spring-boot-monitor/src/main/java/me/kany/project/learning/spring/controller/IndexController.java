/**
 * Project Name:learning-spring
 * File Name:IndexController.java
 * Package Name:me.kany.project.learning.spring.controller
 * Date:2022-09-12 12:28 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: IndexController<br/>
 * Function: 首页控制器<br/>
 * Date: 2022-09-12 12:28 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 1.8
 */
@Log4j2
@RestController
public class IndexController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping("")
    public Object execute() throws JsonProcessingException {
        log.info("模拟业务处理1分钟");
        Long serverTime = System.currentTimeMillis();
        log.info("正在处理业务，开始时间：{}", serverTime);
        log.info("正在处理业务，结束时间：{}", System.currentTimeMillis());
        Map<String,Object> jsonObject = new HashMap<>();
        jsonObject.put("ip", request.getRemoteAddr());
        jsonObject.put("timestamp", System.currentTimeMillis());
        log.info("模拟业务处理1分钟，响应参数：{}", objectMapper.writeValueAsString(jsonObject));
        return jsonObject;
    }
}
