/**
 * Project Name:learning-spring
 * File Name:JwtAuthenticationEntryPoint.java
 * Package Name:me.kany.project.learning.spring.handler
 * Date:2020年03月13日 14:46
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.handler;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import me.kany.project.learning.spring.entity.BaseResponse;
import me.kany.project.learning.spring.entity.ConstantCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName:JwtAuthenticationEntryPoint<br/>
 * Function: JwtAuthenticationEntryPoint<br/>
 * Date:2020年03月13日 14:46<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Log4j2
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // This is invoked when user tries to access a secured REST resource without supplying any credentials
        // We should just send a 401 Unauthorized response because there is no 'login page' to redirect to
        response.setContentType("application/json;charset=UTF-8");
        BaseResponse bre = new BaseResponse(ConstantCode.ACCESS_DENIED);
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(JSONObject.toJSONString(bre));
    }
}
