/**
 * Project Name:learning-spring
 * File Name:IndexController.java
 * Package Name:me.kany.project.learning.spring.controller
 * Date:2020年03月16日 10:39
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.controller;

import me.kany.project.learning.spring.entity.BaseResponse;
import me.kany.project.learning.spring.entity.ConstantCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:IndexController<br/>
 * Function: 首页控制器<br/>
 * Date:2020年03月16日 10:39<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@RestController
@RequestMapping("/")
public class IndexController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @RequestMapping("")
    public BaseResponse execute() {
        BaseResponse baseResponse = new BaseResponse(ConstantCode.SUCCESS);
        baseResponse.setData(stringRedisTemplate.getClientList());
        return baseResponse;
    }
}
