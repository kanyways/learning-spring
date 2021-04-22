/**
 * Project Name:learning-spring
 * File Name:NacosConsumerController.java
 * Package Name:me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.controller
 * Date:2021年04月21日 11:02
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.alibaba.nacos.consumer.config.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ClassName:NacosConsumerController<br/>
 * Function: 控制器<br/>
 * Date:2021年04月21日 11:02<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@RestController
public class NacosConsumerController {
    /**
     * 需要创建实例
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 自动注入Config中的对象
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 读取配置文件中的应用名称
     */
    @Value("${spring.application.name}")
    private String appName;

    @GetMapping(value = "/echo/app/name")
    public String echo() {
        //使用 LoadBalanceClient 和 RestTemplate 结合的方式来访问
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-provider");
        // 这个地址需要与服务提供者路径一致
        String url = String.format("http://%s:%s/echo/%s", serviceInstance.getHost(), serviceInstance.getPort(), appName);
        return restTemplate.getForObject(url, String.class);
    }
}