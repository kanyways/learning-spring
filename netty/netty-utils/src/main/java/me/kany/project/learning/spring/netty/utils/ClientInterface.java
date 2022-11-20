/**
 * Project Name:NettyServer
 * File Name:ClientInterface.java
 * Package Name:me.kany.project.utils.server.utils
 * Date:2022-11-19 5:43 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.utils;

import me.kany.project.learning.spring.netty.utils.impl.ConnectStatusCallback;
import me.kany.project.learning.spring.netty.utils.listener.OnEventListener;
import me.kany.project.learning.spring.netty.utils.manager.MsgTimeoutTimerManager;
import me.kany.project.learning.spring.netty.utils.message.Message;
import me.kany.project.learning.spring.netty.utils.message.MsgDispatcher;

import java.util.Vector;

/**
 * ClassName: ClientInterface<br/>
 * Function: 抽象接口，需要切换到其它方式实现功能，实现此接口即可<br/>
 * Date: 2022-11-19 5:43 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public interface ClientInterface {

    /**
     * init: 初始化<br/>
     *
     * @param serverUrlList 服务器地址列表
     * @param listener      与应用层交互的listener
     * @param callback      ims连接状态回调
     * @author Jason.Wang
     * @date 2022/11/19 6:05 PM
     */
    void init(Vector<String> serverUrlList, OnEventListener listener, ConnectStatusCallback callback);

    /**
     * resetConnect: 重置连接，也就是重连。首次连接也可认为是重连<br/>
     *
     * @author Jason.Wang
     * @date 2022/11/19 6:04 PM
     */
    void resetConnect();

    /**
     * resetConnect: 重置连接，也就是重连。首次连接也可认为是重连<br/>
     *
     * @param isFirst 是否首次连接
     * @author Jason.Wang
     * @date 2022/11/19 6:05 PM
     */
    void resetConnect(boolean isFirst);

    /**
     * close: 关闭连接，同时释放资源<br/>
     *
     * @author Jason.Wang
     * @date 2022/11/19 6:05 PM
     */
    void close();

    /**
     * isClosed: 标识Server是否已关闭<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:05 PM
     */
    boolean isClosed();

    /**
     * sendMsg: 发送消息<br/>
     *
     * @param msg
     * @author Jason.Wang
     * @date 2022/11/19 6:05 PM
     */
    void sendMsg(Message msg);

    /**
     * sendMsg: 发送消息，是否加入超时管理器<br/>
     *
     * @param msg
     * @param isJoinTimeoutManager 是否加入发送超时管理器
     * @author Jason.Wang
     * @date 2022/11/19 6:06 PM
     */
    void sendMsg(Message msg, boolean isJoinTimeoutManager);

    /**
     * getReconnectInterval: 获取重连间隔时长<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:06 PM
     */
    int getReconnectInterval();

    /**
     * getConnectTimeout: 获取连接超时时长<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:06 PM
     */
    int getConnectTimeout();

    /**
     * getForegroundHeartbeatInterval: 获取应用在前台时心跳间隔时间<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:06 PM
     */
    int getForegroundHeartbeatInterval();

    /**
     * getBackgroundHeartbeatInterval: 获取应用在后台时心跳间隔时间<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:06 PM
     */
    int getBackgroundHeartbeatInterval();

    /**
     * setAppStatus: 设置app前后台状态<br/>
     *
     * @param appStatus
     * @author Jason.Wang
     * @date 2022/11/19 6:06 PM
     */
    void setAppStatus(int appStatus);

    /**
     * getHandshakeMsg: 获取由应用层构造的握手消息<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:06 PM
     */
    Message getHandshakeMsg();

    /**
     * getHeartbeatMsg: 获取由应用层构造的心跳消息<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:07 PM
     */
    Message getHeartbeatMsg();

    /**
     * getServerSentReportMsgType: 获取应用层消息发送状态报告消息类型<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:07 PM
     */
    int getServerSentReportMsgType();

    /**
     * getClientReceivedReportMsgType: 获取应用层消息接收状态报告消息类型<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:07 PM
     */
    int getClientReceivedReportMsgType();

    /**
     * getResendCount: 获取应用层消息发送超时重发次数<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:07 PM
     */
    int getResendCount();

    /**
     * getResendInterval: 获取应用层消息发送超时重发间隔<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:07 PM
     */
    int getResendInterval();

    /**
     * getMsgDispatcher: 获取消息转发器<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:07 PM
     */
    MsgDispatcher getMsgDispatcher();

    /**
     * getMsgTimeoutTimerManager: 获取消息发送超时定时器<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:07 PM
     */
    MsgTimeoutTimerManager getMsgTimeoutTimerManager();
}
