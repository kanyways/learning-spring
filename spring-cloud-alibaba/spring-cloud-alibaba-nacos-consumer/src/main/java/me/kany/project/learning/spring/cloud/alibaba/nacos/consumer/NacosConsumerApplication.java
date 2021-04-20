/**
 * Project Name:learning-spring
 * File Name:NacosConsumerApplication.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.consumer
 * Date:2021年04月20日 19:11
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * ClassName:NacosConsumerApplication<br/>
 * Function: 消费者<br/>
 * Date:2021年04月20日 19:11<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
@EnableDiscoveryClient
@SpringBootApplication
public class NacosConsumerApplication {

    /**
     * main: 启动类型<br/>
     *
     * @param args
     * @author Jason.Wang
     * @date 2021/4/20 19:18
     */
    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerApplication.class, args);
    }
}