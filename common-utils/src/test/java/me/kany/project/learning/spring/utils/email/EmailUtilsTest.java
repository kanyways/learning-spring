/**
 * Project Name:ethereum-parent
 * File Name:EmailUtilsTest.java
 * Package Name:me.kany.project.learning.spring.utils.email
 * Date:2019年06月20日 19:11
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.utils.email;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName:EmailUtilsTest<br/>
 * Function: EmailUtils测试类<br/>
 * Date:2019年06月20日 19:11<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public class EmailUtilsTest {

    private static final String ALIDM_SMTP_HOST = "smtpdm.aliyun.com";
    //或"80"
    private static final String ALIDM_SMTP_PORT = "25";
    private static final String ALIDM_SMTP_SSL_PORT = "465";

    private static final String ALIDM_EMAIL_USER = "发件邮箱";
    private static final String ALIDM_EMAIL_PWD = "密码";

    @Test
    public void sendMail() {
        String to = "收件人"; // 收件人，多个收件人以半角逗号分隔
        String cc = "";// 抄送，多个抄送以半角逗号分隔
        String bcc = "密送人";
        String subject = "【002001-张三】院校及专业筛选"; // 主题
        String body = "这是邮件的正文"; // 正文，可以用html格式的哟
        // 附件的路径，多个附件也不怕
        List<String> attachments = Arrays.asList("college.xlsx");
        EmailUtils email = EmailUtils.entity(ALIDM_SMTP_HOST, ALIDM_SMTP_SSL_PORT, true, ALIDM_EMAIL_USER, "培升教育", ALIDM_EMAIL_PWD, to, cc, bcc, subject, body, attachments);
        // 发送！
        try {
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
