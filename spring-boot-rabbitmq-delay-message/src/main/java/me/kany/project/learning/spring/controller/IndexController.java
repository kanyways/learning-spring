/**
 * Project Name:learning-spring
 * File Name:IndexController.java
 * Package Name:me.kany.project.learning.spring.controller
 * Date:2022-10-15 7:41 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.kany.project.learning.spring.constant.Constants;
import me.kany.project.learning.spring.producer.RabbitmqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ClassName: IndexController<br/>
 * Function: IndexController 首页控制器<br/>
 * Date: 2022-10-15 7:41 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */

@RestController
public class IndexController {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RabbitmqProducer rabbitmqProducer;

    /**
     * execute: 主页执行方法<br/>
     *
     * @param message
     * @return
     * @author Jason.Wang
     */
    @GetMapping("")
    public Object execute(String message) {
        ObjectNode jsonData = objectMapper.createObjectNode();

        String timeDelay = "1m";
        Duration duration = Duration.parse("PT" + timeDelay);

        jsonData.put("code", 0);
        jsonData.put("message", "消息发送成功");
        jsonData.put("body", message);
        jsonData.put("tips", String.format("这是一个延迟%d秒的消息", duration.getSeconds()));
        jsonData.put("pushTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        try {
            rabbitmqProducer.sendConsumerDelayMessage(Constants.CONSUMER_DELAY_ROUTING_NAME,
                objectMapper.writeValueAsString(jsonData), Integer.valueOf(duration.getSeconds() + ""));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonData;
    }
}
