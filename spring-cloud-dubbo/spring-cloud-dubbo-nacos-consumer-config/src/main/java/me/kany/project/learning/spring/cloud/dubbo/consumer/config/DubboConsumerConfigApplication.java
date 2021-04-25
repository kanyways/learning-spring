/**
 * Project Name:learning-spring
 * File Name:DubboConsumerConfigApplication.java
 * Package Name:me.kany.project.learning.spring.cloud.dubbo.consumer.config
 * Date:2021年04月25日 12:06
 * Copyright (c) 2021, Kai.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.dubbo.consumer.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName:DubboConsumerConfigApplication<br/>
 * Function: 远端读取配置文件<br/>
 * Date:2021年04月25日 12:06<br/>
 *
 * @author Kai.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@SpringBootApplication
public class DubboConsumerConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerConfigApplication.class, args);
    }
}