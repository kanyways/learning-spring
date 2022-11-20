/**
 * Project Name:NettyServer
 * File Name:NettyTcpClient.java
 * Package Name:me.kany.project.utils.server.utils.impl
 * Date:2022-11-19 5:06 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.utils.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.StringUtil;
import me.kany.project.learning.spring.netty.utils.ClientInterface;
import me.kany.project.learning.spring.netty.utils.NettyConfig;
import me.kany.project.learning.spring.netty.utils.factory.ExecutorServiceFactory;
import me.kany.project.learning.spring.netty.utils.handler.HeartbeatHandler;
import me.kany.project.learning.spring.netty.utils.handler.TCPChannelInitializerHandler;
import me.kany.project.learning.spring.netty.utils.handler.TCPReadHandler;
import me.kany.project.learning.spring.netty.utils.listener.OnEventListener;
import me.kany.project.learning.spring.netty.utils.manager.MsgTimeoutTimerManager;
import me.kany.project.learning.spring.netty.utils.message.Message;
import me.kany.project.learning.spring.netty.utils.message.MsgDispatcher;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: NettyTcpClient<br/>
 * Function: netty的tcp连接客户端<br/>
 * Date: 2022-11-19 5:06 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class NettyTcpClient implements ClientInterface {
    private static volatile NettyTcpClient instance;

    /**
     * 标识 Server 是否已关闭
     */
    private boolean isClosed = false;
    /**
     * 服务通道
     */
    private Channel channel;

    /**
     * bootstrap
     */
    private Bootstrap bootstrap;
    /**
     * 线程池工厂
     */
    private ExecutorServiceFactory loopGroup;
    /**
     * 服务器地址组
     */
    private Vector<String> serverUrlList;
    /**
     * 是否正在进行重连
     */
    private boolean isReconnecting = false;
    /**
     * 与应用层交互的listener
     */
    private OnEventListener onEventListener;
    /**
     * 连接状态回调监听器
     */
    private ConnectStatusCallback connectStatusCallback;

    /**
     * 消息转发器
     */
    private MsgDispatcher msgDispatcher;

    /**
     * 连接状态，初始化为连接失败
     */
    private int connectStatus = NettyConfig.CONNECT_STATE_FAILURE;
    /**
     * 重连间隔时长
     */
    private int reconnectInterval = NettyConfig.DEFAULT_RECONNECT_BASE_DELAY_TIME;
    /**
     * 连接超时时长
     */
    private int connectTimeout = NettyConfig.DEFAULT_CONNECT_TIMEOUT;
    /**
     * 心跳间隔时间
     */
    private int heartbeatInterval = NettyConfig.DEFAULT_HEARTBEAT_INTERVAL_FOREGROUND;
    /**
     * 应用在后台时心跳间隔时间
     */
    private int foregroundHeartbeatInterval = NettyConfig.DEFAULT_HEARTBEAT_INTERVAL_FOREGROUND;
    /**
     * 应用在前台时心跳间隔时间
     */
    private int backgroundHeartbeatInterval = NettyConfig.DEFAULT_HEARTBEAT_INTERVAL_BACKGROUND;
    /**
     * app前后台状态
     */
    private int appStatus = NettyConfig.APP_STATUS_FOREGROUND;
    /**
     * 消息发送超时重发次数
     */
    private int resendCount = NettyConfig.DEFAULT_RESEND_COUNT;
    /**
     * 消息发送失败重发间隔时长
     */
    private int resendInterval = NettyConfig.DEFAULT_RESEND_INTERVAL;
    /**
     * 消息发送超时定时器管理
     */
    private MsgTimeoutTimerManager msgTimeoutTimerManager;
    /**
     * 当前连接host
     */
    private String currentHost = null;
    /**
     * 当前连接port
     */
    private int currentPort = -1;

    /**
     * getInstance: 采用单例构建的客户端连接对象<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 5:07 PM
     */
    public static NettyTcpClient getInstance() {
        if (null == instance) {
            synchronized (NettyTcpClient.class) {
                if (null == instance) {
                    instance = new NettyTcpClient();
                }
            }
        }
        return instance;
    }

    /**
     * init: 初始化<br/>
     *
     * @param serverUrlList 服务器地址列表
     * @param listener      与应用层交互的listener
     * @param callback      ims连接状态回调
     * @author Jason.Wang
     * @date 2022/11/19 6:05 PM
     */
    @Override
    public void init(Vector<String> serverUrlList, OnEventListener listener, ConnectStatusCallback callback) {
        close();
        isClosed = false;
        this.serverUrlList = serverUrlList;
        onEventListener = listener;
        connectStatusCallback = callback;
        msgDispatcher = new MsgDispatcher();
        msgDispatcher.setOnEventListener(listener);
        loopGroup = new ExecutorServiceFactory();
        loopGroup.initBossLoopGroup();// 初始化重连线程组
        msgTimeoutTimerManager = new MsgTimeoutTimerManager(this);

        resetConnect(true);// 进行第一次连接
    }

    /**
     * resetConnect: 重置连接，也就是重连。首次连接也可认为是重连<br/>
     *
     * @author Jason.Wang
     * @date 2022/11/19 6:04 PM
     */
    @Override
    public void resetConnect() {

    }

    /**
     * resetConnect: 重置连接，也就是重连。首次连接也可认为是重连<br/>
     *
     * @param isFirst 是否首次连接
     * @author Jason.Wang
     * @date 2022/11/19 6:05 PM
     */
    @Override
    public void resetConnect(boolean isFirst) {
        if (!isFirst) {
            try {
                Thread.sleep(NettyConfig.DEFAULT_RECONNECT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 只有第一个调用者才能赋值并调用重连
        if (!isClosed && !isReconnecting) {
            synchronized (this) {
                if (!isClosed && !isReconnecting) {
                    // 标识正在进行重连
                    isReconnecting = true;
                    // 回调ims连接状态
                    onConnectStatusCallback(NettyConfig.CONNECT_STATE_CONNECTING);
                    // 先关闭channel
                    closeChannel();
                    // 执行重连任务
                    loopGroup.execBossTask(new ResetConnectRunnable(isFirst));
                }
            }
        }
    }

    /**
     * isClosed: 标识Server 是否已关闭<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 5:34 PM
     */
    @Override
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * sendMsg: 发送消息<br/>
     *
     * @param msg 消息
     * @author Jason.Wang
     * @date 2022/11/19 5:35 PM
     */
    @Override
    public void sendMsg(Message msg) {
        sendMsg(msg, true);
    }

    /**
     * sendMsg: 发送消息<br/>
     *
     * @param msg                  消息
     * @param isJoinTimeoutManager 是否加入发送超时管理器
     * @author Jason.Wang
     * @date 2022/11/19 5:35 PM
     */
    @Override
    public void sendMsg(Message msg, boolean isJoinTimeoutManager) {
        if (null == msg) {
            System.out.println("发送消息失败，消息为空\tmessage=" + msg);
            return;
        }
        if (!StringUtil.isNullOrEmpty(msg.getMsgId())) {
            if (isJoinTimeoutManager) {
                msgTimeoutTimerManager.add(msg);
            }
        }

        if (channel == null) {
            System.out.println("发送消息失败，channel为空\tmessage=" + msg);
        }

        try {
            channel.writeAndFlush(msg);
        } catch (Exception ex) {
            System.out.println("发送消息失败，reason:" + ex.getMessage() + "\tmessage=" + msg);
        }
    }

    /**
     * getReconnectInterval: 获取重连间隔时长<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:04 PM
     */
    @Override
    public int getReconnectInterval() {
        if (onEventListener != null && onEventListener.getReconnectInterval() > 0) {
            return reconnectInterval = onEventListener.getReconnectInterval();
        }
        return reconnectInterval;
    }

    /**
     * getConnectTimeout: 获取连接超时时长<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:06 PM
     */
    @Override
    public int getConnectTimeout() {
        if (onEventListener != null && onEventListener.getConnectTimeout() > 0) {
            return connectTimeout = onEventListener.getConnectTimeout();
        }
        return connectTimeout;
    }

    /**
     * getForegroundHeartbeatInterval: 获取应用在前台时心跳间隔时间<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:06 PM
     */
    @Override
    public int getForegroundHeartbeatInterval() {
        if (onEventListener != null && onEventListener.getForegroundHeartbeatInterval() > 0) {
            return foregroundHeartbeatInterval = onEventListener.getForegroundHeartbeatInterval();
        }
        return foregroundHeartbeatInterval;
    }

    /**
     * getBackgroundHeartbeatInterval: 获取应用在后台时心跳间隔时间<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:06 PM
     */
    @Override
    public int getBackgroundHeartbeatInterval() {
        if (onEventListener != null && onEventListener.getBackgroundHeartbeatInterval() > 0) {
            return backgroundHeartbeatInterval = onEventListener.getBackgroundHeartbeatInterval();
        }
        return backgroundHeartbeatInterval;
    }

    /**
     * 添加心跳消息管理handler
     */
    public void addHeartbeatHandler() {
        if (channel == null || !channel.isActive() || channel.pipeline() == null) {
            return;
        }

        try {
            // 之前存在的读写超时handler，先移除掉，再重新添加
            if (channel.pipeline().get(IdleStateHandler.class.getSimpleName()) != null) {
                channel.pipeline().remove(IdleStateHandler.class.getSimpleName());
            }
            // 3次心跳没响应，代表连接已断开
            channel.pipeline().addFirst(IdleStateHandler.class.getSimpleName(),
                new IdleStateHandler(heartbeatInterval * 3L, heartbeatInterval, 0, TimeUnit.MILLISECONDS));

            // 重新添加HeartbeatHandler
            if (channel.pipeline().get(HeartbeatHandler.class.getSimpleName()) != null) {
                channel.pipeline().remove(HeartbeatHandler.class.getSimpleName());
            }
            if (channel.pipeline().get(TCPReadHandler.class.getSimpleName()) != null) {
                channel.pipeline()
                    .addBefore(TCPReadHandler.class.getSimpleName(), HeartbeatHandler.class.getSimpleName(),
                        new HeartbeatHandler(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("添加心跳消息管理handler失败，reason：" + e.getMessage());
        }
    }

    /**
     * setAppStatus: 设置app前后台状态<br/>
     *
     * @param appStatus
     * @author Jason.Wang
     * @date 2022/11/19 6:06 PM
     */
    @Override
    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
        if (this.appStatus == NettyConfig.APP_STATUS_FOREGROUND) {
            heartbeatInterval = foregroundHeartbeatInterval;
        } else if (this.appStatus == NettyConfig.APP_STATUS_BACKGROUND) {
            heartbeatInterval = backgroundHeartbeatInterval;
        }
        addHeartbeatHandler();
    }

    /**
     * getHandshakeMsg: 获取由应用层构造的握手消息<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:06 PM
     */
    @Override
    public Message getHandshakeMsg() {
        if (onEventListener != null) {
            return onEventListener.getHandshakeMsg();
        }
        return null;
    }

    /**
     * getHeartbeatMsg: 获取由应用层构造的心跳消息<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:07 PM
     */
    @Override
    public Message getHeartbeatMsg() {
        if (onEventListener != null) {
            return onEventListener.getHeartbeatMsg();
        }

        return null;
    }

    /**
     * getServerSentReportMsgType: 获取应用层消息发送状态报告消息类型<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:07 PM
     */
    @Override
    public int getServerSentReportMsgType() {
        if (onEventListener != null) {
            return onEventListener.getServerSentReportMsgType();
        }
        return 0;
    }

    /**
     * getClientReceivedReportMsgType: 获取应用层消息接收状态报告消息类型<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:07 PM
     */
    @Override
    public int getClientReceivedReportMsgType() {
        if (onEventListener != null) {
            return onEventListener.getClientReceivedReportMsgType();
        }
        return 0;
    }

    /**
     * getHeartbeatInterval: 获取心跳间隔时间<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:24 PM
     */
    public int getHeartbeatInterval() {
        return heartbeatInterval;
    }

    /**
     * getLoopGroup: 获取线程池工厂<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:24 PM
     */
    public ExecutorServiceFactory getLoopGroup() {
        return loopGroup;
    }

    /**
     * getResendCount: 获取应用层消息发送超时重发次数<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:07 PM
     */
    @Override
    public int getResendCount() {
        if (onEventListener != null && onEventListener.getResendCount() != 0) {
            return resendCount = onEventListener.getResendCount();
        }

        return resendCount;
    }

    /**
     * getResendInterval: 获取应用层消息发送超时重发间隔<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:07 PM
     */
    @Override
    public int getResendInterval() {
        if (onEventListener != null && onEventListener.getReconnectInterval() != 0) {
            return resendInterval = onEventListener.getResendInterval();
        }
        return resendInterval;
    }

    /**
     * getMsgDispatcher: 获取消息转发器<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:07 PM
     */
    @Override
    public MsgDispatcher getMsgDispatcher() {
        return msgDispatcher;
    }

    /**
     * getMsgTimeoutTimerManager: 获取消息发送超时定时器<br/>
     *
     * @return MsgTimeoutTimerManager
     * @author Jason.Wang
     * @date 2022/11/19 6:07 PM
     */
    @Override
    public MsgTimeoutTimerManager getMsgTimeoutTimerManager() {
        return msgTimeoutTimerManager;
    }

    private void closeChannel() {
        try {
            if (channel != null) {
                try {
                    removeHandler(HeartbeatHandler.class.getSimpleName());
                    removeHandler(TCPReadHandler.class.getSimpleName());
                    removeHandler(IdleStateHandler.class.getSimpleName());
                } finally {
                    try {
                        channel.close();
                    } catch (Exception ex) {
                    }
                    try {
                        channel.eventLoop().shutdownGracefully();
                    } catch (Exception ex) {
                    }

                    channel = null;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("关闭channel出错，reason:" + ex.getMessage());
        }
    }

    /**
     * removeHandler: 移除指定handler<br/>
     *
     * @param handlerName
     * @author Jason.Wang
     * @date 2022/11/19 6:12 PM
     */
    private void removeHandler(String handlerName) {
        try {
            if (channel.pipeline().get(handlerName) != null) {
                channel.pipeline().remove(handlerName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("移除handler失败，handlerName=" + handlerName);
        }
    }

    /**
     * onConnectStatusCallback: 回调Server连接状态<br/>
     *
     * @param connectStatus
     * @author Jason.Wang
     * @date 2022/11/19 6:12 PM
     */
    private void onConnectStatusCallback(int connectStatus) {
        this.connectStatus = connectStatus;
        switch (connectStatus) {
            case NettyConfig.CONNECT_STATE_CONNECTING: {
                System.out.println("Server连接中...");
                if (connectStatusCallback != null) {
                    connectStatusCallback.onConnecting();
                }
                break;
            }
            case NettyConfig.CONNECT_STATE_SUCCESSFUL: {
                System.out.printf("Server连接成功，host『%s』, port『%s』%n", currentHost, currentPort);
                if (connectStatusCallback != null) {
                    connectStatusCallback.onConnected();
                }
                // 连接成功，发送握手消息
                Message handshakeMsg = getHandshakeMsg();
                if (handshakeMsg != null) {
                    System.out.println("发送握手消息，message=" + handshakeMsg);
                    sendMsg(handshakeMsg, false);
                } else {
                    System.err.println("请应用层构建握手消息！");
                }
                break;
            }

            case NettyConfig.CONNECT_STATE_FAILURE:
            default: {
                System.out.println("Server 连接失败");
                if (connectStatusCallback != null) {
                    connectStatusCallback.onConnectFailed();
                }
                break;
            }
        }
    }

    /**
     * close: 关闭连接，同时释放资源<br/>
     *
     * @author Jason.Wang
     * @date 2022/11/19 5:33 PM
     */
    @Override
    public void close() {
        if (isClosed) {
            return;
        }
        isClosed = true;
        // 关闭channel
        try {
            closeChannel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // 关闭bootstrap
        try {
            if (bootstrap != null) {
                bootstrap.config().group().shutdownGracefully();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            // 释放线程池
            if (loopGroup != null) {
                loopGroup.destroy();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (serverUrlList != null) {
                    serverUrlList.clear();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            // 设置没有在重连
            isReconnecting = false;
            // 释放通道
            channel = null;
            // 释放bootstrap
            bootstrap = null;
        }
    }

    /**
     * 初始化bootstrap
     */
    private void initBootstrap() {
        EventLoopGroup loopGroup = new NioEventLoopGroup(4);
        bootstrap = new Bootstrap();
        bootstrap.group(loopGroup).channel(NioSocketChannel.class);
        // 设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        // 设置禁用nagle算法
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        // 设置连接超时时长
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, getConnectTimeout());
        // 设置初始化Channel
        bootstrap.handler(new TCPChannelInitializerHandler(this));
    }

    /**
     * isNetworkAvailable: 从应用层获取网络是否可用<br/>
     *
     * @return
     * @author Jason.Wang
     * @date 2022/11/19 6:27 PM
     */
    private boolean isNetworkAvailable() {
        if (onEventListener != null) {
            return onEventListener.isNetworkAvailable();
        }
        return false;
    }

    /**
     * toServer: 真正连接服务器的地方<br/>
     *
     * @author Jason.Wang
     * @date 2022/11/19 6:27 PM
     */
    private void toServer() {
        try {
            channel = bootstrap.connect(currentHost, currentPort).sync().channel();
        } catch (Exception e) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.err.println(String.format("连接Server(ip[%s], port[%s])失败", currentHost, currentPort));
            channel = null;
        }
    }

    /**
     * 重连任务
     */
    private class ResetConnectRunnable implements Runnable {

        private boolean isFirst;

        public ResetConnectRunnable(boolean isFirst) {
            this.isFirst = isFirst;
        }

        @Override
        public void run() {
            // 非首次进行重连，执行到这里即代表已经连接失败，回调连接状态到应用层
            if (!isFirst) {
                onConnectStatusCallback(NettyConfig.CONNECT_STATE_FAILURE);
            }

            try {
                // 重连时，释放工作线程组，也就是停止心跳
                loopGroup.destroyWorkLoopGroup();

                while (!isClosed) {
                    if (!isNetworkAvailable()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    // 网络可用才进行连接
                    int status;
                    if ((status = reConnect()) == NettyConfig.CONNECT_STATE_SUCCESSFUL) {
                        onConnectStatusCallback(status);
                        // 连接成功，跳出循环
                        break;
                    }

                    if (status == NettyConfig.CONNECT_STATE_FAILURE) {
                        onConnectStatusCallback(status);
                        try {
                            Thread.sleep(NettyConfig.DEFAULT_RECONNECT_INTERVAL);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                // 标识重连任务停止
                isReconnecting = false;
            }
        }

        /**
         * reConnect: 重连，首次连接也认为是第一次重连<br/>
         *
         * @return
         * @author Jason.Wang
         * @date 2022/11/19 6:45 PM
         */
        private int reConnect() {
            // 未关闭才去连接
            if (!isClosed) {
                try {
                    // 先释放EventLoop线程组
                    if (bootstrap != null) {
                        bootstrap.group().shutdownGracefully();
                    }
                } finally {
                    bootstrap = null;
                }

                // 初始化bootstrap
                initBootstrap();
                return connectServer();
            }
            return NettyConfig.CONNECT_STATE_FAILURE;
        }

        /**
         * connectServer: 连接服务器<br/>
         *
         * @return
         * @author Jason.Wang
         * @date 2022/11/19 6:46 PM
         */
        private int connectServer() {
            // 如果服务器地址无效，直接回调连接状态，不再进行连接
            // 有效的服务器地址示例：127.0.0.1 8860
            if (serverUrlList == null || serverUrlList.size() == 0) {
                return NettyConfig.CONNECT_STATE_FAILURE;
            }

            for (int i = 0; (!isClosed && i < serverUrlList.size()); i++) {
                String serverUrl = serverUrlList.get(i);
                // 如果服务器地址无效，直接回调连接状态，不再进行连接
                if (StringUtil.isNullOrEmpty(serverUrl)) {
                    return NettyConfig.CONNECT_STATE_FAILURE;
                }

                String[] address = serverUrl.split(" ");
                for (int j = 1; j <= NettyConfig.DEFAULT_RECONNECT_COUNT; j++) {
                    // 如果ims已关闭，或网络不可用，直接回调连接状态，不再进行连接
                    if (isClosed || !isNetworkAvailable()) {
                        return NettyConfig.CONNECT_STATE_FAILURE;
                    }

                    // 回调连接状态
                    if (connectStatus != NettyConfig.CONNECT_STATE_CONNECTING) {
                        onConnectStatusCallback(NettyConfig.CONNECT_STATE_CONNECTING);
                    }
                    System.out.printf("正在进行『%s』的第『%d』次连接，当前重连延时时长为『%dms』%n", serverUrl, j,
                        j * getReconnectInterval());

                    try {
                        currentHost = address[0];// 获取host
                        currentPort = Integer.parseInt(address[1]);// 获取port
                        toServer();// 连接服务器

                        // channel不为空，即认为连接已成功
                        if (channel != null) {
                            return NettyConfig.CONNECT_STATE_SUCCESSFUL;
                        } else {
                            // 连接失败，则线程休眠n * 重连间隔时长
                            Thread.sleep(j * getReconnectInterval());
                        }
                    } catch (InterruptedException e) {
                        close();
                        break;// 线程被中断，则强制关闭
                    }
                }
            }

            // 执行到这里，代表连接失败
            return NettyConfig.CONNECT_STATE_FAILURE;
        }
    }
}
