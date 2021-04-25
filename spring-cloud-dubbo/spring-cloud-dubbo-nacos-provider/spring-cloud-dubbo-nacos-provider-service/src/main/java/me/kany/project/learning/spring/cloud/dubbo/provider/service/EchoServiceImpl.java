/**
 * Project Name:learning-spring
 * File Name:EchoServiceImpl.java
 * Package Name:me.kany.project.learning.spring.cloud.dubbo.provider.service
 * Date:2021年04月23日 15:58
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.dubbo.provider.service;

import me.kany.project.learning.spring.cloud.dubbo.provider.api.EchoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

/**
 * ClassName:EchoServiceImpl<br/>
 * Function: 实现类<br/>
 * Date:2021年04月23日 15:58<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@DubboService(version = "1.0.0")
public class EchoServiceImpl implements EchoService {

    @Value("${dubbo.protocol.port}")
    private String port;

    @Override
    public String echo(String string) {
        return "Dubbo Provider " + string + " port: " + port;
    }
}