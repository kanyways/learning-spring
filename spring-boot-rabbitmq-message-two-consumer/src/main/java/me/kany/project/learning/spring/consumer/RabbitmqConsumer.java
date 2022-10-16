/**
 * Project Name:learning-spring
 * File Name:RabbitmqConsumer.java
 * Package Name:me.kany.project.learning.spring.consumer
 * Date:2022-10-15 7:29 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.kany.project.learning.spring.constant.Constants;
import me.kany.project.learning.spring.producer.RabbitmqProducer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: RabbitmqConsumer<br/>
 * Function: <br/>
 * Date: 2022-10-15 7:29 PM<br/>
 *
 * @author Jason.Wang
 * @see
 * @since JDK
 */
@Slf4j
@Service
public class RabbitmqConsumer {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RabbitmqProducer rabbitmqProducer;

    /**
     * firstQueueReceiveListener: 监听发送给通道一的消息<br/>
     *
     * @param message
     * @author Jason.Wang
     */
    @RabbitListener(queues = Constants.CONSUMER_FIRST_QUEUE_NAME)
    public void firstQueueReceiveListener(String message) {
        log.info("通道一消费者接收到消息：{}", message);
    }

    /**
     * secondQueueReceiveListener: 监听发送给通道二的消息<br/>
     *
     * @param message
     * @author Jason.Wang
     */
    @RabbitListener(queues = Constants.CONSUMER_SECOND_QUEUE_NAME)
    public void secondQueueReceiveListener(String message) {
        log.info("通道二消费者接收到消息：{}", message);
    }
}
