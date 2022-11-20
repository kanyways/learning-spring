/**
 * Project Name:learning-spring
 * File Name:RabbitmqProducer.java
 * Package Name:me.kany.project.learning.spring.producer
 * Date:2022-10-15 7:22 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.kany.project.learning.spring.constant.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName: RabbitmqProducer<br/>
 * Function: Rabbit 的生产者<br/>
 * Date: 2022-10-15 7:22 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
@Slf4j
@Component
public class RabbitmqProducer {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    ObjectMapper objectMapper;

    /**
     * sendConsumerDelayMessage: 发送延迟消息<br/>
     *
     * @param routingKey
     * @param obj
     * @param delayTime  延迟时间
     * @author Jason.Wang
     */
    public void sendConsumerDelayMessage(String routingKey, Object obj, Integer delayTime) {
        rabbitTemplate.convertAndSend(Constants.CONSUMER_DELAY_EXCHANGE_NAME, routingKey, obj, (message) -> {
            message.getMessageProperties().setDelay(delayTime);
            try {
                log.info("消息内容为：{}", objectMapper.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return message;
        });
    }
}
