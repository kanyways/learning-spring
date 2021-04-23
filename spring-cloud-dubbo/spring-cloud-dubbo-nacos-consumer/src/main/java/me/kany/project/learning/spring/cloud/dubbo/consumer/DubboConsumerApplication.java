/**
 * Project Name:learning-spring
 * File Name:DubboConsumerApplication.java
 * Package Name:me.kany.project.learning.spring.cloud.dubbo.consumer
 * Date:2021年04月23日 22:57
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.dubbo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName:DubboConsumerApplication<br/>
 * Function: Dubbo消费者<br/>
 * Date:2021年04月23日 22:57<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@SpringBootApplication
public class DubboConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class, args);
    }
}