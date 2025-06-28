/**
 * Project Name:learning-spring
 * File Name:Demo.java
 * Package Name:me.kany.project.learning.spring.d08_structured_concurrency
 * Date:2025-06-28 10:41
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d08_structured_concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.StructuredTaskScope;

/**
 * ClassName: Demo<br/>
 * Function: 结构化并发<br/>
 * Date: 2025-06-28 10:41<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 21
 */
public class Demo {
    public static void main(String[] args) throws InterruptedException {
        Callable<String> empTask = new EmpTask();
        Callable<String> deptTask = new DeptTask();

//        try (StructuredTaskScope<Object> scope = new StructuredTaskScope.ShutdownOnFailure()) { // 一个执行失败时，停止所有任务
        try (StructuredTaskScope<Object> scope = new StructuredTaskScope.ShutdownOnSuccess<>()) { // 一个任务成功时，停止所有任务

            StructuredTaskScope.Subtask<String> empSubtask = scope.fork(empTask);
            StructuredTaskScope.Subtask<String> deptSubtask = scope.fork(deptTask);
            scope.join();

            // 获取两个任务的结果
            String empResult = empSubtask.get();
            String deptResult = deptSubtask.get();
            System.out.println(STR."\{empResult}; \{deptResult}");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
