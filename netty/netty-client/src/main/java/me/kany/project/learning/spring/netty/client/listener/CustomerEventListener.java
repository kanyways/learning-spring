/**
 * Project Name:learning-spring
 * File Name:CustomerEventListener.java
 * Package Name:me.kany.project.learning.spring.netty.client.listener
 * Date:2022-11-20 2:21 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.client.listener;

import me.kany.project.learning.spring.netty.utils.listener.OnEventListener;
import me.kany.project.learning.spring.netty.utils.message.Message;

/**
 * ClassName: CustomerEventListener<br/>
 * Function: 与Server交互的listener<br/>
 * Date: 2022-11-20 2:21 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class CustomerEventListener implements OnEventListener {

    private String userId;
    private String token;

    public CustomerEventListener(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    /**
     * 分发消息到应用层
     *
     * @param msg
     */
    @Override
    public void dispatchMsg(Message msg) {

    }

    /**
     * 从应用层获取网络是否可用
     *
     * @return
     */
    @Override
    public boolean isNetworkAvailable() {
        return false;
    }

    /**
     * 获取重连间隔时长
     *
     * @return
     */
    @Override
    public int getReconnectInterval() {
        return 0;
    }

    /**
     * 获取连接超时时长
     *
     * @return
     */
    @Override
    public int getConnectTimeout() {
        return 0;
    }

    /**
     * 获取应用在前台时心跳间隔时间
     *
     * @return
     */
    @Override
    public int getForegroundHeartbeatInterval() {
        return 0;
    }

    /**
     * 获取应用在前台时心跳间隔时间
     *
     * @return
     */
    @Override
    public int getBackgroundHeartbeatInterval() {
        return 0;
    }

    /**
     * 获取由应用层构造的握手消息
     *
     * @return
     */
    @Override
    public Message getHandshakeMsg() {
        return null;
    }

    /**
     * 获取由应用层构造的心跳消息
     *
     * @return
     */
    @Override
    public Message getHeartbeatMsg() {
        return null;
    }

    /**
     * 获取应用层消息发送状态报告消息类型
     *
     * @return
     */
    @Override
    public int getServerSentReportMsgType() {
        return 0;
    }

    /**
     * 获取应用层消息接收状态报告消息类型
     *
     * @return
     */
    @Override
    public int getClientReceivedReportMsgType() {
        return 0;
    }

    /**
     * 获取应用层消息发送超时重发次数
     *
     * @return
     */
    @Override
    public int getResendCount() {
        return 0;
    }

    /**
     * 获取应用层消息发送超时重发间隔
     *
     * @return
     */
    @Override
    public int getResendInterval() {
        return 0;
    }
}
