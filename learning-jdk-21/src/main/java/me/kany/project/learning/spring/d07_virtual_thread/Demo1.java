/**
 * Project Name:learning-spring
 * File Name:Demo1.java
 * Package Name:me.kany.project.learning.spring.d07_virtual_thread
 * Date:2025-06-26 17:31
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d07_virtual_thread;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

/**
 * ClassName: Demo1<br/>
 * Function: 虚拟线程与物理线程执行性能对比
 * -Djdk.virtualThreadScheduler.parallelism=2 -Djdk.virtualThreadScheduler.maxPoolSize=2 <br/>
 * Date: 2025-06-26 17:31<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 21
 */
public class Demo1 {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> threadList = IntStream.range(0, 100000).mapToObj(new IntFunction<Thread>() {
            @Override
            public Thread apply(int value) {
                // 创建一个未启动的虚拟线程
                return Thread.ofVirtual().unstarted(() -> {
                    System.out.println(Thread.currentThread());
                });
                // 本机测试验证虚拟线程执行效率是物理线程的6倍
                //                return Thread.ofPlatform().unstarted(() -> {
                //                    System.out.println(Thread.currentThread());
                //                });
            }
        }).toList();

        long begin = System.currentTimeMillis();
        for (Thread thread : threadList) {
            thread.start();
            thread.join();
        }
        long end = System.currentTimeMillis();
        System.out.println(STR."线程执行时间：\{(end - begin) / 1000}秒");
    }
}
