/**
 * Project Name:learning-spring
 * File Name:Demo.java
 * Package Name:me.kany.project.learning.spring.d03_string_templates
 * Date:2025-06-26 11:26
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d03_string_templates;

/**
 * ClassName: Demo<br/>
 * Function: 字符串模板的使用<br/>
 * Date: 2025-06-26 11:26<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 21
 */
public class Demo {

    public static void main(String[] args) {
        String user = "北京";
        String welcome1 = user + "欢迎你。";
        System.out.println(welcome1);

        String welcome2 = STR."\{user}，欢迎你。";
        System.out.println(welcome2);

        String phone = "13800013800";
        String json = STR."""
            {
                "name":"\{user}",
                "phone":"\{phone}"
            }
            """;
        System.out.println(json);
    }
}
