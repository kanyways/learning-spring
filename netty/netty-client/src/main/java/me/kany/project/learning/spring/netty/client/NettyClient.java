/**
 * Project Name:NettyServer
 * File Name:NettyClient.java
 * Package Name:me.kany.project.netty.server
 * Date:2022-11-19 5:03 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.client;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.kany.project.learning.spring.netty.client.listener.ClientConnectStatusListener;
import me.kany.project.learning.spring.netty.client.listener.CustomerEventListener;
import me.kany.project.learning.spring.netty.utils.ClientInterface;
import me.kany.project.learning.spring.netty.utils.impl.NettyTcpClient;
import me.kany.project.learning.spring.netty.utils.message.Message;

import java.util.Vector;

/**
 * ClassName: NettyClient<br/>
 * Function: Netty的客户端连接<br/>
 * Date: 2022-11-19 5:03 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class NettyClient {

    private boolean isActive;

    private ClientInterface clientInterface;

    private static final NettyClient instance = new NettyClient();

    public static NettyClient getInstance() {
        return instance;
    }

    public static void main(String[] args) {

        String userId = "100001";
        String token = "token_" + userId;
        String hosts = "[{\"host\":\"127.0.0.1\", \"port\":8866}]";
        NettyClient nettyClient = NettyClient.getInstance();
        nettyClient.init(userId, token, hosts, 0);
    }

    public synchronized void init(String userId, String token, String hosts, int appStatus) {
        if (!isActive()) {
            Vector<String> serverUrlList = convertHosts(hosts);
            if (serverUrlList == null || serverUrlList.size() == 0) {
                System.out.println("init IMLibClientBootstrap error,ims hosts is null");
                return;
            }

            isActive = true;
            System.out.println("init IMLibClientBootstrap, servers=" + hosts);
            if (null != clientInterface) {
                clientInterface.close();
            }
            clientInterface = NettyTcpClient.getInstance();
            updateAppStatus(appStatus);
            clientInterface.init(serverUrlList, new CustomerEventListener(userId, token), new ClientConnectStatusListener());
        }
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * 发送消息
     *
     * @param msg
     */
    public void sendMessage(Message msg) {
        if (isActive) {
            clientInterface.sendMsg(msg);
        }
    }

    private static Vector<String> convertHosts(String hosts) {
        if (hosts != null && hosts.length() > 0) {
            JSONArray hostArray = JSONArray.parseArray(hosts);
            if (null != hostArray && hostArray.size() > 0) {
                Vector<String> serverUrlList = new Vector<String>();
                JSONObject host;
                for (int i = 0; i < hostArray.size(); i++) {
                    host = JSON.parseObject(hostArray.get(i).toString());
                    serverUrlList.add(host.getString("host") + " " + host.getInteger("port"));
                }
                return serverUrlList;
            }
        }
        return null;
    }

    public void updateAppStatus(int appStatus) {
        if (clientInterface == null) {
            return;
        }
        clientInterface.setAppStatus(appStatus);
    }
}
