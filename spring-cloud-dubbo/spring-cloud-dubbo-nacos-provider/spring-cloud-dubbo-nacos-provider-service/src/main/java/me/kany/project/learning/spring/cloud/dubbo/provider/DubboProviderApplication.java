/**
 * Project Name:learning-spring
 * File Name:DubboProviderApplication.java
 * Package Name:me.kany.project.learning.spring.cloud.dubbo.provider
 * Date:2021年04月23日 15:58
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.dubbo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName:DubboProviderApplication<br/>
 * Function: 启动程序<br/>
 * Date:2021年04月23日 15:58<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@SpringBootApplication
public class DubboProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
    }
}