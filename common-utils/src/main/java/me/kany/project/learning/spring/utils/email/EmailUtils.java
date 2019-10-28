/**
 * Project Name:ethereum-parent
 * File Name:EmailUtils.java
 * Package Name:me.kany.project.learning.spring.utils.email
 * Date:2019年06月20日 19:05
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.email;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * ClassName:EmailUtils<br/>
 * Function: 邮件工具类<br/>
 * Date:2019年06月20日 19:05<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public class EmailUtils {
    Logger logger = LoggerFactory.getLogger(this.getClass().getCanonicalName());

    /**
     * 默认的发件人用户名，defaultEntity用得到
     */
    private static String defaultSenderName = "";
    /**
     * 默认的发件人的名称
     */
    private String sendNickName;
    /**
     * 默认的发件人密码，defaultEntity用得到
     */
    private static String defaultSenderPass = "";
    /**
     * 默认的邮件服务器地址，defaultEntity用得到
     */
    private static String defaultSmtpHost = "";
    private static String defaultSmtPort = "";
    private static boolean defaultIsPort = false;
    /**
     * 邮件服务器地址
     */
    private String smtpHost;
    /**
     * 邮件端口
     */
    private String smtpPort;
    /**
     * 发件人的用户名
     */
    private String sendUserName;
    /**
     * 发件人密码
     */
    private String sendUserPass;
    /**
     * 邮件对象
     */
    private MimeMessage mimeMsg;
    /**
     * 是否是安全模式
     */
    private boolean isSSL;

    private Session session;
    private Properties props;
    /**
     * 附件添加的组件
     */
    private Multipart mp;
    /**
     * 存放附件文件
     */
    private List<FileDataSource> files = new LinkedList<>();

    private void init() {
        if (props == null) {
            props = System.getProperties();
        }
        props.put("mail.smtp.host", smtpHost);
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        if (isSSL) {
            // 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.port", smtpPort);
        }
        props.put("mail.smtp.port", smtpPort);
        session = Session.getDefaultInstance(props, null);
        // 置true可以在控制台（console)上看到发送邮件的过程
        session.setDebug(false);
        // 用session对象来创建并初始化邮件对象
        mimeMsg = new MimeMessage(session);
        // 生成附件组件的实例
        mp = new MimeMultipart();
    }

    /**
     * AliMailSender: 默认的创建方法<br/>
     *
     * @param smtpHost     邮件服务器地址
     * @param smtpPort     右键服务器端口
     * @param isSSL        是否是ssl的连接
     * @param sendUserName 发件邮件地址
     * @param sendNickName 邮件昵称
     * @param sendUserPass 发件邮箱密码
     * @param to           收件人，多个邮箱地址以半角逗号分隔
     * @param cc           抄送，多个邮箱地址以半角逗号分隔
     * @param bcc          密送，多个邮箱地址以半角逗号分隔
     * @param mailSubject  邮件主题
     * @param mailBody     邮件正文
     * @param attachments  附件路径
     * @author Jason.Wang
     * @createTime 2019/6/10 15:32
     */
    private EmailUtils(String smtpHost, String smtpPort, boolean isSSL, String sendUserName, String sendNickName, String sendUserPass, String to, String cc, String bcc, String mailSubject, String mailBody, List<String> attachments) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.isSSL = isSSL;
        this.sendUserName = sendUserName;
        this.sendUserPass = sendUserPass;
        this.sendNickName = sendNickName;


        init();
        setFrom(sendUserName, sendNickName);
        //收件人
        setTo(to);
        //抄送
        setCC(cc);
        //密送
        setBCC(bcc);
        setBody(mailBody);
        setSubject(mailSubject);
        if (attachments != null) {
            for (String attachment : attachments) {
                addFileAffix(attachment);
            }
        }

    }

    /**
     * entity: 邮件实体<br/>
     *
     * @param smtpHost     邮件服务器地址
     * @param smtpPort     右键服务器端口
     * @param isSSL        是否是ssl的连接
     * @param sendUserName 发件邮件地址
     * @param sendNickName 邮件昵称
     * @param sendUserPass 发件邮箱密码
     * @param to           收件人，多个邮箱地址以半角逗号分隔
     * @param cc           抄送，多个邮箱地址以半角逗号分隔
     * @param bcc          密送，多个邮箱地址以半角逗号分隔
     * @param mailSubject  邮件主题
     * @param mailBody     邮件正文
     * @param attachments  附件路径
     * @return
     * @author Jason.Wang
     * @createTime 2019/6/19 9:31
     */
    public static EmailUtils entity(String smtpHost, String smtpPort, boolean isSSL, String sendUserName, String sendNickName, String sendUserPass, String to, String cc, String bcc, String mailSubject, String mailBody, List<String> attachments) {
        return new EmailUtils(smtpHost, smtpPort, isSSL, sendUserName, sendNickName, sendUserPass, to, cc, bcc, mailSubject, mailBody, attachments);
    }

    /**
     * 邮件实体
     *
     * @param smtpHost     邮件服务器地址
     * @param smtpPort     右键服务器端口
     * @param isSSL        是否是ssl的连接
     * @param sendUserName 发件邮件地址
     * @param sendUserPass 发件邮箱密码
     * @param to           收件人，多个邮箱地址以半角逗号分隔
     * @param cc           抄送，多个邮箱地址以半角逗号分隔
     * @param bcc          密送，多个邮箱地址以半角逗号分隔
     * @param mailSubject  邮件主题
     * @param mailBody     邮件正文
     * @param attachments  附件路径
     * @return
     */
    public static EmailUtils entity(String smtpHost, String smtpPort, boolean isSSL, String sendUserName, String sendUserPass, String to, String cc, String bcc, String mailSubject, String mailBody, List<String> attachments) {
        return new EmailUtils(smtpHost, smtpPort, isSSL, sendUserName, null, sendUserPass, to, cc, bcc, mailSubject, mailBody, attachments);
    }

    /**
     * 默认邮件实体，用了默认的发送帐号和邮件服务器
     *
     * @param to          收件人，多个邮箱地址以半角逗号分隔
     * @param cc          抄送，多个邮箱地址以半角逗号分隔
     * @param bcc         密送，多个邮箱地址以半角逗号分隔
     * @param subject     邮件主题
     * @param body        邮件正文
     * @param attachments 附件全路径
     * @return
     */
    public static EmailUtils defaultEntity(String to, String cc, String bcc, String subject, String body, List<String> attachments) {
        return new EmailUtils(defaultSmtpHost, defaultSmtPort, defaultIsPort, defaultSenderName, null, defaultSenderPass, to, cc, bcc, subject, body, attachments);
    }

    /**
     * 设置邮件主题
     *
     * @param mailSubject
     * @return
     */
    private boolean setSubject(String mailSubject) {
        try {
            mimeMsg.setSubject(mailSubject);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 设置邮件内容,并设置其为文本格式或HTML文件格式，编码方式为UTF-8
     *
     * @param mailBody
     * @return
     */
    private boolean setBody(String mailBody) {
        try {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=UTF-8>" + mailBody, "text/html;charset=UTF-8");
            // 在组件上添加邮件文本
            mp.addBodyPart(bp);
        } catch (Exception e) {
            logger.error("设置邮件正文时发生错误！" + e);
            return false;
        }
        return true;
    }

    /**
     * addFileAffix: 添加一个附件<br/>
     *
     * @param filename 邮件附件的地址，只能是本机地址而不能是网络地址，否则抛出异常
     * @return boolean 是否添加成功
     * @author Jason.Wang
     * @createTime 2019/6/20 19:14
     */
    public boolean addFileAffix(String filename) {
        try {
            if (filename != null && filename.length() > 0) {
                BodyPart bp = new MimeBodyPart();
                FileDataSource fileDataSource = new FileDataSource(filename);
                bp.setDataHandler(new DataHandler(fileDataSource));
                // 解决附件名称乱码
                bp.setFileName(MimeUtility.encodeText(fileDataSource.getName(), "utf-8", null));
                // 添加附件
                mp.addBodyPart(bp);
                files.add(fileDataSource);
            }
        } catch (Exception e) {
            logger.error("增加邮件附件：" + filename + "发生错误！" + e);
            return false;
        }
        return true;
    }

    /**
     * delFileAffix: 删除所有附件<br/>
     *
     * @return boolean 是否删除成功
     * @author Jason.Wang
     * @createTime 2019/6/20 19:14
     */
    public boolean delFileAffix() {
        try {
            FileDataSource fileds = null;
            for (Iterator<FileDataSource> it = files.iterator(); it.hasNext(); ) {
                fileds = it.next();
                if (fileds != null && fileds.getFile() != null) {
                    fileds.getFile().delete();
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * setFrom: 设置发件人地址<br/>
     *
     * @param from         发件人地址
     * @param sendNickName 发件人昵称
     * @return boolean 是否设置成功
     * @author Jason.Wang
     * @createTime 2019/6/20 19:13
     */
    private boolean setFrom(String from, String sendNickName) {
        try {
            if (StringUtils.isNotEmpty(sendNickName)) {
                mimeMsg.setFrom(new InternetAddress(from, sendNickName));
            } else {
                mimeMsg.setFrom(new InternetAddress(from));
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * setTo: 设置收件人地址<br/>
     *
     * @param to 收件人的地址
     * @return boolean 是否设置成功
     * @author Jason.Wang
     * @createTime 2019/6/20 19:12
     */
    private boolean setTo(String to) {
        if (StringUtils.isBlank(to)) {
            return false;
        }
        try {
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * setCC: 设置抄送<br/>
     *
     * @param cc 抄送人的地址，使用","
     * @return
     * @author Jason.Wang
     * @createTime 2019/6/19 9:32
     */
    private boolean setCC(String cc) {
        if (StringUtils.isBlank(cc)) {
            return false;
        }
        try {
            mimeMsg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * setBCC: 设置密送<br/>
     *
     * @param bcc 设置密送人的地址，使用“,”间隔
     * @return
     * @author Jason.Wang
     * @createTime 2019/6/19 9:33
     */
    private boolean setBCC(String bcc) {
        if (StringUtils.isBlank(bcc)) {
            return false;
        }
        try {
            mimeMsg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * send: 执行发送邮件<br/>
     *
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @createTime 2019/6/20 19:06
     */
    public boolean send() throws Exception {
        mimeMsg.setContent(mp);
        mimeMsg.saveChanges();
        logger.info("正在发送邮件....");
        Transport transport = session.getTransport("smtp");
        // 连接邮件服务器并进行身份验证
        transport.connect(smtpHost, sendUserName, sendUserPass);
        // 发送邮件
        transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
        logger.info("发送邮件成功！");
        transport.close();
        return true;
    }
}
