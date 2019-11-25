/**
 * Project Name:learning-spring
 * File Name:CustomLocaleResolver.java
 * Package Name:me.kany.project.learning.spring.config
 * Date:2019年11月25日 11:42
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * ClassName:CustomLocaleResolver<br/>
 * Function: 自定义的解析<br/>
 * Date:2019年11月25日 11:42<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public class CustomLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String lang = httpServletRequest.getParameter("lang");
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(lang)) {
            /**
             * @FIXME 这个地方存在错误
             */
            String[] split = lang.split("_");
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
