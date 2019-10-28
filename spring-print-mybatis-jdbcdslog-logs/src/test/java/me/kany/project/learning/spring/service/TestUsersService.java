/**
 * Project Name:learning-spring
 * File Name:TestUsersService.java
 * Package Name:me.kany.project.learning.spring.service
 * Date:2019年10月25日 15:04
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import me.kany.project.learning.spring.entity.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ClassName:TestUsersService<br/>
 * Function: UsersService的测试类<br/>
 * Date:2019年10月25日 15:04<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring.xml"})
public class TestUsersService {
    @Autowired
    private IUsersService IUsersService;

    @Test
    public void queryUser() {
        log.info("查询的对象为：{}", IUsersService.getById(1));
    }
}