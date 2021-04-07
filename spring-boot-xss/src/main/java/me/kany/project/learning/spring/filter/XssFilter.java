/**
 * Project Name:learning-spring
 * File Name:XssFilter.java
 * Package Name:me.kany.project.learning.spring.filter
 * Date:2021年01月21日 15:49
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.filter;

import me.kany.project.learning.spring.wrapper.XssHttpServletRequestWrapper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:XssFilter<br/>
 * Function: 创建XSSFilter<br/>
 * Date:2021年01月21日 15:49<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
public class XssFilter implements Filter {

    /**
     * 例外urls
     */
    private List<String> excludeUrls = new ArrayList<>();

    /**
     * 例外标签
     */
    private List<String> excludeTags = new ArrayList<>();

    /**
     * 需要过滤标签
     */
    private List<String> includeTags = new ArrayList<>();

    /**
     * 开关
     */
    public boolean enabled = false;

    /**
     * 编码
     */
    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String enabledStr = filterConfig.getInitParameter("enabled");
        String excludeUrlStr = filterConfig.getInitParameter("excludeUrls");
        String excludeTagStr = filterConfig.getInitParameter("excludeTagStr");
        String includeTagStr = filterConfig.getInitParameter("excludeTagStr");
        String encodingStr = filterConfig.getInitParameter("encoding");

        if (StringUtils.isNotEmpty(excludeUrlStr)) {
            String[] url = excludeUrlStr.split(",");
            Collections.addAll(this.excludeUrls, url);
        }

        if (StringUtils.isNotEmpty(excludeTagStr)) {
            String[] url = excludeTagStr.split(",");
            Collections.addAll(this.excludeTags, url);
        }

        if (StringUtils.isNotEmpty(excludeTagStr)) {
            String[] url = excludeTagStr.split(",");
            Collections.addAll(this.includeTags, url);
        }

        if (StringUtils.isNotEmpty(enabledStr)) {
            this.enabled = Boolean.parseBoolean(enabledStr);
        }

        if (StringUtils.isNotEmpty(encodingStr)) {
            this.encoding = encodingStr;
        }

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (handleExcludeUrls(req, resp)) {
            chain.doFilter(request, response);
            return;
        }

        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request, encoding, excludeTags, includeTags);
        chain.doFilter(xssRequest, response);

    }

    private boolean handleExcludeUrls(HttpServletRequest request, HttpServletResponse response) {
        if (!enabled) {
            return true;
        }
        if (excludeUrls == null || excludeUrls.isEmpty()) {
            return false;
        }
        String url = request.getServletPath();
        for (String pattern : excludeUrls) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }
}