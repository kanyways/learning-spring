/**
 * Project Name:NettyServer
 * File Name:MsgDispatcher.java
 * Package Name:me.kany.project.utils.server.utils.message
 * Date:2022-11-19 5:30 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.utils.message;

import me.kany.project.learning.spring.netty.utils.listener.OnEventListener;

/**
 * ClassName: MsgDispatcher<br/>
 * Function: 消息转发器，负责将接收到的消息转发到应用层<br/>
 * Date: 2022-11-19 5:30 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class MsgDispatcher {

    private OnEventListener onEventListener;

    /**
     * setOnEventListener: 设置监听器<br/>
     *
     * @param listener
     * @author Jason.Wang
     * @date 2022/11/19 5:32 PM
     */
    public void setOnEventListener(OnEventListener listener) {
        onEventListener = listener;
    }

    /**
     * receivedMsg: 接收消息，并通过OnEventListener转发消息到应用层<br/>
     *
     * @param msg
     * @author Jason.Wang
     * @date 2022/11/19 5:31 PM
     */
    public void receivedMsg(Message msg) {
        if (onEventListener == null) {
            return;
        }
        onEventListener.dispatchMsg(msg);
    }
}
