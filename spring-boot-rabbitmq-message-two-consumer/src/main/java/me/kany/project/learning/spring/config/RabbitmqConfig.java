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
     * producerSendTwoConsumerDirectExchange: 创建一个生产者 两个消费者的交换机，由于需要一条信息两个消费者都要受到，所以交换机需要一致<br/>
     *
     * @return
     * @author Jason.Wang
     */
    @Bean
    public DirectExchange producerSendTwoConsumerDirectExchange() {
        return new DirectExchange(Constants.TWO_CONSUMER_EXCHANGE_NAME, true, false);
    }

    /**
     * consumerFirstDirectQueue: 创建消费者一队列<br/>
     *
     * @return
     * @author Jason.Wang
     */
    @Bean
    public Queue consumerFirstDirectQueue() {
        return QueueBuilder.durable(Constants.CONSUMER_FIRST_QUEUE_NAME).build();
    }

    /**
     * consumerSecondDirectQueue: 创建消费者二队列<br/>
     *
     * @return
     * @author Jason.Wang
     */
    @Bean
    public Queue consumerSecondDirectQueue() {
        return QueueBuilder.durable(Constants.CONSUMER_SECOND_QUEUE_NAME).build();
    }

    /**
     * consumerSecondExchangeBindOneQueue: 将消费者一绑定到交换机中<br/>
     *
     * @return
     * @author Jason.Wang
     */
    @Bean
    public Binding consumerFirstExchangeBindOneQueue() {
        return BindingBuilder.bind(consumerFirstDirectQueue()).to(producerSendTwoConsumerDirectExchange())
            .with(Constants.TWO_CONSUMER_ROUTING_NAME);
    }

    /**
     * consumerSecondExchangeBindOneQueue: 将消费者二绑定到交换机中<br/>
     *
     * @return
     * @author Jason.Wang
     */
    @Bean
    public Binding consumerSecondExchangeBindOneQueue() {
        return BindingBuilder.bind(consumerSecondDirectQueue()).to(producerSendTwoConsumerDirectExchange())
            .with(Constants.TWO_CONSUMER_ROUTING_NAME);
    }
}
