/**
 * Project Name:NettyServer
 * File Name:HeartbeatHandler.java
 * Package Name:me.kany.project.utils.server.utils.handler
 * Date:2022-11-19 6:14 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.utils.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import me.kany.project.learning.spring.netty.utils.impl.NettyTcpClient;
import me.kany.project.learning.spring.netty.utils.message.Message;

/**
 * ClassName: HeartbeatHandler<br/>
 * Function: 心跳任务管理器<br/>
 * Date: 2022-11-19 6:14 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    /**
     * 私有变量
     */
    private NettyTcpClient nettyTcpClient;

    public HeartbeatHandler(NettyTcpClient nettyTcpClient) {
        this.nettyTcpClient = nettyTcpClient;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent)evt).state();
            switch (state) {
                case READER_IDLE: {
                    // 规定时间内没收到服务端心跳包响应，进行重连操作
                    nettyTcpClient.resetConnect(false);
                    break;
                }

                case WRITER_IDLE: {
                    // 规定时间内没向服务端发送心跳包，即发送一个心跳包
                    if (heartbeatTask == null) {
                        heartbeatTask = new HeartbeatTask(ctx);
                    }

                    nettyTcpClient.getLoopGroup().execWorkTask(heartbeatTask);
                    break;
                }
            }
        }
    }

    private HeartbeatTask heartbeatTask;

    private class HeartbeatTask implements Runnable {

        private ChannelHandlerContext ctx;

        public HeartbeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            if (ctx.channel().isActive()) {
                Message heartbeatMsg = nettyTcpClient.getHeartbeatMsg();
                if (heartbeatMsg == null) {
                    return;
                }
                System.out.println(
                    "发送心跳消息，message=" + heartbeatMsg + "当前心跳间隔为：" + nettyTcpClient.getHeartbeatInterval()
                        + "ms\n");
                nettyTcpClient.sendMsg(heartbeatMsg, false);
            }
        }
    }
}
