/**
 * Project Name:learning-spring
 * File Name:EmpTask.java
 * Package Name:me.kany.project.learning.spring.d08_structured_concurrency
 * Date:2025-06-28 10:49
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d08_structured_concurrency;

import java.util.concurrent.Callable;

/**
 * ClassName: EmpTask<br/>
 * Function: 员工任务<br/>
 * Date: 2025-06-28 10:49<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 21
 */
public class EmpTask implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("查询员工信息开始...");
        Thread.sleep(3000);
        System.out.println("查询员工信息结束...");
        return "员工：旺财";
    }
}
