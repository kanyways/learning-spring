/**
 * Project Name:NettyServer
 * File Name:MsgTimeoutTimer.java
 * Package Name:me.kany.project.utils.server.utils.timer
 * Date:2022-11-19 5:41 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.utils.timer;

import me.kany.project.learning.spring.netty.utils.ClientInterface;
import me.kany.project.learning.spring.netty.utils.NettyConfig;
import me.kany.project.learning.spring.netty.utils.message.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ClassName: MsgTimeoutTimer<br/>
 * Function: 消息发送超时定时器，每一条消息对应一个定时器<br/>
 * Date: 2022-11-19 5:41 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class MsgTimeoutTimer extends Timer {
    /**
     * 客户端
     */
    private ClientInterface clientInterface;
    /**
     * 发送的消息
     */
    private Message msg;
    /**
     * 当前重发次数
     */
    private int currentResendCount;
    /**
     * 消息发送超时任务
     */
    private MsgTimeoutTask task;

    public MsgTimeoutTimer(ClientInterface imsClient, Message msg) {
        clientInterface = imsClient;
        this.msg = msg;
        task = new MsgTimeoutTask();
        schedule(task, imsClient.getResendInterval(), imsClient.getResendInterval());
    }

    /**
     * 消息发送超时任务
     */
    private class MsgTimeoutTask extends TimerTask {

        @Override
        public void run() {
            if (clientInterface.isClosed()) {
                if (clientInterface.getMsgTimeoutTimerManager() != null) {
                    clientInterface.getMsgTimeoutTimerManager().remove(msg.getMsgId());
                }

                return;
            }

            currentResendCount++;
            if (currentResendCount > clientInterface.getResendCount()) {
                // 重发次数大于可重发次数，直接标识为发送失败，并通过消息转发器通知应用层
                try {
                    Message message = new Message();
                    message.setMsgId(msg.getMsgId());
                    message.setMsgType(clientInterface.getServerSentReportMsgType());
                    message.setTimestamp(System.currentTimeMillis());
                    message.setStatusReport(NettyConfig.DEFAULT_REPORT_SERVER_SEND_MSG_FAILURE);

                    // 通知应用层消息发送失败
                    clientInterface.getMsgDispatcher().receivedMsg(message);
                } finally {
                    // 从消息发送超时管理器移除该消息
                    clientInterface.getMsgTimeoutTimerManager().remove(msg.getMsgId());
                    // 执行到这里，认为连接已断开或不稳定，触发重连
                    clientInterface.resetConnect();
                    currentResendCount = 0;
                }
            } else {
                // 发送消息，但不再加入超时管理器，达到最大发送失败次数就算了
                sendMsg();
            }
        }
    }

    public void sendMsg() {
        System.out.println("正在重发消息，message=" + msg);
        clientInterface.sendMsg(msg, false);
    }

    public Message getMsg() {
        return msg;
    }

    @Override
    public void cancel() {
        if (task != null) {
            task.cancel();
            task = null;
        }

        super.cancel();
    }
}
