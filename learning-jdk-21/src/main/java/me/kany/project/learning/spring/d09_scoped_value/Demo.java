/**
 * Project Name:learning-spring
 * File Name:Demo.java
 * Package Name:me.kany.project.learning.spring.d09_scoped_value
 * Date:2025-06-28 11:03
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d09_scoped_value;

/**
 * ClassName: Demo<br/>
 * Function: 作用域值<br/>
 * Date: 2025-06-28 11:03<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 21
 */
public class Demo {

    public static final ScopedValue<String> scopeValue = ScopedValue.newInstance();

    public static void main(String[] args) {
        ScopedValue.runWhere(scopeValue, "旺财", Demo::print_one);
    }

    public static void print_one() {
        System.out.println(STR."print_one：\{scopeValue.get()}");
        ScopedValue.runWhere(scopeValue, "小花", Demo::print_two);
        print_two();
    }

    public static void print_two() {
        System.out.println(STR."print_two：\{scopeValue.get()}");
    }
}
