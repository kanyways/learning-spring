/**
 * Project Name:learning-spring
 * File Name:Demo.java
 * Package Name:me.kany.project.learning.spring.d04_unnamed_patterns_and_variables
 * Date:2025-06-26 16:22
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d04_unnamed_patterns_and_variables;

/**
 * ClassName: Demo<br/>
 * Function: 未命名模式和变量<br/>
 * Date: 2025-06-26 16:22<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 21
 */
public class Demo {
    public static void main(String[] args) {
        int orderIds[] = {1, 2, 3, 4, 5};
        int total = 0;
        for (int _ : orderIds) {
            total += 1;
        }
        System.out.println(total);

        System.out.println(
            getDistance1(new ColoredPoint(new Point(1, 1), Color.RED), new ColoredPoint(new Point(2, 3), Color.BLUE)));
        System.out.println(getDistance2(new ColoredPoint(new Point(1, 1), Color.RED), new ColoredPoint(new Point(2, 3), Color.BLUE)));

    }

    /**
     * getDistance1: 获取两个点之间的距离<br/>
     *
     * @param o1
     * @param o2
     * @return double
     * @author Jason.Wang
     * @createTime 2025/6/26 16:23
     */
    public static double getDistance1(Object o1, Object o2) {
        if (o1 instanceof ColoredPoint(Point p1, Color c1) && o2 instanceof ColoredPoint(Point p2, Color c2)) {
            return Math.sqrt(Math.pow(p1.x() - p2.x(), 2) + Math.pow(p1.y() - p2.y(), 2));
        } else {
            return -1;
        }
    }

    public static double getDistance2(Object o1, Object o2) {
        if (o1 instanceof ColoredPoint(Point p1, _) && o2 instanceof ColoredPoint(Point p2, _)) {
            return Math.sqrt(Math.pow(p1.x() - p2.x(), 2) + Math.pow(p1.y() - p2.y(), 2));
        } else {
            return -1;
        }
    }
}
