/**
 * Project Name:learning-spring
 * File Name:XssHttpServletRequestWrapper.java
 * Package Name:me.kany.project.learning.spring.wrapper
 * Date:2021年01月21日 15:52
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.wrapper;

import lombok.extern.log4j.Log4j2;
import me.kany.project.learning.spring.utils.JsoupUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:XssHttpServletRequestWrapper<br/>
 * Function: XSS的自定义过滤器<br/>
 * Date:2021年01月21日 15:52<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@Log4j2
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    HttpServletRequest orgRequest;

    String encoding;

    JsoupUtil jsoupUtil;

    private final static String JSON_CONTENT_TYPE = "application/json";

    private final static String CONTENT_TYPE = "Content-Type";


    /**
     * @param request     HttpServletRequest
     * @param encoding    编码
     * @param excludeTags 例外的特定标签
     * @param includeTags 需要过滤的标签
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request, String encoding, List<String> excludeTags, List<String> includeTags) {
        super(request);
        orgRequest = request;
        this.encoding = encoding;
        this.jsoupUtil = JsoupUtil.create(excludeTags, includeTags);
    }

    /**
     * @param request  HttpServletRequest
     * @param encoding 编码
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request, String encoding) {
        this(request, encoding, null, null);
    }

    private String xssFilter(String input) {
        return jsoupUtil.clean(input);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 非json处理
        if (!JSON_CONTENT_TYPE.equalsIgnoreCase(super.getHeader(CONTENT_TYPE))) {
            return super.getInputStream();
        }

        String body = null;

        try (InputStream in = super.getInputStream()) {
            body = IOUtils.toString(in, encoding);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        //空串处理直接返回
        if (StringUtils.isBlank(body)) {
            return super.getInputStream();
        }

        // xss过滤
        body = xssFilter(body);
        return new RequestCachingInputStream(body.getBytes(encoding));

    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(xssFilter(name));
        if (StringUtils.isNotBlank(value)) {
            value = xssFilter(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameters = super.getParameterValues(name);
        if (parameters == null || parameters.length == 0) {
            return null;
        }

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = xssFilter(parameters[i]);
        }
        return parameters;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = xssFilter(values[i]);
            }
            map.put(key, values);
        }
        return map;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(xssFilter(name));
        if (StringUtils.isNotBlank(value)) {
            value = xssFilter(value);
        }
        return value;
    }

    /**
     * <b>
     * #获取最原始的request
     * </b>
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * <b>
     * #获取最原始的request
     * </b>
     *
     * @param request HttpServletRequest
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
        if (request instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) request).getOrgRequest();
        }
        return request;
    }

    /**
     * <pre>
     * servlet中inputStream只能一次读取，后续不能再次读取inputStream
     * xss过滤body后，重新把流放入ServletInputStream中
     * </pre>
     */
    private static class RequestCachingInputStream extends ServletInputStream {
        private final ByteArrayInputStream inputStream;

        public RequestCachingInputStream(byte[] bytes) {
            inputStream = new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
        }
    }
}