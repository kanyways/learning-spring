/**
 * Project Name:learning-spring
 * File Name:NacosConsumerConfigController.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.feign.controller
 * Date:2021年04月21日 11:40
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.config.feign.controller;

import me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.config.feign.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:NacosConsumerFeignController<br/>
 * Function: 调用客户端<br/>
 * Date:2021年04月21日 11:40<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@RestController
public class NacosConsumerFeignController {

    @Autowired
    private EchoService echoService;

    @GetMapping(value = "/echo/hi")
    public String echo() {
        return echoService.echo("Hi Feign");
    }
}