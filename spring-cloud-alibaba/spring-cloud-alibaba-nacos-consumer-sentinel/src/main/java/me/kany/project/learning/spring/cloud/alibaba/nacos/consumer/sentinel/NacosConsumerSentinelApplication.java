/**
 * Project Name:spring-cloud-alibaba
 * File Name:NacosConsumerSentinelApplication.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.sentinel
 * Date:2021年04月22日 15:28
 * Copyright (c) 2021, Kai.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * ClassName:NacosConsumerSentinelApplication<br/>
 * Function: 使用Sentinel接管熔断<br/>
 * Date:2021年04月22日 15:28<br/>
 *
 * @author Kai.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NacosConsumerSentinelApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerSentinelApplication.class, args);
    }
}