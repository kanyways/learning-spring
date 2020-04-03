/**
 * Project Name:learning-spring
 * File Name:HttpRestTools.java
 * Package Name:me.kany.project.learning.spring
 * Date:2020年04月03日 10:37
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * ClassName:HttpRestTools<br/>
 * Function: 创建工具类<br/>
 * Date:2020年04月03日 10:37<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Log4j2
public class HttpRestTools {
    private static Integer httpTimeOut = 5000;

    /**
     * getHttpFactoryForDeploy: 构建请求工厂<br/>
     *
     * @return
     * @author Jason.Wang
     * @createTime 2020/4/1 21:47
     */
    public static SimpleClientHttpRequestFactory getHttpFactoryForDeploy() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        return factory;
    }

    /**
     * getRestTemplate: 创建请求模版<br/>
     *
     * @return
     * @author Jason.Wang
     * @createTime 2020/4/1 21:47
     */
    public static RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory factory = getHttpFactoryForDeploy();
        // ms
        factory.setReadTimeout(httpTimeOut);
        factory.setConnectTimeout(httpTimeOut);
        return new RestTemplate(factory);
    }

    /**
     * buildHttpEntity: 构建Http请求<br/>
     *
     * @param param
     * @return
     * @author Jason.Wang
     * @createTime 2020/4/1 21:24
     */
    public static HttpEntity buildHttpEntity(Object param) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String paramStr = null;
        if (Objects.nonNull(param)) {
            paramStr = JSON.toJSONString(param);
        }
        HttpEntity requestEntity = new HttpEntity(paramStr, headers);
        return requestEntity;
    }

    /**
     * buildHttpEntityForm: 构建Http请求<br/>
     *
     * @param param
     * @return
     * @author Jason.Wang
     * @createTime 2020/4/1 21:24
     */
    public static HttpEntity buildHttpEntityForm(Map<String, String> param) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        param.keySet().forEach(key -> {
            params.add(key, param.get(key));
        });
        //提交请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        return requestEntity;
    }

    /**
     * getForEntity: get from api for entity<br/>
     *
     * @param uri
     * @param clazz
     * @param <T>
     * @return
     * @author Jason.Wang
     * @createTime 2020/4/1 21:28
     */
    public static <T> T getForEntity(String uri, Class<T> clazz) {
        return restTemplateExchange(uri, HttpMethod.GET, null, clazz);
    }

    /**
     * postForEntity: post from api for entity<br/>
     *
     * @param uri
     * @param params
     * @param clazz
     * @param <T>
     * @return
     * @author Jason.Wang
     * @createTime 2020/4/1 21:28
     */
    public static <T> T postForEntity(String uri, Object params, Class<T> clazz) {
        return restTemplateExchange(uri, HttpMethod.POST, params, clazz);
    }

    /**
     * postForEntityForm: post from api for entity<br/>
     *
     * @param uri
     * @param params
     * @param clazz
     * @param <T>
     * @return
     * @author Jason.Wang
     * @createTime 2020/4/1 21:28
     */
    public static <T> T postForEntityForm(String uri, Map<String, String> params, Class<T> clazz) {
        return restTemplateExchangeForm(uri, HttpMethod.POST, params, clazz);
    }

    /**
     * deleteForEntity: delete from api for entity<br/>
     *
     * @param uri
     * @param params
     * @param clazz
     * @param <T>
     * @return
     * @author Jason.Wang
     * @createTime 2020/4/1 21:29
     */
    public static <T> T deleteForEntity(String uri, Object params, Class<T> clazz) {
        return restTemplateExchange(uri, HttpMethod.DELETE, params, clazz);
    }

    /**
     * restTemplateExchange: restTemplate exchange<br/>
     *
     * @param url
     * @param method
     * @param param
     * @param clazz
     * @param <T>
     * @return
     * @author Jason.Wang
     * @createTime 2020/4/1 21:37
     */
    private static <T> T restTemplateExchange(String url, HttpMethod method, Object param, Class<T> clazz) {
        RestTemplate restTemplate = getRestTemplate();
        try {
            // build entity
            HttpEntity entity = buildHttpEntity(param);
            if (null == restTemplate) {
                log.error("fail restTemplateExchange, rest is null. url:{}", url);
                throw new RuntimeException();
            }
            ResponseEntity<T> response = restTemplate.exchange(url, method, entity, clazz);
            return response.getBody();
        } catch (ResourceAccessException ex) {
            log.warn("fail restTemplateExchange", ex);
        } catch (HttpStatusCodeException e) {
            JSONObject error = JSONObject.parseObject(e.getResponseBodyAsString());
            log.error("http request fail. error:{}", JSON.toJSONString(error));
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * restTemplateExchangeForm: restTemplate exchange<br/>
     *
     * @param url
     * @param method
     * @param param
     * @param clazz
     * @param <T>
     * @return
     * @author Jason.Wang
     * @createTime 2020/4/1 21:37
     */
    private static <T> T restTemplateExchangeForm(String url, HttpMethod method, Map<String, String> param, Class<T> clazz) {
        RestTemplate restTemplate = getRestTemplate();
        try {
            // build entity
            HttpEntity<MultiValueMap<String, String>> entity = buildHttpEntityForm(param);
            if (null == restTemplate) {
                log.error("fail restTemplateExchange, rest is null. url:{}", url);
                throw new RuntimeException();
            }
            ResponseEntity<T> response = restTemplate.exchange(url, method, entity, clazz);
            return response.getBody();
        } catch (ResourceAccessException ex) {
            log.warn("fail restTemplateExchange", ex);
        } catch (HttpStatusCodeException e) {
            JSONObject error = JSONObject.parseObject(e.getResponseBodyAsString());
            log.error("http request fail. error:{}", JSON.toJSONString(error));
            throw new RuntimeException(e);
        }
        return null;
    }
}
