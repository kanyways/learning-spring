/**
 * Project Name:learning-spring
 * File Name:CustomerTextWebSocketHandler.java
 * Package Name:me.kany.project.learning.spring.config
 * Date:2022-11-26 12:48 AM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.config;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: CustomerTextWebSocketHandler<br/>
 * Function: 自定义TextWebSocketHandler<br/>
 * Date: 2022-11-26 12:48 AM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class CustomerTextWebSocketHandler extends TextWebSocketHandler {
    /**
     * 用于缓存所有的用户和连接之间的关系
     */
    private static final ConcurrentHashMap<String, WebSocketSession> allClients = new ConcurrentHashMap<>(32);

    /**
     * afterConnectionEstablished: 当和用户成功建立连接的时候会调用此方法,在此方法内部应该保存连接<br/>
     *
     * @param session
     * @throws Exception
     * @author Jason.Wang
     * @date 2022/11/26 12:51 AM
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    /**
     * sendMessageToUser: 给指定的用户发送消息<br/>
     *
     * @param userName
     * @param message
     * @author Jason.Wang
     * @date 2022/11/26 12:52 AM
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        //根据接收方的名字找到对应的连接
        WebSocketSession webSocketSession = allClients.get(userName);
        //如果没有离线,如果离线,请根据实际业务需求来处理,可能会需要保存离线消息
        if (webSocketSession != null && webSocketSession.isOpen()) {
            try {
                //发送消息
                webSocketSession.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * sendMessageToUsers: 给所有用户发送消息<br/>
     *
     * @param message
     * @author Jason.Wang
     * @date 2022/11/26 12:52 AM
     */
    public void sendMessageToUsers(TextMessage message) {
        //获取所有的连接
        for (Map.Entry<String, WebSocketSession> webSocketSessionEntry : allClients.entrySet()) {
            //找到每个连接
            WebSocketSession session = webSocketSessionEntry.getValue();
            if (session != null && session.isOpen()) {
                try {
                    session.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        //        return super.supportsPartialMessages();
        return false;
    }
}
