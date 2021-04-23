/**
 * Project Name:spring-cloud-alibaba
 * File Name:EchoController.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.sentinel.controller
 * Date:2021年04月22日 15:33
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.sentinel.controller;

import me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.sentinel.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:EchoController<br/>
 * Function: 输出控制器<br/>
 * Date:2021年04月22日 15:33<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@RestController
@RefreshScope
public class EchoController {
    @Autowired
    private EchoService echoService;

    @Value("${user.name}")
    private String userName;

    @GetMapping(value = "/echo/user/name")
    public String echo() {
        return echoService.echo(userName);
    }
}