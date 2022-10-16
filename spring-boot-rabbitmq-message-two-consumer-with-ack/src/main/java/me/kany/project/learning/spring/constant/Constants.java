/**
 * Project Name:learning-spring
 * File Name:Constants.java
 * Package Name:me.kany.project.learning.spring.constant
 * Date:2022-10-15 6:39 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.constant;

/**
 * ClassName: Constants<br/>
 * Function: Constants 常量定义<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class Constants {

    /**
     * 交换机名称
     */
    public static final String TWO_CONSUMER_EXCHANGE_NAME = "two_consumer_direct_exchange";

    /**
     * 消费者一的队列
     */
    public static final String CONSUMER_FIRST_QUEUE_NAME = "consumer_first_queue";
    /**
     * 消费者二的队列
     */
    public static final String CONSUMER_SECOND_QUEUE_NAME = "consumer_second_queue";
    /**
     * 路由器代理名称
     */
    public static final String TWO_CONSUMER_ROUTING_NAME = "two_consumer_direct_routing";
}
