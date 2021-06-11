/**
 * Project Name:learning-spring
 * File Name:GracefulShutdownTomcat.java
 * Package Name:me.kany.project.learning.spring.provider
 * Date:2021年06月10日 14:33
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.provider;

import lombok.extern.log4j.Log4j2;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:GracefulShutdownTomcat<br/>
 * Function: 优雅关闭Spring Boot之Tomcat方式<br/>
 * Date:2021年06月10日 13:59<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@Log4j2
@Component
public class GracefulShutdownTomcat implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
    private volatile Connector connector;
    /**
     * 停止等待时间
     */
    private final int waitTime = 30;

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        this.connector.pause();
        Executor executor = this.connector.getProtocolHandler().getExecutor();
        if (executor instanceof ThreadPoolExecutor) {
            try {
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                threadPoolExecutor.shutdown();
                if (!threadPoolExecutor.awaitTermination(waitTime, TimeUnit.SECONDS)) {
                    log.debug("Tomcat thread pool did not shut down gracefully within " + waitTime + " seconds. Proceeding with forceful shutdown");
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * servletContainer: 定义优雅停止Spring Boot的接口<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2021/6/10 14:01
     */
    @Bean
    public ConfigurableServletWebServerFactory servletContainer(final GracefulShutdownTomcat gracefulShutdownTomcat) {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(gracefulShutdownTomcat);
        return tomcat;
    }
}