/**
 * Project Name:learning-spring
 * File Name:EchoController.java
 * Package Name:me.kany.project.learning.spring.cloud.dubbo.consumer.controller
 * Date:2021年04月23日 23:03
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.dubbo.consumer.controller;

import me.kany.project.learning.spring.cloud.dubbo.provider.api.EchoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.apache.dubbo.config.annotation.DubboReference;

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
@RestController
public class EchoController {

    @DubboReference(version="1.0.0")
    private EchoService echoService;

    @GetMapping(value = "/echo/{string}")
    public String echo(@PathVariable String string){
        return echoService.echo(string);
    }
}