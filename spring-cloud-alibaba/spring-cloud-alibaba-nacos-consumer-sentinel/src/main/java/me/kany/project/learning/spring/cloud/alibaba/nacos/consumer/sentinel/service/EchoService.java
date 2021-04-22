/**
 * Project Name:spring-cloud-alibaba
 * File Name:EchoService.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.sentinel.service
 * Date:2021年04月22日 15:35
 * Copyright (c) 2021, Kai.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.sentinel.service;

import me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.sentinel.service.fallback.EchoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * InterfaceName:EchoService<br/>
 * Function: Feign调用的接口服务<br/>
 * Date:2021年04月22日 15:35<br/>
 *
 * @author Kai.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@FeignClient(value = "nacos-provider",fallback = EchoServiceFallback.class)
public interface EchoService {

    @GetMapping(value = "/echo/{message}")
    String echo(@PathVariable("message") String message);
}