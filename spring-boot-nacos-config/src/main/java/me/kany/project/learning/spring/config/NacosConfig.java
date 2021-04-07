/**
 * Project Name:learning-spring
 * File Name:NacosConfig.java
 * Package Name:me.kany.project.learning.spring.config
 * Date:2021年04月07日 14:34
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:NacosConfig<br/>
 * Function: Nacos Config<br/>
 * Date:2021年04月07日 14:34<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@Configuration
public class NacosConfig {
    @Value(value = "${nacos.name}")
    public String name;
}