/**
 * Project Name:learning-spring
 * File Name:TestUserService.java
 * Package Name:me.kany.project.learning.spring.service
 * Date:2019年10月25日 14:48
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * ClassName:TestUserService<br/>
 * Function: UserService测试版<br/>
 * Date:2019年10月25日 14:48<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUserService {
    @Resource
    private UsersService usersService;

    @Test
    public void queryUser() {
        log.info("查询的对象为：{}", usersService.getById(1));
    }
}
