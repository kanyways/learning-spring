/**
 * Project Name:learning-spring
 * File Name:UndertowExtraConfiguration.java
 * Package Name:me.kany.project.learning.spring.config
 * Date:2021年06月10日 15:44
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.config;

import io.undertow.UndertowOptions;
import lombok.AllArgsConstructor;
import me.kany.project.learning.spring.provider.GracefulShutdownUndertowWrapper;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * ClassName:UndertowExtraConfiguration<br/>
 * Function: pes<br/>
 * Date:2021年06月10日 15:44<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
@Component
@AllArgsConstructor
public class UndertowExtraConfiguration {
    private final GracefulShutdownUndertowWrapper gracefulShutdownUndertowWrapper;

    /**
     * servletWebServerFactory: Undertow 是红帽公司开发的一款基于 NIO 的高性能 Web 嵌入式服务器<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2021/6/10 16:29
     */
    @Bean
    public UndertowServletWebServerFactory servletWebServerFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        factory.addDeploymentInfoCustomizers(deploymentInfo -> deploymentInfo.addOuterHandlerChainWrapper(gracefulShutdownUndertowWrapper));
        factory.addBuilderCustomizers(builder -> {
            builder.setServerOption(UndertowOptions.ENABLE_STATISTICS, true);
            // 开启HTTP2
            builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true);
        });
        return factory;
    }
}