/**
 * Project Name:learning-spring
 * File Name:ClientConnectStatusListener.java
 * Package Name:me.kany.project.learning.spring.netty.client.listener
 * Date:2022-11-25 10:55 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.client.listener;

import me.kany.project.learning.spring.netty.utils.impl.ConnectStatusCallback;

/**
 * ClassName: ClientConnectStatusListener<br/>
 * Function: 客户端连接状态监听<br/>
 * Date: 2022-11-25 10:55 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class ClientConnectStatusListener implements ConnectStatusCallback {
    /**
     * Server 连接中
     */
    @Override
    public void onConnecting() {

    }

    /**
     * Server 连接成功
     */
    @Override
    public void onConnected() {

    }

    /**
     * Server 连接失败
     */
    @Override
    public void onConnectFailed() {

    }
}
