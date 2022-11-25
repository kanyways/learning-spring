/**
 * Project Name:learning-spring
 * File Name:CustomerWebSocket.java
 * Package Name:me.kany.project.learning.spring.service
 * Date:2022-11-26 12:55 AM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.service;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: CustomerWebSocket<br/>
 * Function: 自定义的WebSocket核心类型<br/>
 * Date: 2022-11-26 12:55 AM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{name}")
public class CustomerWebSocket {

    /**
     * 与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    private String name;

    /**
     * 用于存所有的连接服务的客户端，这个对象存储是安全的
     * 注意这里的kv,设计的很巧妙，v刚好是本类 WebSocket (用来存放每个客户端对应的CustomerWebSocket对象)
     */
    private static ConcurrentHashMap<String, CustomerWebSocket> webSocketSet = new ConcurrentHashMap<>();

    /**
     * OnOpen: 连接建立成功调用的方法，session为与某个客户端的连接会话，需要通过它来给客户端发送数据<br/>
     *
     * @param session
     * @param name
     * @author Jason.Wang
     * @date 2022/11/26 1:00 AM
     */
    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "name") String name) {
        log.info("----------------------------------");
        this.session = session;
        this.name = name;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(name, this);
        log.info("[WebSocket] 连接成功，当前连接人数为：={}", webSocketSet.size());
        log.info("----------------------------------");
        log.info("");

        GroupSending("Hi~," + name + " 来了");
    }

    /**
     * OnClose: 连接关闭调用的方法<br/>
     *
     * @author Jason.Wang
     * @date 2022/11/26 1:00 AM
     */
    @OnClose
    public void OnClose() {
        webSocketSet.remove(this.name);
        log.info("[WebSocket] 退出成功，当前连接人数为：={}", webSocketSet.size());
        GroupSending(name + " 走了~");
    }

    /**
     * OnMessage: 收到客户端消息后调用的方法<br/>
     *
     * @param message
     * @author Jason.Wang
     * @date 2022/11/26 1:01 AM
     */
    @OnMessage
    public void OnMessage(String message) {
        log.info("[WebSocket] 收到消息：{}", message);
        //判断是否需要指定发送，具体规则自定义
        //message_str的格式 TOUSER:user2;message:aaaaaaaaaaaaaaaaaa;
        JSONObject jsonObject = JSONObject.parseObject(message);
        if (jsonObject.containsKey("to")) {
            //指定发送
            AppointSending(jsonObject.getString("to"), jsonObject.getString("message"));
        } else {
            //群发
            GroupSending(jsonObject.getString("message"));
        }
    }

    /**
     * onError: 发生错误时调用<br/>
     *
     * @param session
     * @param error
     * @author Jason.Wang
     * @date 2022/11/26 1:02 AM
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("发生错误");
        error.printStackTrace();
    }

    /**
     * GroupSending: 群发<br/>
     *
     * @param message
     * @author Jason.Wang
     * @date 2022/11/26 1:02 AM
     */
    public void GroupSending(String message) {
        for (String name : webSocketSet.keySet()) {
            try {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * AppointSending: 指定发送<br/>
     *
     * @param name
     * @param message
     * @author Jason.Wang
     * @date 2022/11/26 1:02 AM
     */
    public void AppointSending(String name, String message) {
        try {
            webSocketSet.get(name).session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
