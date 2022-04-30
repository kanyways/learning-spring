/**
 * Project Name:learning-spring
 * File Name:SpringBootDemoApplication.java
 * Package Name:me.kany.project.learning.spring
 * Date:2022-04-30 5:53 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName: SpringBootDemoApplication<br/>
 * Function: 主要启动程序<br/>
 * Date: 2022-04-30 5:53 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
@SpringBootApplication
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

}
