/**
 * Project Name:learning-spring
 * File Name:NacosConsumerConfiguration.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.config
 * Date:2021年04月21日 11:01
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.config.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * ClassName:NacosConsumerConfiguration<br/>
 * Function: 配置中心<br/>
 * Date:2021年04月21日 11:01<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@Configuration
public class NacosConsumerConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}