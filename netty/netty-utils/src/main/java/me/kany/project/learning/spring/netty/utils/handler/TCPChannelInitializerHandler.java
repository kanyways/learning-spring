/**
 * Project Name:NettyServer
 * File Name:TCPChannelInitializerHandler.java
 * Package Name:me.kany.project.utils.server.utils.handler
 * Date:2022-11-19 6:29 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.utils.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import me.kany.project.learning.spring.netty.utils.handler.resp.HeartbeatRespHandler;
import me.kany.project.learning.spring.netty.utils.handler.resp.LoginAuthRespHandler;
import me.kany.project.learning.spring.netty.utils.impl.NettyTcpClient;

/**
 * ClassName: TCPChannelInitializerHandler<br/>
 * Function: Channel的初始化配置<br/>
 * Date: 2022-11-19 6:29 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class TCPChannelInitializerHandler extends ChannelInitializer<Channel> {

    private NettyTcpClient nettyTcpClient;

    public TCPChannelInitializerHandler(NettyTcpClient nettyTcpClient) {
        this.nettyTcpClient = nettyTcpClient;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        // netty提供的自定义长度解码器，解决TCP拆包/粘包问题
        pipeline.addLast("frameEncoder", new LengthFieldPrepender(2));
        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));

        // 握手认证消息响应处理handler
        pipeline.addLast(LoginAuthRespHandler.class.getSimpleName(), new LoginAuthRespHandler(nettyTcpClient));
        // 心跳消息响应处理handler
        pipeline.addLast(HeartbeatRespHandler.class.getSimpleName(), new HeartbeatRespHandler(nettyTcpClient));
        // 接收消息处理handler
        pipeline.addLast(TCPReadHandler.class.getSimpleName(), new TCPReadHandler(nettyTcpClient));
    }
}
