/**
 * Project Name:learning-spring
 * File Name:EchoController.java
 * Package Name:me.kany.project.learning.spring.cloud.dubbo.consumer.controller
 * Date:2021年04月23日 23:03
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.dubbo.consumer.config.controller;

import me.kany.project.learning.spring.cloud.dubbo.provider.api.EchoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:EchoController<br/>
 * Function: 展示控制器<br/>
 * Date:2021年04月23日 23:03<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@RefreshScope
@RestController
public class EchoController {

    @DubboReference(version = "1.0.0")
    private EchoService echoService;

    @Value("${user.name}")
    private String userName;

    @GetMapping(value = "/echo/{string}")
    public String echo(@PathVariable String string) {
        return echoService.echo(string);
    }

    @GetMapping(value = "/echo/user/name")
    public String echo() {
        return echoService.echo(userName);
    }
}