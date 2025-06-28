/**
 * Project Name:learning-spring
 * File Name:ColoredPoint.java
 * Package Name:me.kany.project.learning.spring.d04_unnamed_patterns_and_variables
 * Date:2025-06-26 16:25
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d04_unnamed_patterns_and_variables;

/**
 * ClassName: ColoredPoint<br/>
 * Function: 颜色的记录类<br/>
 * Date: 2025-06-26 16:25<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 21
 */
public record ColoredPoint(Point point, Color color) {
}
