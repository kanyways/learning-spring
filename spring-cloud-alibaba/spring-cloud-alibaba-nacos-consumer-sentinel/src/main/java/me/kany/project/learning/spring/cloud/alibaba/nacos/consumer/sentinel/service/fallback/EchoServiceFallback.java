/**
 * Project Name:spring-cloud-alibaba
 * File Name:EchoServiceFallback.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.sentinel.service.fallback
 * Date:2021年04月22日 15:37
 * Copyright (c) 2021, Kai.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.sentinel.service.fallback;

import me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.sentinel.service.EchoService;
import org.springframework.stereotype.Component;

/**
 * ClassName:EchoServiceFallback<br/>
 * Function: EchoService的fallback实现类<br/>
 * Date:2021年04月22日 15:37<br/>
 *
 * @author Kai.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@Component
public class EchoServiceFallback implements EchoService {
    @Override
    public String echo(String message) {
        return "服务异常，请稍后重试";
    }
}