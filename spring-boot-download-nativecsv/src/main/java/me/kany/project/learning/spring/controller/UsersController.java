package me.kany.project.learning.spring.controller;


import me.kany.project.learning.spring.entity.Users;
import me.kany.project.learning.spring.service.UsersService;
import me.kany.project.learning.spring.utils.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @ResponseBody
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
    @ResponseBody
    @RequestMapping(path = "{id}")
    public Object query(@PathVariable(name = "id") Long id) {
        return usersService.getById(id);
    }

    /**
     * download: 使用CSVUtils<br/>
     *
     * @param response
     * @author Jason.Wang
     * @createTime 2019/10/31 10:06
     */
    @RequestMapping(path = "download")
    public void download(HttpServletResponse response) {
        String fileName = "user-list.csv";
        List<Users> users = usersService.list();
        List<List<String>> data = new ArrayList<>();
        users.forEach(user -> {
            data.add(Arrays.asList(user.getUid().toString(), user.getLoginName(), user.getMobile()));
        });
        CSVUtils.exportCsv(response, Arrays.asList("用户Id", "登录名称", "手机号"), data, fileName);
    }
}

