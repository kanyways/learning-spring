/**
 * Project Name:learning-spring
 * File Name:IndexController.java
 * Package Name:me.kany.project.learning.spring.controller
 * Date:2022-11-26 1:12 AM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ClassName: IndexController<br/>
 * Function: 首页控制器<br/>
 * Date: 2022-11-26 1:12 AM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
@Controller
public class IndexController {
    @GetMapping(value = "/")
    public String execute() {
        return "index";
    }
}
