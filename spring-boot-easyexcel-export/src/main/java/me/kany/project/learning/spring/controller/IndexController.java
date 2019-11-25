/**
 * Project Name:learning-spring
 * File Name:IndexController.java
 * Package Name:me.kany.project.learning.spring.controller
 * Date:2019年11月25日 10:19
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.controller;

import me.kany.project.learning.spring.entity.Users;
import me.kany.project.learning.spring.service.UsersService;
import me.kany.project.learning.spring.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ClassName:IndexController<br/>
 * Function: 首页控制器<br/>
 * Date:2019年11月25日 10:19<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    protected HttpServletRequest request;
    @Resource
    private UsersService usersService;

    @RequestMapping("")
    public String execute() {
        return "front/index";
    }

    @RequestMapping("/download")
    public void download(HttpServletResponse response) {
        List<Users> users = usersService.list();
        try {
            ExcelUtils.writeExcel(response, users, "用户信息", Users.class, "用户信息表");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
