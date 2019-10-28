package me.kany.project.learning.spring.controller;


import me.kany.project.learning.spring.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    private IUsersService IUsersService;

    @ResponseBody
    @RequestMapping("")
    public Object execute() {
        return IUsersService.list();
    }

    @ResponseBody
    @RequestMapping(path = "{id}")
    public Object query(@PathVariable(name = "id") Long id) {
        return IUsersService.getById(id);
    }
}

