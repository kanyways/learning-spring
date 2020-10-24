/**
 * Project Name:learning-agent
 * File Name:Agent.java
 * Package Name:me.kany.project.learning.agent
 * Date:2020-10-24 2:49 下午
 * Copyright (c) 2020, jason All Rights Reserved.
 */
package me.kany.project.learning.agent;

import java.lang.instrument.Instrumentation;

/**
 * ClassName: Agent<br/>
 * Function: JetBrains的Agent类<br/>
 * Date: 2020-10-24 2:49 下午<br/>
 *
 * @author jason
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
public class Agent {

    /**
     * premain : 在main方法之前运营，与main方法运行在同一个jvm中，被同一个System ClassLoader装载。被统一的安全策略(security policy)和上下文(context)管理
     *
     * @param agentOps
     * @param instrumentation
     * @author jason
     * @date 2020/10/24 3:22 下午
     */
    public static void premain(String agentOps, Instrumentation instrumentation) {
        System.out.println("agentOps = [" + agentOps + "]");
        System.out.println("instrumentation = [" + instrumentation + "]");
        System.out.println("agent is start...");
        instrumentation.addTransformer(new CustomerClassFileTransformer());
    }
}
