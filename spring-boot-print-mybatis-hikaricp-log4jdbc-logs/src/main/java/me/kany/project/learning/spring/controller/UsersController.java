package me.kany.project.learning.spring.controller;


import me.kany.project.learning.spring.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Jason.Wang
 * @since 2019-10-25
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    /**
     * execute: 获取用户列表<br/>
     *
     * @return
     * @author Jason.Wang
     * @createTime 2019/10/31 10:06
     */
    @RequestMapping("")
    public Object execute() {
        return usersService.list();
    }

    /**
     * query: 查询单个用户<br/>
     *
     * @param id
     * @return
     * @author Jason.Wang
     * @createTime 2019/10/31 10:06
     */
    @RequestMapping(path = "{id}")
    public Object query(@PathVariable(name = "id") Long id) {
        return usersService.getById(id);
    }
}

