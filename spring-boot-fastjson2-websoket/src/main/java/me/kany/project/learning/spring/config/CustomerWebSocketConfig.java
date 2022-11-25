/**
 * Project Name:learning-spring
 * File Name:CustomerWebSocketConfig.java
 * Package Name:me.kany.project.learning.spring.config
 * Date:2022-11-26 12:44 AM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.config;

import com.alibaba.fastjson2.support.spring.websocket.sockjs.FastjsonSockJsMessageCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * ClassName: CustomerWebSocketConfig<br/>
 * Function: 自定义WebSocket文件<br/>
 * Date: 2022-11-26 12:44 AM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
@Slf4j
@Configuration
public class CustomerWebSocketConfig implements WebSocketConfigurer {

    /**
     * @param webSocketHandlerRegistry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        log.info("初始化路径拦截...");
        webSocketHandlerRegistry.addHandler(new CustomerTextWebSocketHandler(), "/websocket/*").withSockJS()
            .setMessageCodec(new FastjsonSockJsMessageCodec());
    }

    /**
     * ServerEndpointExporter 作用
     * <p>
     * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
