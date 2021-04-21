/**
 * Project Name:learning-spring
 * File Name:EchoService.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.feign.service
 * Date:2021年04月21日 11:33
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName:EchoService<br/>
 * Function: 输出接口<br/>
 * Date:2021年04月21日 11:33<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@FeignClient(value = "nacos-provider")
public interface EchoService {

    @GetMapping(value = "/echo/{message}")
    String echo(@PathVariable("message") String message);
}