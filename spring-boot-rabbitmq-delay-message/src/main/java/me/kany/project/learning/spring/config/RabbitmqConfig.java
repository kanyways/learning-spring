/**
 * Project Name:learning-spring
 * File Name:RabbitmqConfig.java
 * Package Name:me.kany.project.learning.spring.config
 * Date:2022-10-15 6:28 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.config;

import me.kany.project.learning.spring.constant.Constants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: RabbitmqConfig<br/>
 * Function: RabbitmqConfig 配置<br/>
 * Date: 2022-10-15 6:28 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
@Configuration
public class RabbitmqConfig {

    /**
     * messageConverter: 序列化配置<br/>
     *
     * @return
     * @author Jason.Wang
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * consumerDelayDirectExchange: 创建一个延迟消息<br/>
     *
     * @return
     * @author Jason.Wang
     */
    @Bean
    public CustomExchange consumerDelayDirectExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(Constants.CONSUMER_DELAY_EXCHANGE_NAME, Constants.EXCHANGE_TYPE_DELAYED_MESSAGE, true,
            false, arguments);
    }

    /**
     * consumerDelayDirectQueue: 延迟发送的队列<br/>
     *
     * @return
     * @author Jason.Wang
     */
    @Bean
    public Queue consumerDelayDirectQueue() {
        return QueueBuilder.durable(Constants.CONSUMER_DELAY_QUEUE_NAME).build();
    }

    /**
     * consumerDelayExchangeBindOneQueue: 延迟发送队列、路由交换机绑定<br/>
     *
     * @return
     * @author Jason.Wang
     */
    @Bean
    public Binding consumerDelayExchangeBindOneQueue() {
        return BindingBuilder.bind(consumerDelayDirectQueue()).to(consumerDelayDirectExchange())
            .with(Constants.CONSUMER_DELAY_ROUTING_NAME).noargs();
    }
}
