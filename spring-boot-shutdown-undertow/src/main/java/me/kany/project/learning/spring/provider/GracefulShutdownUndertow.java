/**
 * Project Name:learning-spring
 * File Name:GracefulShutdownUndertow.java
 * Package Name:me.kany.project.learning.spring.provider
 * Date:2021年06月10日 15:34
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.provider;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.ConnectorStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * ClassName:GracefulShutdownUndertow<br/>
 * Function: 优雅关闭Spring Boot之Undertow方式<br/>
 * Date:2021年06月10日 15:34<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@Component
public class GracefulShutdownUndertow implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private GracefulShutdownUndertowWrapper gracefulShutdownUndertowWrapper;

    @Autowired
    private ServletWebServerApplicationContext context;

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        gracefulShutdownUndertowWrapper.getGracefulShutdownHandler().shutdown();
        try {
            UndertowServletWebServer webServer = (UndertowServletWebServer) context.getWebServer();
            Field field = webServer.getClass().getDeclaredField("undertow");
            field.setAccessible(true);
            Undertow undertow = (Undertow) field.get(webServer);
            List<Undertow.ListenerInfo> listenerInfo = undertow.getListenerInfo();
            Undertow.ListenerInfo listener = listenerInfo.get(0);
            ConnectorStatistics connectorStatistics = listener.getConnectorStatistics();
            while (connectorStatistics.getActiveConnections() > 0) {
            }
        } catch (Exception e) {
            // Application Shutdown
        }
    }
}