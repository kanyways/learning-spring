/**
 * Project Name:learning-spring
 * File Name:DeptTask.java
 * Package Name:me.kany.project.learning.spring.d08_structured_concurrency
 * Date:2025-06-28 10:46
 * Copyright (c) 2025, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.d08_structured_concurrency;

import java.util.concurrent.Callable;

/**
 * ClassName: DeptTask<br/>
 * Function: 部门任务<br/>
 * Date: 2025-06-28 10:46<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 21
 */
public class DeptTask implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("查询部门信息开始...");
        Thread.sleep(1000);
        String deptName = null;
        System.out.println(deptName.length());
        System.out.println("查询部门信息结束...");
        return "所属部门：文娱部";
    }
}
