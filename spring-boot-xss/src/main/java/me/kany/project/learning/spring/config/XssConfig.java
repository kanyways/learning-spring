/**
 * Project Name:learning-spring
 * File Name:XssConfig.java
 * Package Name:me.kany.project.learning.spring.config
 * Date:2021年01月21日 16:00
 * Copyright (c) 2021, Kai.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.config;

import com.google.common.collect.Maps;
import me.kany.project.learning.spring.filter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * ClassName:XssConfig<br/>
 * Function: 配置XSS的类<br/>
 * Date:2021年01月21日 16:00<br/>
 *
 * @author Kai.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@Configuration
public class XssConfig {

    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = Maps.newHashMap();
        initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
        initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}