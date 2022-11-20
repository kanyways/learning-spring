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
     * 交换机类型
     */
    public static final String EXCHANGE_TYPE_DELAYED_MESSAGE = "x-delayed-message";

    /**
     * 创建延迟队列交换机
     */
    public static final String CONSUMER_DELAY_EXCHANGE_NAME = "consumer_delay_exchange";
    /**
     * 创建延迟队列路由
     */
    public static final String CONSUMER_DELAY_ROUTING_NAME = "consumer_delay_direct_routing";
    /**
     * 创建延迟队列队列
     */
    public static final String CONSUMER_DELAY_QUEUE_NAME = "consumer_delay_queue";

}
