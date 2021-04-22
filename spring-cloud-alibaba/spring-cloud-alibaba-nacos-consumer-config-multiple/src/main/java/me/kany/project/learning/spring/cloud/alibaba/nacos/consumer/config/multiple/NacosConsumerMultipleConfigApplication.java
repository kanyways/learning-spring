/**
 * Project Name:learning-spring
 * File Name:NacosConsumerMultipleConfigApplication.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.config.multiple
 * Date:2021年04月22日 12:54
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.config.multiple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * ClassName:NacosConsumerMultipleConfigApplication<br/>
 * Function: 远端读取配置中心<br/>
 * Date:2021年04月22日 12:54<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NacosConsumerMultipleConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerMultipleConfigApplication.class, args);
    }
}