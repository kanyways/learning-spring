package me.kany.project.learning.spring.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.kany.project.learning.spring.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jason.Wang
 */
@Slf4j
@RestController
public class IndexController {

    @PostMapping
    public Object execute(@RequestBody User user) {
        log.info("{}", JSONObject.toJSONString(user));
        return user;
    }

    @PostMapping("list")
    public Object executeList(@RequestBody List<User> users) {
        log.info("{}", JSONObject.toJSONString(users));
        return users;
    }

    @PostMapping("array")
    public Object executeList(@RequestBody User[] users) {
        log.info("{}", JSONObject.toJSONString(users));
        return users;
    }

    @PostMapping("form")
    public Object form(User user) {
        log.info("{}", JSONObject.toJSONString(user));
        return user;
    }

    @PostMapping("json")
    public Object json(@RequestBody JSONObject jsonObject) {
        return jsonObject.getJSONArray("data").toJavaList(User.class);
    }
}
