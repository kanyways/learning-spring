/**
 * Project Name:NettyServer
 * File Name:LoginAuthRespHandler.java
 * Package Name:me.kany.project.utils.server.utils.handler.resp
 * Date:2022-11-19 6:53 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.utils.handler.resp;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.kany.project.learning.spring.netty.utils.impl.NettyTcpClient;
import me.kany.project.learning.spring.netty.utils.message.Message;

/**
 * ClassName: LoginAuthRespHandler<br/>
 * Function: 握手认证消息响应处理handler<br/>
 * Date: 2022-11-19 6:53 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

    private NettyTcpClient nettyTcpClient;

    public LoginAuthRespHandler(NettyTcpClient nettyTcpClient) {
        this.nettyTcpClient = nettyTcpClient;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message handshakeRespMsg = (Message)msg;
        if (handshakeRespMsg == null) {
            return;
        }

        Message handshakeMsg = nettyTcpClient.getHandshakeMsg();
        if (handshakeMsg == null) {
            return;
        }

        int handshakeMsgType = handshakeMsg.getMsgType();
        if (handshakeMsgType == handshakeRespMsg.getMsgType()) {
            System.out.println("收到服务端握手响应消息，message=" + handshakeRespMsg);
            int status = -1;
            try {
                JSONObject jsonObj = JSON.parseObject(handshakeRespMsg.getExtend());
                status = jsonObj.getIntValue("status");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (status == 1) {
                    // 握手成功，马上先发送一条心跳消息，至于心跳机制管理，交由HeartbeatHandler
                    Message heartbeatMsg = nettyTcpClient.getHeartbeatMsg();
                    if (heartbeatMsg == null) {
                        return;
                    }

                    // 握手成功，检查消息发送超时管理器里是否有发送超时的消息，如果有，则全部重发
                    nettyTcpClient.getMsgTimeoutTimerManager().onResetConnected();

                    System.out.println(
                        "发送心跳消息：" + heartbeatMsg + "当前心跳间隔为：" + nettyTcpClient.getHeartbeatInterval()
                            + "ms\n");
                    nettyTcpClient.sendMsg(heartbeatMsg);

                    // 添加心跳消息管理handler
                    nettyTcpClient.addHeartbeatHandler();
                } else {
                    //imsClient.resetConnect(false);// 握手失败，触发重连
                    //握手失败且返回了消息一定是服务端认证没通过 所以这里需要关闭客户端
                    nettyTcpClient.close();
                }
            }
        } else {
            // 消息透传
            ctx.fireChannelRead(msg);
        }
    }
}
