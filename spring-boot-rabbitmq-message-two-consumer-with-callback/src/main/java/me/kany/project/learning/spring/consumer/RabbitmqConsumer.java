/**
 * Project Name:learning-spring
 * File Name:RabbitmqConsumer.java
 * Package Name:me.kany.project.learning.spring.consumer
 * Date:2022-10-15 7:29 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import me.kany.project.learning.spring.constant.Constants;
import me.kany.project.learning.spring.producer.RabbitmqProducer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

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
     * @param channel
     * @param tag
     * @throws IOException
     * @author Jason.Wang
     */
    @RabbitListener(queues = Constants.CONSUMER_FIRST_QUEUE_NAME)
    public void firstQueueReceiveListener(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
        throws IOException {
        try {
            log.info("通道一消费者接收到消息：{}", message);
            ObjectNode objectNode = objectMapper.createObjectNode();

            objectNode.put("consumer", 1);
            objectNode.put("timestamp", LocalDateTime.now().getNano() / 10e3);
            objectNode.put("message", message);

            rabbitmqProducer.sendFirstConsumerMessage(Constants.CONSUMER_FIRST_SINGLETON_ROUTING_NAME,
                objectMapper.writeValueAsString(objectNode));
            // 业务逻辑处理成功，告诉RabbitMQ，已经接收到消息并做了处理了。这样消息队列这条消息才算真正消费成功
            channel.basicAck(tag, false);
        } catch (Exception e) {
            // 处理过程中，发生了不是业务逻辑的错误异常，则不答复ACK，这样MQ会认为这条消息未成功消费，所以会重新把该条消息放回队列中，直到ACK正常答复
            channel.basicNack(tag, false, true);
        }
    }

    /**
     * secondQueueReceiveListener: 监听发送给通道二的消息<br/>
     *
     * @param message
     * @param channel
     * @param tag
     * @throws IOException
     * @author Jason.Wang
     */
    @RabbitListener(queues = Constants.CONSUMER_SECOND_QUEUE_NAME)
    public void secondQueueReceiveListener(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
        throws IOException {
        try {
            log.info("通道二消费者接收到消息：{}", message);
            ObjectNode objectNode = objectMapper.createObjectNode();

            objectNode.put("consumer", 2);
            objectNode.put("timestamp", LocalDateTime.now().getNano() / 10e3);
            objectNode.put("message", message);

            rabbitmqProducer.sendSecondConsumerMessage(Constants.CONSUMER_SECOND_SINGLETON_ROUTING_NAME,
                objectMapper.writeValueAsString(objectNode));
            // 业务逻辑处理成功，告诉RabbitMQ，已经接收到消息并做了处理了。这样消息队列这条消息才算真正消费成功
            channel.basicAck(tag, false);
        } catch (Exception e) {
            // 处理过程中，发生了不是业务逻辑的错误异常，则不答复ACK，这样MQ会认为这条消息未成功消费，所以会重新把该条消息放回队列中，直到ACK正常答复
            channel.basicNack(tag, false, true);
        }
    }

    /**
     * firstSingletonQueueReceiveListener: 监听发送给通道一的消息<br/>
     *
     * @param message
     * @param channel
     * @param tag
     * @throws IOException
     * @author Jason.Wang
     */
    @RabbitListener(queues = Constants.CONSUMER_FIRST_SINGLETON_QUEUE_NAME)
    public void firstSingletonQueueReceiveListener(String message, Channel channel,
        @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info("通道一消费者接收到消息：{}", message);
            // 业务逻辑处理成功，告诉RabbitMQ，已经接收到消息并做了处理了。这样消息队列这条消息才算真正消费成功
            channel.basicAck(tag, false);
        } catch (Exception e) {
            // 处理过程中，发生了不是业务逻辑的错误异常，则不答复ACK，这样MQ会认为这条消息未成功消费，所以会重新把该条消息放回队列中，直到ACK正常答复
            channel.basicNack(tag, false, true);
        }
    }

    /**
     * secondSingletonQueueReceiveListener: 监听发送给通道二的消息<br/>
     *
     * @param message
     * @param channel
     * @param tag
     * @throws IOException
     * @author Jason.Wang
     */
    @RabbitListener(queues = Constants.CONSUMER_SECOND_SINGLETON_QUEUE_NAME)
    public void secondSingletonQueueReceiveListener(String message, Channel channel,
        @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info("通道二消费者接收到消息：{}", message);
            // 业务逻辑处理成功，告诉RabbitMQ，已经接收到消息并做了处理了。这样消息队列这条消息才算真正消费成功
            channel.basicAck(tag, false);
        } catch (Exception e) {
            // 处理过程中，发生了不是业务逻辑的错误异常，则不答复ACK，这样MQ会认为这条消息未成功消费，所以会重新把该条消息放回队列中，直到ACK正常答复
            channel.basicNack(tag, false, true);
        }
    }
}
