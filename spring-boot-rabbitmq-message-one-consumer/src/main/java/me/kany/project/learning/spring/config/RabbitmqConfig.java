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
     * consumerDirectExchange: 创建消费者一的交换机<br/>
     *
     * @return
     * @author Jason.Wang
     */
    @Bean
    public DirectExchange consumerDirectExchange() {
        return new DirectExchange(Constants.CONSUMER_SINGLETON_EXCHANGE_NAME, true, false);
    }

    /**
     * consumerSingletonDirectQueue: 创建消费者一队列<br/>
     *
     * @return
     * @author Jason.Wang
     */
    @Bean
    public Queue consumerSingletonDirectQueue() {
        return QueueBuilder.durable(Constants.CONSUMER_SINGLETON_QUEUE_NAME).build();
    }

    /**
     * consumerExchangeBindOneQueue: 将消费者绑定队列到消费者一的交换机和路由中<br/>
     *
     * @return
     * @author Jason.Wang
     */
    @Bean
    public Binding consumerExchangeBindOneQueue() {
        return BindingBuilder.bind(consumerSingletonDirectQueue()).to(consumerDirectExchange())
            .with(Constants.CONSUMER_SINGLETON_ROUTING_NAME);
    }
}
