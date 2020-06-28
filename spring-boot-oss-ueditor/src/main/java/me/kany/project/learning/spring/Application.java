/**
 * Project Name:learning-spring
 * File Name:Application.java
 * Package Name:me.kany.project.learning.spring
 * Date:2020年06月28日 11:05
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * ClassName:Application<br/>
 * Function: 启动程序<br/>
 * Date:2020年06月28日 11:05<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    /**
     * main: 启动类型<br/>
     *
     * @param args
     * @author Jason.Wang
     * @createTime 2020-06-28 11:10:10
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

    /**
     * getObjectMapper: 配置时间格式<br/>
     *
     * @return
     * @author Jason.Wang
     * @createTime 2020-06-28 11:10:10
     */
    @Bean(name = "mapperObject")
    public ObjectMapper getObjectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
}
