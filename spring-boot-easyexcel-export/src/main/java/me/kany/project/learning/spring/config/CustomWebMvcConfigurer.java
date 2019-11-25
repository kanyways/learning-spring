/**
 * Project Name:learning-spring
 * File Name:CustomWebMvcConfigurer.java
 * Package Name:me.kany.project.learning.spring.config
 * Date:2019年11月25日 11:48
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName:CustomWebMvcConfigurer<br/>
 * Function: 自定义Mvc配置<br/>
 * Date:2019年11月25日 11:48<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {
    @Bean
    public LocaleResolver localeResolver() {
        return new CustomLocaleResolver();
    }
}
