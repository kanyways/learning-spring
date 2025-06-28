/**
 * Project Name:learning-spring
 * File Name:Circle.java
 * Package Name:me.kany.project.learning.spring.d02_record_pattern
 * Date:2025-06-26 11:10
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d02_record_pattern;

/**
 * ClassName: Circle<br/>
 * Function: 111<br/>
 * Date: 2025-06-26 11:10<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 21
 */
public record Circle(double radius) implements Shape { // radius：圆的半径
}
