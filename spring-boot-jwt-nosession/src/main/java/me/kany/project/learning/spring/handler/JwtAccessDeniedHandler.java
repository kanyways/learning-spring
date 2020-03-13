/**
 * Project Name:learning-spring
 * File Name:JwtAccessDeniedHandler.java
 * Package Name:me.kany.project.learning.spring.handler
 * Date:2020年03月13日 18:01
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.handler;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import me.kany.project.learning.spring.entity.BaseResponse;
import me.kany.project.learning.spring.entity.ConstantCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName:JwtAccessDeniedHandler<br/>
 * Function: asd<br/>
 * Date:2020年03月13日 18:01<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Log4j2
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        BaseResponse bre = new BaseResponse(ConstantCode.ACCESS_DENIED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(JSONObject.toJSONString(bre));
    }
}
