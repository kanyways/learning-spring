/**
 * Project Name:learning-spring
 * File Name:IndexController.java
 * Package Name:me.kany.project.learning.spring.controller
 * Date:2022-10-15 7:41 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.kany.project.learning.spring.constant.Constants;
import me.kany.project.learning.spring.producer.RabbitmqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        jsonData.put("code", 0);
        jsonData.put("message", String.format("消息： 【%s】，发送成功。", message));
        rabbitmqProducer.sendProducerTwoConsumerMessage(Constants.TWO_CONSUMER_ROUTING_NAME, message);
        return jsonData;
    }
}
