/**
 * Project Name:NettyServer
 * File Name:ServerHandler.java
 * Package Name:me.kany.project.utils.server.handler
 * Date:2022-11-19 7:36 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.server.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.kany.project.learning.spring.netty.server.message.Message;

/**
 * ClassName: ServerHandler<br/>
 * Function: 服务端控制类<br/>
 * Date: 2022-11-19 7:36 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final String TAG = ServerHandler.class.getSimpleName();

    /**
     * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("ServerHandler channelActive() " + ctx.channel().remoteAddress());
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelInactive()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("ServerHandler channelInactive() ");
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message)msg;
        System.out.println("收到来自客户端的消息：" + JSONObject.toJSONString(message));
        int msgType = message.getMsgType();
        switch (msgType) {
            case 1001:
                JSONObject jsonObject = JSON.parseObject(message.getExtend());
                String token = jsonObject.getString("token");
                JSONObject resp = new JSONObject();
                if (token.equals("token_" + message.getMsgId())) {
                    resp.put("status", 1);
                } else {
                    resp.put("status", -1);
                }
                ctx.channel().writeAndFlush(message);
                break;
            case 1002:
                ctx.channel().writeAndFlush(message);
                break;
            case 2001:
            case 3001:
                Message data = new Message();
                data.setMsgId(message.getMsgId());
                data.setMsgType(1010);
                data.setTimestamp(System.currentTimeMillis());
                data.setStatusReport(1);
                ctx.channel().writeAndFlush(data);
                break;
            default:
                break;
        }
    }

    /**
     * Calls {@link ChannelHandlerContext#fireUserEventTriggered(Object)} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        System.out.println("ServerHandler userEventTriggered() ");
    }

    /**
     * Calls {@link ChannelHandlerContext#fireExceptionCaught(Throwable)} to forward
     * to the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.out.println("ServerHandler exceptionCaught() ");
    }
}
