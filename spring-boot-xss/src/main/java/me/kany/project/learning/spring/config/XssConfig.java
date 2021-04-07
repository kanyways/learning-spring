/**
 * Project Name:learning-spring
 * File Name:XssConfig.java
 * Package Name:me.kany.project.learning.spring.config
 * Date:2021年01月21日 16:00
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.config;

import com.google.common.collect.Maps;
import me.kany.project.learning.spring.filter.XssFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:XssConfig<br/>
 * Function: 配置XSS的类<br/>
 * Date:2021年01月21日 16:00<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@Configuration
public class XssConfig {

    @Value("${xss.enabled:true}")
    private String enabled;

    @Value("${xss.excludes:}")
    private String excludes;

    @Value("${xss.includes$:}")
    private String includes;

    @Value("${xss.urlPatterns:/*}")
    private String urlPatterns;

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistrationBean() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(urlPatterns.split(","));
        registration.setName("XssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", excludes);
        initParameters.put("includes", excludes);
        initParameters.put("enabled", enabled);
        registration.setInitParameters(initParameters);
        return registration;
    }
}