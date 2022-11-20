/**
 * Project Name:learning-spring
 * File Name:Message.java
 * Package Name:me.kany.project.learning.spring.netty.server.message
 * Date:2022-11-20 2:05 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.server.message;

/**
 * ClassName: Message<br/>
 * Function: Message<br/>
 * Date: 2022-11-20 2:05 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class Message {
    /**
     * 消息id
     */
    private String msgId;
    /**
     * 消息类型
     */
    private int msgType;
    /**
     * 消息内容类型
     */
    private int contentType;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息时间戳
     */
    private long timestamp;
    /**
     * 状态报告
     */
    private int statusReport;
    /**
     * 扩展字段
     */
    private String extend;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatusReport() {
        return statusReport;
    }

    public void setStatusReport(int statusReport) {
        this.statusReport = statusReport;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
}
