/**
 * Project Name:learning-spring
 * File Name:ICalUtils.java
 * Package Name:me.kany.project.learning.spring.utils.email
 * Date:2019年10月23日 12:39
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.email;

import lombok.Getter;
import lombok.Setter;
import net.fortuna.ical4j.data.CalendarOutputter;

import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.CompatibilityHints;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.validate.ValidationException;
import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.TimeZone;
import java.util.Properties;

import static net.fortuna.ical4j.model.Property.METHOD;
import static net.fortuna.ical4j.model.Property.SEQUENCE;


/**
 * ClassName:ICalUtils<br/>
 * Function: 利用ical4j可以读写iCalendar 数据流，把日程信息作为邮件正文发送会议邀请<br/>
 * Date:2019年10月23日 12:39<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public class ICalUtils {

    /**
     * getSession : 创建Email的Session
     *
     * @param smtpHost
     * @param smtpPort
     * @param isSSL
     * @return
     * @author Jason
     * @date 2019-10-23 23:04
     */
    public static Session getSession(String smtpHost, String smtpPort, boolean isSSL) {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        if (isSSL) {
            // 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.port", smtpPort);
        }
        props.put("mail.smtp.port", smtpPort);
        Session session = Session.getDefaultInstance(props, null);
        // 置true可以在控制台（console)上看到发送邮件的过程
        session.setDebug(false);
        return session;
    }

    public static MimeMessage createMimeMessage(Session session, String _levelType, Method _method, String from, String sendNickName, String to, String _meetingID, String _subject, String _content, String _location, Date _start, Date _end, TimeZone _tz) throws Exception {
        MimeMessage mimeMessage = new MimeMessage(session);
        //设置邮件主题
        mimeMessage.setSubject(_subject);
        //配置是否需要回执
        mimeMessage.addHeader("ReturnReceipt", "1");
        //设置邮件等级
        switch (_levelType) {
            case "高":
                mimeMessage.addHeader("X-Priority", "1");
                break;
            case "中":
                mimeMessage.addHeader("X-Priority", "3");
                break;
            default:
                mimeMessage.addHeader("X-Priority", "5");
                break;
        }
        //设置发件人，如果有昵称就使用昵称
        if (StringUtils.isNotEmpty(sendNickName)) {
            mimeMessage.setFrom(new InternetAddress(from, sendNickName));
        } else {
            mimeMessage.setFrom(new InternetAddress(from));
        }
        //设置收件人
        mimeMessage.addRecipients(Message.RecipientType.TO, to);
        // 以下两步骤的处理很重要，可以避免outlook将生成的日历项当做附件处理
        Multipart multipart = new MimeMultipart();
        /**
         * 会议内容核心拼装
         */
        BodyPart meetingBodyPart = new MimeBodyPart();
        try {
            meetingBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            meetingBodyPart.setHeader("Content-ID", "calendar_message");
            meetingBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(buildCalendar(_meetingID, _method, from, to.split(","), _subject, _content, _location, _start, _end, _tz).toString(), "text/calendar;method=" + _method.getValue() + ";charset=\"UTF-8\"")));
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }

        // 邮件原文组合+发送
        multipart.addBodyPart(meetingBodyPart);
        BodyPart contentBodyPart = new MimeBodyPart();
        contentBodyPart.setContent(_content, "text/html; charset=utf-8");
        multipart.addBodyPart(contentBodyPart);
        mimeMessage.setContent(multipart);
        return mimeMessage;

//        /**
//         * <pre>
//         * setDataHandler的处理方式也是非常关键
//         * 如果直接按照处理
//         * mimeMessage.setContent(iCalAttachment , "text/calendar");
//         * 则在邮件发送的时候会抛出如下异常
//         * javax.activation.UnsupportedDataTypeException: no object DCH for MIME type text/calendar
//         * 这样发送能否成功目前没有找到解决方案
//         * </pre>
//         */
//        byte[] invite = buildCalendar(_meetingID, _method, from, to.split(","), _subject, _content, _location, _start, _end, _tz);
//        iCalAttachment.setDataHandler(new DataHandler(new ByteArrayDataSource(new ByteArrayInputStream(invite), "text/calendar;method=REQUEST;charset=\"UTF-8\"")));
//        multipart.addBodyPart(iCalAttachment);
//        mimeMessage.setContent(multipart);
//        return mimeMessage;
    }

    private static Calendar buildCalendar(String _meetingID, Method _method, String _from, String[] to, String _subject, String _content, String _location, Date _start, Date _end, TimeZone _tz) throws Exception {
        //以下两步骤的处理也是为了防止outlook或者是notes将日历当做附件使用增加的
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_OUTLOOK_COMPATIBILITY, true);
        CompatibilityHints.setHintEnabled(CompatibilityHints.KEY_NOTES_COMPATIBILITY, true);
        VEvent event = new VEvent();

        //配置会议的Id
        event.getProperties().add(new Uid(_meetingID));
        //配置会议的主题
        event.getProperties().add(new Summary(_subject));

        /*
         * @FIXME 配置会议的内容,这个地方为什么不能配置关于HTML的邮件内容呢？
         *
         */
        event.getProperties().add(new Description(_content));
        // 组织者
        event.getProperties().add(new Organizer("mailto:" + _from));
        //配置会议的开始时间
        event.getProperties().add(new DtStart(new DateTime(_start)));
        //配置会议的结束时间
        event.getProperties().add(new DtEnd(new DateTime(_end)));
        //配置会议地址
        event.getProperties().add(new Location(_location));
        // 添加邀请者
        for (int i = 0; i < to.length; i++) {
            Attendee dev = new Attendee(URI.create("mailto:" + to[i]));
            dev.getParameters().add(Role.REQ_PARTICIPANT);
            //这个地方可以设置中文
            //dev.getParameters().add(new Cn("Developer " + (i + 1)));
            event.getProperties().add(dev);
        }
        //使用过 ical4j 构建内容
        Calendar cal = new Calendar();
        cal.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
        cal.getProperties().add(Version.VERSION_2_0);
        cal.getProperties().add(CalScale.GREGORIAN);
        //METHOD:CANCEL 取消会议  METHOD:REQUEST 创建和更新会议
        if (_method.equals(Method.CANCEL)) {
            cal.getProperties().add(Method.CANCEL);
            //如果是取消必须配置等级 设置优先级 其中cancel>update>create
            event.getProperties().add(new Sequence(3));
        } else {
            //发送更新邮件
            cal.getProperties().add(Method.REQUEST);
            /**
             * 提醒,提前10分钟，注意在取消邮件的时候不能使用改方式
             */
            VAlarm valarm = new VAlarm(Duration.ofMinutes(-10));
            valarm.getProperties().add(new Summary("事件提醒"));
            valarm.getProperties().add(Action.DISPLAY);
            valarm.getProperties().add(new Description(_subject));
            // 将VAlarm加入VEvent
            event.getAlarms().add(valarm);
        }

        //配置时区信息
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        VTimeZone tz = registry.getTimeZone(_tz.getID()).getVTimeZone();
        cal.getComponents().add(tz);
        cal.getComponents().add(event);

        // 验证
        try {
            cal.validate();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        return cal;
        // 如果邮件得到的日历内容时乱码，可以考虑通过将日历内容按照某种编码转换成bytes后，在生成stream
//        ByteArrayOutputStream bout = new ByteArrayOutputStream();
//        CalendarOutputter calendarOutputter = new CalendarOutputter();
//        calendarOutputter.output(cal, bout);
//        return bout.toByteArray();
    }
}
