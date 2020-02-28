/**
 * Project Name:learning-spring
 * File Name:LoginController.java
 * Package Name:me.kany.project.learning.spring.shiro.controller.login
 * Date:2020年01月08日 15:43
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.shiro.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName:LoginController<br/>
 * Function: 登录控制器<br/>
 * Date:2020年01月08日 15:43<br/>
 *
 * @author Jason.Wang
 * @see
 * @since JDK1.8
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    /**
     * execute: 登录页面<br/>
     *
     * @return
     * @author Jason.Wang
     * @createTime 2020/1/8 15:44
     */
    @RequestMapping("")
    public String execute() {
        return "login/login";
    }
}
