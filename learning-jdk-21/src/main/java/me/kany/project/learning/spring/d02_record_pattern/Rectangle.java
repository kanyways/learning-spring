/**
 * Project Name:learning-spring
 * File Name:Rectangle.java
 * Package Name:me.kany.project.learning.spring.d02_record_pattern
 * Date:2025-06-26 11:10
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d02_record_pattern;

/**
 * ClassName: Rectangle<br/>
 * Function: Rectangle<br/>
 * Date: 2025-06-26 11:10<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 21
 */
public record Rectangle(double length, double width) implements Shape {
}
