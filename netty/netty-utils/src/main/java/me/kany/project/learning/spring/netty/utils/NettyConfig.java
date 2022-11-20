/**
 * Project Name:NettyServer
 * File Name:NettyConfig.java
 * Package Name:me.kany.project.utils.server.utils
 * Date:2022-11-19 5:03 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.utils;

/**
 * ClassName: NettyConfig<br/>
 * Function: Netty的配置文件<br/>
 * Date: 2022-11-19 5:03 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class NettyConfig {
    /**
     * 默认重连一个周期失败间隔时长
     */
    public static final int DEFAULT_RECONNECT_INTERVAL = 3 * 1000;
    /**
     * 连接超时时长
     */
    public static final int DEFAULT_CONNECT_TIMEOUT = 10 * 1000;
    /**
     * 默认一个周期重连次数
     */
    public static final int DEFAULT_RECONNECT_COUNT = 3;
    /**
     * 默认重连起始延时时长，重连规则：最大n次，每次延时n * 起始延时时长，重连次数达到n次后，重置
     */
    public static final int DEFAULT_RECONNECT_BASE_DELAY_TIME = 3 * 1000;
    /**
     * 默认消息发送失败重发次数
     */
    public static final int DEFAULT_RESEND_COUNT = 3;
    /**
     * 默认消息重发间隔时长
     */
    public static final int DEFAULT_RESEND_INTERVAL = 8 * 1000;
    /**
     * 默认应用在前台时心跳消息间隔时长
     */
    public static final int DEFAULT_HEARTBEAT_INTERVAL_FOREGROUND = 3 * 1000;
    /**
     * 默认应用在后台时心跳消息间隔时长
     */
    public static final int DEFAULT_HEARTBEAT_INTERVAL_BACKGROUND = 30 * 1000;
    /**
     * 应用在前台标识
     */
    public static final int APP_STATUS_FOREGROUND = 0;
    /**
     * 应用在后台标识
     */
    public static final int APP_STATUS_BACKGROUND = -1;
    /**
     * 保存App的状态标识
     */
    public static final String KEY_APP_STATUS = "key_app_status";
    /**
     * 默认服务端返回的消息发送成功状态报告
     */
    public static final int DEFAULT_REPORT_SERVER_SEND_MSG_SUCCESSFUL = 1;
    /**
     * 默认服务端返回的消息发送失败状态报告
     */
    public static final int DEFAULT_REPORT_SERVER_SEND_MSG_FAILURE = 0;
    /**
     * Server 连接状态：连接中
     */
    public static final int CONNECT_STATE_CONNECTING = 0;
    /**
     * Server 连接状态：连接成功
     */
    public static final int CONNECT_STATE_SUCCESSFUL = 1;
    /**
     * Server 连接状态：连接失败
     */
    public static final int CONNECT_STATE_FAILURE = -1;
}
