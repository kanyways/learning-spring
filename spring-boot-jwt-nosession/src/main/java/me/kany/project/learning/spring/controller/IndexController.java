/**
 * Project Name:learning-spring
 * File Name:IndexController.java
 * Package Name:me.kany.project.learning.spring.controller
 * Date:2020年03月13日 14:31
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.controller;

import me.kany.project.learning.spring.request.user.ReqUser;
import me.kany.project.learning.spring.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * ClassName:IndexController<br/>
 * Function: 首页控制器<br/>
 * Date:2020年03月13日 14:31<br/>
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
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserDetailsService userDetailsService;

    @RequestMapping("")
    public Object execute(@RequestBody ReqUser reqUser) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(reqUser.getUserName());
        //这里的认证信息应该是加密之前的。
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "123456");
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @RequestMapping("sss")
    public Object sss() {
        return Arrays.asList("测试", "sdsdsd");
    }
}
