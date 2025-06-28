/**
 * Project Name:learning-spring
 * File Name:Demo.java
 * Package Name:me.kany.project.learning.spring.d02_record_pattern
 * Date:2025-06-26 11:08
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d02_record_pattern;

/**
 * ClassName: Demo<br/>
 * Function: 使用Records记录类<br/>
 * Date: 2025-06-26 11:08<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 21
 */
public class Demo {
    public static void main(String[] args) {
        Circle circle = new Circle(5);
        System.out.println(String.format("半径: %.2f", circle.radius()));

        Rectangle rectangle = new Rectangle(10, 15);
        System.out.println(String.format("长: %.2f", rectangle.length()));
        System.out.println(String.format("宽: %.2f", rectangle.width()));

        // Record Pattern 记录模式的使用
        System.out.println(getArea1(rectangle));
        System.out.println(getArea2(rectangle));
        System.out.println(getArea3(rectangle));
    }

    /**
     * getArea: 获取面积 JDK 16+<br/>
     *
     * @param shape
     * @return double
     * @author Jason.Wang
     * @date 2025/6/26 11:09
     */
    public static double getArea1(Shape shape) {
        if (shape instanceof Circle circle) {
            System.out.println(String.format("圆的面积: %.2f", Math.PI * circle.radius() * circle.radius()));
            return Math.PI * circle.radius() * circle.radius();
        } else if (shape instanceof Rectangle rectangle) {
            System.out.println(String.format("长方形的面积: %.2f", rectangle.length() * rectangle.width()));
            return rectangle.length() * rectangle.width();
        }
        return 0;
    }

    /**
     * getArea2: 获取面积 JDK 21+<br/>
     *
     * @param shape
     * @return double
     * @author Jason.Wang
     * @date 2025/6/26 11:09
     */
    public static double getArea2(Shape shape) {
        if (shape instanceof Circle(double radius)) {
            System.out.printf("圆的面积: %.2f%n", Math.PI * radius * radius);
            return Math.PI * radius * radius;
        } else if (shape instanceof Rectangle(double length, double width)) {
            System.out.printf("长方形的面积: %.2f%n", length * width);
            return length * width;
        }
        return 0;
    }

    /**
     * getArea3: 获取面积，使用Switch Pattern + Record Pattern JDK 21+<br/>
     *
     * @param shape
     * @return double
     * @author Jason.Wang
     * @date 2025/6/26 11:09
     */
    public static double getArea3(Shape shape) {
        return switch (shape) {
            case Circle(double radius) -> {
                System.out.printf("圆的面积: %.2f%n", Math.PI * radius * radius);
                yield Math.PI * radius * radius;
            }
            case Rectangle(double length, double width) -> {
                System.out.printf("长方形的面积: %.2f%n", length * width);
                yield length * width;
            }
            default -> {
                yield 0;
            }
        };
    }
}
