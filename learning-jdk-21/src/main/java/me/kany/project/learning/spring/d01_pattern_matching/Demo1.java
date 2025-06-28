/**
 * Project Name:learning-spring
 * File Name:Demo1.java
 * Package Name:me.kany.project.learning.spring.d01_pattern_matching
 * Date:2025-06-25 16:59
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d01_pattern_matching;

/**
 * ClassName: Demo1<br/>
 * Function: pattern matching for switch：模式匹配的 instanceof <br/>
 * Date: 2025-06-25 16:59<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 16
 */
public class Demo1 {
    /**
     * main: 测试<br/>
     *
     * @param args
     * @return void
     * @author Jason.Wang
     * @createTime 2025/6/25 17:01
     */
    public static void main(String[] args) {
        judge_one("hello world");
        judge_two("hello world");
    }

    /**
     * judge_one: 判断对象 JDK 1.8+<br/>
     *
     * @param obj
     * @return void
     * @author Jason.Wang
     * @createTime 2025/6/25 17:01
     */
    public static void judge_one(Object obj) {
        if (obj instanceof String) {
            String str = (String)obj;
            System.out.println(str.toUpperCase());
        } else if (obj instanceof Integer) {
            Integer num = (Integer)obj;
            System.out.println(num * 2);
        }
    }

    /**
     * judge_two: 优化后的判断对象 JDK16+<br/>
     *
     * @param obj
     * @return void
     * @author Jason.Wang
     * @createTime 2025/6/25 17:01
     */
    public static void judge_two(Object obj) {
        if (obj instanceof String str) {
            System.out.println(str.toUpperCase());
        } else if (obj instanceof Integer num) {
            System.out.println(num * 2);
        }
    }
}
