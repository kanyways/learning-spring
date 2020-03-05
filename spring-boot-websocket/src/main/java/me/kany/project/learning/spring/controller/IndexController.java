/**
 * Project Name:learning-spring
 * File Name:IndexController.java
 * Package Name:me.kany.project.learning.spring.controller
 * Date:2020年03月05日 11:04
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName:IndexController<br/>
 * Function: 首页控制器<br/>
 * Date:2020年03月05日 11:04<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Controller
@RequestMapping("/")
public class IndexController {

    /**
     * execute: 首页方式<br/>
     *
     * @return
     * @author Jason.Wang
     * @createTime 2020/3/5 11:06
     */
    @RequestMapping("")
    public String execute() {
        return "index";
    }
}
