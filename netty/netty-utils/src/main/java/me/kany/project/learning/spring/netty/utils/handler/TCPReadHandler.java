/**
 * Project Name:NettyServer
 * File Name:TCPReadHandler.java
 * Package Name:me.kany.project.utils.server.utils.handler
 * Date:2022-11-19 6:16 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.utils.handler;

import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.StringUtil;
import me.kany.project.learning.spring.netty.utils.NettyConfig;
import me.kany.project.learning.spring.netty.utils.impl.NettyTcpClient;
import me.kany.project.learning.spring.netty.utils.message.Message;

import java.util.UUID;

/**
 * ClassName: TCPReadHandler<br/>
 * Function: 消息接收处理handler<br/>
 * Date: 2022-11-19 6:16 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class TCPReadHandler extends ChannelInboundHandlerAdapter {

    private NettyTcpClient nettyTcpClient;

    public TCPReadHandler(NettyTcpClient nettyTcpClient) {
        this.nettyTcpClient = nettyTcpClient;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.err.println("TCPReadHandler channelInactive()");
        Channel channel = ctx.channel();
        if (channel != null) {
            channel.close();
            ctx.close();
        }
        // 触发重连
        nettyTcpClient.resetConnect(false);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.err.println("TCPReadHandler exceptionCaught()");
        Channel channel = ctx.channel();
        if (channel != null) {
            channel.close();
            ctx.close();
        }
        // 触发重连
        nettyTcpClient.resetConnect(false);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message)msg;
        if (message == null) {
            return;
        }

        int msgType = message.getMsgType();
        if (msgType == nettyTcpClient.getServerSentReportMsgType()) {
            int statusReport = message.getStatusReport();
            System.out.println(String.format("服务端状态报告：「%d」, 1代表成功，0代表失败", statusReport));
            if (statusReport == NettyConfig.DEFAULT_REPORT_SERVER_SEND_MSG_SUCCESSFUL) {
                System.out.println("收到服务端消息发送状态报告，message=" + message + "，从超时管理器移除");
                nettyTcpClient.getMsgTimeoutTimerManager().remove(message.getMsgId());
            }
        } else {
            // 其它消息
            // 收到消息后，立马给服务端回一条消息接收状态报告
            System.out.println("收到消息，message=" + message);
            Message receivedReportMsg = buildReceivedReportMsg(message.getMsgId());
            if (receivedReportMsg != null) {
                nettyTcpClient.sendMsg(receivedReportMsg);
            }
        }
        // 接收消息，由消息转发器转发到应用层
        nettyTcpClient.getMsgDispatcher().receivedMsg(message);
    }

    /**
     * 构建客户端消息接收状态报告
     *
     * @param msgId
     * @return
     */
    private Message buildReceivedReportMsg(String msgId) {
        if (StringUtil.isNullOrEmpty(msgId)) {
            return null;
        }
        Message message = new Message();
        message.setMsgId(UUID.randomUUID().toString());
        message.setMsgType(nettyTcpClient.getClientReceivedReportMsgType());
        message.setTimestamp(System.currentTimeMillis());
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("msgId", msgId);
        message.setExtend(jsonObj.toString());
        return message;
    }
}
