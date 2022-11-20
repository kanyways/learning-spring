/**
 * Project Name:NettyServer
 * File Name:ConnectStatusCallback.java
 * Package Name:me.kany.project.utils.server.utils.impl
 * Date:2022-11-19 5:11 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.utils.impl;

/**
 * ClassName: ConnectStatusCallback<br/>
 * Function: 连接状态的回调<br/>
 * Date: 2022-11-19 5:11 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public interface ConnectStatusCallback {
    /**
     * Server 连接中
     */
    void onConnecting();

    /**
     * Server 连接成功
     */
    void onConnected();

    /**
     * Server 连接失败
     */
    void onConnectFailed();
}
