/**
 * Project Name:learning-spring
 * File Name:Demo2.java
 * Package Name:me.kany.project.learning.spring.d01_pattern_matching
 * Date:2025-06-26 10:50
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d01_pattern_matching;

/**
 * ClassName: Demo2<br/>
 * Function: pattern matching for switch：模式匹配的 switch<br/>
 * Date: 2025-06-26 10:50<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 21
 */
public class Demo2 {

    /**
     * main: 测试方法<br/>
     *
     * @param args 测试参数
     */
    public static void main(String[] args) {
        // 匹配对象
        System.out.println(formatter(123.456));
        // Switch 匹配对象
        System.out.println(formatter2(123.456));
        // 增强Switch 匹配对象
        System.out.println(formatter3(123.456));
    }

    /**
     * formatter: 匹配对象 JDK 16+<br/>
     *
     * @param obj
     * @return
     * @author Jason.Wang
     * @date 2025/6/26 10:53
     */
    public static String formatter(Object obj) {
        String formatted = "default";
        if (obj instanceof Integer num) {
            formatted = String.format("整数：%d", num);
        } else if (obj instanceof Long num) {
            formatted = String.format("长整数：%d", num);
        } else if (obj instanceof Double num) {
            formatted = String.format("小数：%f", num);
        } else if (obj instanceof String str) {
            formatted = String.format("字符串：%s", str);
        }
        return formatted;
    }

    /**
     * formatter: 匹配对象 JDK 14+<br/>
     *
     * @param obj
     * @return
     * @author Jason.Wang
     * @date 2025/6/26 10:53
     */
    public static String formatter2(Object obj) {
        String formatted = "default";
        switch (obj) {
            case Integer num:
                formatted = String.format("整数：%d", num);
                break;
            case Long num:
                formatted = String.format("长整数：%d", num);
                break;
            case Double num:
                formatted = String.format("小数：%f", num);
                break;
            case String str:
                formatted = String.format("字符串：%s", str);
                break;
            default:
                throw new IllegalArgumentException("Unsupported type: " + obj.getClass().getName());
        }
        return formatted;
    }

    /**
     * formatter: 匹配对象 JDK 21+<br/>
     *
     * @param obj
     * @return
     * @author Jason.Wang
     * @date 2025/6/26 10:53
     */
    public static String formatter3(Object obj) {
        return switch (obj) {
            case Integer num -> String.format("整数：%d", num);
            case Long num -> String.format("长整数：%d", num);
            case Double num -> String.format("小数：%f", num);
            case String str -> String.format("字符串：%s", str);
            default -> "未知类型";
        };
    }
}
