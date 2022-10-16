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
     * 接收消费者一发送的信息，one 2 one
     */
    public static final String CONSUMER_SINGLETON_EXCHANGE_NAME = "consumer_singleton_direct_exchange";
    /**
     * 接收消费者一发送的信息，one 2 one
     */
    public static final String CONSUMER_SINGLETON_ROUTING_NAME = "consumer_singleton_direct_routing";

    /**
     * 消费者的队列
     */
    public static final String CONSUMER_SINGLETON_QUEUE_NAME = "consumer_singleton_direct_queue";

}
