/**
 * Project Name:learning-spring
 * File Name:NacosConsumerFeignApplication.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.consumer
 * Date:2021年04月21日 11:28
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * ClassName:NacosConsumerFeignApplication<br/>
 * Function: 启动程序<br/>
 * Date:2021年04月21日 11:28<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NacosConsumerFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerFeignApplication.class, args);
    }
}