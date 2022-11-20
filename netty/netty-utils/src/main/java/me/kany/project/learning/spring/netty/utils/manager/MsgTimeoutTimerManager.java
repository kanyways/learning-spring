/**
 * Project Name:NettyServer
 * File Name:MsgTimeoutTimerManager.java
 * Package Name:me.kany.project.utils.server.utils.manager
 * Date:2022-11-19 5:40 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.utils.manager;

import io.netty.util.internal.StringUtil;
import me.kany.project.learning.spring.netty.utils.ClientInterface;
import me.kany.project.learning.spring.netty.utils.message.Message;
import me.kany.project.learning.spring.netty.utils.timer.MsgTimeoutTimer;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: MsgTimeoutTimerManager<br/>
 * Function: 消息发送超时管理器，用于管理消息定时器的新增、移除等<br/>
 * Date: 2022-11-19 5:40 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class MsgTimeoutTimerManager {

    /**
     * 定时发送消息的队列，采用线程的HashMap
     */
    private Map<String, MsgTimeoutTimer> msgTimeoutMap = new ConcurrentHashMap<>();
    /**
     * 客户端对象
     */
    private ClientInterface imsClient;

    public MsgTimeoutTimerManager(ClientInterface imsClient) {
        this.imsClient = imsClient;
    }

    /**
     * 添加消息到发送超时管理器
     *
     * @param msg
     */
    public void add(Message msg) {
        if (msg == null) {
            return;
        }
        int handshakeMsgType = -1;
        int heartbeatMsgType = -1;
        int clientReceivedReportMsgType = imsClient.getClientReceivedReportMsgType();
        Message handshakeMsg = imsClient.getHandshakeMsg();
        if (handshakeMsg != null) {
            handshakeMsgType = handshakeMsg.getMsgType();
        }
        Message heartbeatMsg = imsClient.getHeartbeatMsg();
        if (heartbeatMsg != null) {
            heartbeatMsgType = heartbeatMsg.getMsgType();
        }

        int msgType = msg.getMsgType();
        // 握手消息、心跳消息、客户端返回的状态报告消息，不用重发。
        if (msgType == handshakeMsgType || msgType == heartbeatMsgType || msgType == clientReceivedReportMsgType) {
            return;
        }

        String msgId = msg.getMsgId();
        if (!msgTimeoutMap.containsKey(msgId)) {
            MsgTimeoutTimer timer = new MsgTimeoutTimer(imsClient, msg);
            msgTimeoutMap.put(msgId, timer);
        }

        System.out.println("添加消息超发送超时管理器，message=" + msg + "\t当前管理器消息数：" + msgTimeoutMap.size());
    }

    /**
     * remove: 从发送超时管理器中移除消息，并停止定时器<br/>
     *
     * @param msgId
     * @author Jason.Wang
     * @date 2022/11/19 6:02 PM
     */
    public void remove(String msgId) {
        if (StringUtil.isNullOrEmpty(msgId)) {
            return;
        }

        MsgTimeoutTimer timer = msgTimeoutMap.remove(msgId);
        Message msg = null;
        if (timer != null) {
            msg = timer.getMsg();
            timer.cancel();
            timer = null;
        }

        System.out.println("从发送消息管理器移除消息，message=" + msg);
    }

    /**
     * onResetConnected: 重连成功回调，重连并握手成功时，重发消息发送超时管理器中所有的消息<br/>
     *
     * @author Jason.Wang
     * @date 2022/11/19 6:02 PM
     */
    public synchronized void onResetConnected() {
        for (Iterator<Map.Entry<String, MsgTimeoutTimer>> it = msgTimeoutMap.entrySet().iterator(); it.hasNext(); ) {
            it.next().getValue().sendMsg();
        }
    }
}
