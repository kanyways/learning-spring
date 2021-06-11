/**
 * Project Name:learning-spring
 * File Name:GracefulShutdownUndertowWrapper.java
 * Package Name:me.kany.project.learning.spring.provider
 * Date:2021年06月10日 15:35
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.provider;

import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.GracefulShutdownHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * ClassName:GracefulShutdownUndertowWrapper<br/>
 * Function: 自定义实现优雅关机之UndertowWrapper<br/>
 * Date:2021年06月10日 15:35<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@Component
@Getter
public class GracefulShutdownUndertowWrapper implements HandlerWrapper {
    private GracefulShutdownHandler gracefulShutdownHandler;

    /**
     * wrap: 构建单例获取gracefulShutdownHandler对象<br/>
     *
     * @param handler
     * @return
     * @author Jason.Wang
     * @date 2021/6/10 15:36
     */
    @Override
    public HttpHandler wrap(HttpHandler handler) {
        if (gracefulShutdownHandler == null) {
            this.gracefulShutdownHandler = new GracefulShutdownHandler(handler);
        }
        return gracefulShutdownHandler;
    }
}