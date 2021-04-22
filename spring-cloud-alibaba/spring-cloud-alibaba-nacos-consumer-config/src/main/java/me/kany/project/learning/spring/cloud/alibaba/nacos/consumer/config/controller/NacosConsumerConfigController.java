/**
 * Project Name:learning-spring
 * File Name:NacosConsumerConfigController.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.config.controller
 * Date:2021年04月21日 11:40
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.config.controller;

import me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.config.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:NacosConsumerConfigController<br/>
 * Function: 调用客户端<br/>
 * Date:2021年04月21日 11:40<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@RefreshScope
@RestController
public class NacosConsumerConfigController {

    @Autowired
    private EchoService echoService;

    @Value("${user.name}")
    private String userName;

    @GetMapping(value = "/echo/user/name")
    public String echo() {
        return echoService.echo(userName);
    }
}