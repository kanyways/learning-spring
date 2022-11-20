/**
 * Project Name:NettyServer
 * File Name:NettyServer.java
 * Package Name:me.kany.project.utils.server
 * Date:2022-11-19 5:03 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import me.kany.project.learning.spring.netty.server.handler.ServerHandler;

/**
 * ClassName: NettyServer<br/>
 * Function: Netty服务端<br/>
 * Date: 2022-11-19 5:03 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class NettyServer {

    /**
     * main: 启动服务端代码<br/>
     *
     * @param args
     * @author Jason.Wang
     * @date 2022/11/19 7:54 PM
     */
    public static void main(String[] args) {
        // Boss 线程监听端口，worker线程负责数据读写
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            // 辅助启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 设置线程池
            serverBootstrap.group(boss, worker);
            // 设置Socket工厂
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 设置管道工厂
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                /**
                 * This method will be called once the {@link Channel} was registered. After the method returns this instance
                 * will be removed from the {@link ChannelPipeline} of the {@link Channel}.
                 *
                 * @param ch the {@link Channel} which was registered.
                 * @throws Exception is thrown if an error occurs. In that case it will be handled by
                 *                   {@link #exceptionCaught(ChannelHandlerContext, Throwable)} which will by default close
                 *                   the {@link Channel}.
                 */
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast("frameEncoder", new LengthFieldPrepender(2));
                    pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                    // 处理类
                    pipeline.addLast(new ServerHandler());
                }
            });
            // 设置TCP参数
            // 链接缓冲池的大小（ServerSocketChannel的设置）
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            // 维持链接的活跃，清楚死链接（SocketChannel的设置）
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            // 关闭延迟发送
            serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            // 绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(8866).sync();
            System.out.println("服务器启动成功 ...... ");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅退出，释放线程池资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
