/**
 * Project Name:learning-spring
 * File Name:NacosProviderApplication.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.provider
 * Date:2021年04月20日 18:20
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * ClassName:NacosProviderApplication<br/>
 * Function: 服务提供者<br/>
 * Date:2021年04月20日 18:20<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
@EnableDiscoveryClient
@SpringBootApplication
public class NacosProviderApplication {

    /**
     * main: 启动类型<br/>
     *
     * @param args
     * @author Jason.Wang
     * @date 2021/4/20 19:18
     */
    public static void main(String[] args) {
        SpringApplication.run(NacosProviderApplication.class, args);
    }
}