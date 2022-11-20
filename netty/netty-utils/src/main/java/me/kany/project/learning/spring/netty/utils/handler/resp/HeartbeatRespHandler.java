/**
 * Project Name:NettyServer
 * File Name:HeartbeatRespHandler.java
 * Package Name:me.kany.project.utils.server.utils.handler.resp
 * Date:2022-11-19 6:31 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.utils.handler.resp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.kany.project.learning.spring.netty.utils.impl.NettyTcpClient;
import me.kany.project.learning.spring.netty.utils.message.Message;

/**
 * ClassName: HeartbeatRespHandler<br/>
 * Function: 心跳消息响应处理handler<br/>
 * Date: 2022-11-19 6:31 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class HeartbeatRespHandler extends ChannelInboundHandlerAdapter {

    private NettyTcpClient nettyTcpClient;

    public HeartbeatRespHandler(NettyTcpClient nettyTcpClient) {
        this.nettyTcpClient = nettyTcpClient;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message heartbeatRespMsg = (Message)msg;
        if (heartbeatRespMsg == null) {
            return;
        }

        Message heartbeatMsg = nettyTcpClient.getHeartbeatMsg();
        if (heartbeatMsg == null) {
            return;
        }

        int heartbeatMsgType = heartbeatMsg.getMsgType();
        if (heartbeatMsgType == heartbeatRespMsg.getMsgType()) {
            System.out.println("收到服务端心跳响应消息，message=" + heartbeatRespMsg);
        } else {
            // 消息透传
            ctx.fireChannelRead(msg);
        }
    }
}
