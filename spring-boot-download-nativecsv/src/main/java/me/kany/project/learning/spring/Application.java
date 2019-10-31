/**
 * Project Name:learning-spring
 * File Name:Application.java
 * Package Name:me.kany.project.learning.spring
 * Date:2019年10月25日 11:01
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

/**
 * ClassName:Application<br/>
 * Function: 默认启动程序<br/>
 * Date:2019年10月25日 11:01<br/>
 * shi
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@SpringBootApplication
@MapperScan(basePackages = "me.kany.project.learning.spring.mapper")
public class Application extends SpringBootServletInitializer {
    /**
     * main: 启动类型<br/>
     *
     * @param args
     * @author Jason.Wang
     * @createTime 2019/4/22 21:38
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
