/**
 * Project Name:learning-spring
 * File Name:TokenController.java
 * Package Name:me.kany.project.learning.spring.controller
 * Date:2021年06月25日 15:05
 * Copyright (c) 2021, Kai.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * ClassName:TokenController<br/>
 * Function: Token控制器<br/>
 * Date:2021年06月25日 15:05<br/>
 *
 * @author Kai.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@RestController
@RequestMapping("/api/token")
public class TokenController {
    /**
     * authorize: 返回Token<br/>
     *
     * @return
     * @author Kai.Wang
     * @date 2021/6/25 15:07
     */
    @PostMapping("authorize")
    public String authorize() {
        return UUID.randomUUID().toString();
    }
}