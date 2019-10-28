/**
 * Project Name:learning-spring
 * File Name:ICalUtilsTest.java
 * Package Name:me.kany.project.learning.spring.utils.email
 * Date:2019年10月23日 17:02
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.email;

import lombok.extern.slf4j.Slf4j;
import net.fortuna.ical4j.model.property.Method;
import org.junit.Before;
import org.junit.Test;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;

/**
 * ClassName:ICalUtilsTest<br/>
 * Function: 测试类型<br/>
 * Date:2019年10月23日 17:02<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
public class ICalUtilsTest {
    /**
     * 邮件SMTP服务器
     */
    private static final String smtpHost = "smtpdm.aliyun.com";
    /**
     * 非SSL模式下的端口
     */
    private static final String smtp_port = "25";
    /**
     * SSL模式下的端口
     */
    private static final String ssl_smtp_port = "465";
    /**
     * 发送者邮箱
     */
    private static final String sendUserName = "service@notice.peisonedu.com";
    /**
     * 发送者昵称
     */
    private static final String sendUserNickName = "培升教育";
    /**
     * 发送者密码
     */
    private static final String sendUserPass = "QX8wnsHcM99m0mgE";

    /**
     * 邮件的唯一Id，取消更新需要配置相同的邮件Id
     */
    private String uuid;
    /**
     * 时区的Id
     */
    private ZoneId zoneId;
    /**
     * 是否使用SSL链接
     */
    private boolean isSSL = true;
    /**
     * 开始时间转换对象
     */
    private Instant currentTimeInstant;
    /**
     * 结束时间转换Date对象
     */
    private Instant nextTimeInstant;
    /**
     * Email的会话对象
     */
    private Session session = null;
    /**
     * 会议开始时间
     */
    private LocalDateTime currentTime;
    /**
     * 会议结束时间
     */
    private LocalDateTime nextTime;
    /**
     * 时间格式化
     */
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    /**
     * 邮箱等级
     */
    private String level = "中";
    /**
     * 多个邮箱用户请使用英文“,”进行分隔
     */
    private String receiveUser = "curel_moon@163.com,h0jo37@icloud.com";

    /**
     * initData: 初始化通用配置信息<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/10/24 10:40
     */
    @Before
    public void initData() {
        uuid = UUID.randomUUID().toString();
        log.debug("邮件的uuid：{}", uuid);
        zoneId = ZoneId.of("Asia/Shanghai");
        currentTime = LocalDateTime.now();
        //配置时间
        currentTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), currentTime.getHour() + 2, 30);
        nextTime = currentTime.plus(1, ChronoUnit.HOURS);
        currentTimeInstant = currentTime.atZone(zoneId).toInstant();
        nextTimeInstant = nextTime.atZone(zoneId).toInstant();

        if (isSSL) {
            session = ICalUtils.getSession(smtpHost, ssl_smtp_port, true);
        } else {
            session = ICalUtils.getSession(smtpHost, smtp_port, true);
        }
    }

    /**
     * sendMail: 所有功能都执行一次<br/>
     *
     * @throws InterruptedException
     * @author Jason.Wang
     * @createTime 2019/10/24 11:48
     */
    @Test
    public void sendMail() throws InterruptedException {
        createMeeting();
        //形成间隔
        Thread.sleep(3 * 1000L);
        updateMeeting();
        //形成间隔，不然无法取消
        Thread.sleep(10 * 1000L);
        cancelMeeting();
    }

    /**
     * createMeeting : 发送邮件信息
     *
     * @author Jason
     * @date 2019-10-23 23:28
     */
    @Test
    public void createMeeting() {
        Transport transport = null;
        try {
            transport = session.getTransport("smtp");
            // 连接邮件服务器并进行身份验证
            transport.connect(smtpHost, sendUserName, sendUserPass);
            //构建需要发送的邮件体
            MimeMessage mimeMessage = ICalUtils.createMimeMessage(session, level, Method.REQUEST, sendUserName, sendUserNickName, receiveUser, uuid, "创建会议", "计划" + currentTime.format(dateTimeFormatter) + "至" + nextTime.format(dateTimeFormatter) + "的会议地点为：<b>世界之窗</b>，请大家准备好手头工作。", "世界之窗", Date.from(currentTimeInstant), Date.from(nextTimeInstant), TimeZone.getTimeZone(zoneId));
            //发送邮件
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            log.info("发送邮件成功！");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * updateMeeting: 更新邮件内容<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/10/24 10:17
     */
    @Test
    public void updateMeeting() {
        Transport transport = null;
        try {
            transport = session.getTransport("smtp");
            // 连接邮件服务器并进行身份验证
            transport.connect(smtpHost, sendUserName, sendUserPass);
            //构建需要发送的邮件体
            MimeMessage mimeMessage = ICalUtils.createMimeMessage(session, level, Method.REQUEST, sendUserName, sendUserNickName, receiveUser, uuid, "更新会议", "原计划" + currentTime.format(dateTimeFormatter) + "至" + nextTime.format(dateTimeFormatter) + "的会议地点修改为：会议室举行，请大家继续工作。", "会议室", Date.from(currentTimeInstant), Date.from(nextTimeInstant), TimeZone.getTimeZone(zoneId));
            //发送邮件
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            log.info("发送邮件成功！");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * cancelMeeting: 撤销会议<br/>
     *
     * @author Jason.Wang
     * @createTime 2019/10/24 10:17
     */
    @Test
    public void cancelMeeting() {
        Transport transport = null;
        try {
            transport = session.getTransport("smtp");
            // 连接邮件服务器并进行身份验证
            transport.connect(smtpHost, sendUserName, sendUserPass);
            //构建需要发送的邮件体
            MimeMessage mimeMessage = ICalUtils.createMimeMessage(session, level, Method.CANCEL, sendUserName, sendUserNickName, receiveUser, uuid, "取消会议", "原计划" + currentTime.format(dateTimeFormatter) + "至" + nextTime.format(dateTimeFormatter) + "的会议取消了，请大家继续工作。", "世界之窗", Date.from(currentTimeInstant), Date.from(nextTimeInstant), TimeZone.getTimeZone(zoneId));
            //发送邮件
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            log.info("发送邮件成功！");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
