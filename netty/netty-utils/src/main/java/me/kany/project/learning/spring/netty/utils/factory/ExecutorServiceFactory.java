/**
 * Project Name:NettyServer
 * File Name:ExecutorServiceFactory.java
 * Package Name:me.kany.project.utils.server.utils.factory
 * Date:2022-11-19 5:21 PM
 * Copyright (c) 2022, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.netty.utils.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: ExecutorServiceFactory<br/>
 * Function: 线程池工厂，负责重连和心跳线程调度<br/>
 * Date: 2022-11-19 5:21 PM<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0
 * @see
 * @since JDK 1.8
 */
public class ExecutorServiceFactory {

    /**
     * 管理线程组，负责重连
     */
    private ExecutorService bossPool;
    /**
     * 工作线程组，负责心跳
     */
    private ExecutorService workPool;

    /**
     * initBossLoopGroup: 初始化boss线程池<br/>
     *
     * @author Jason.Wang
     * @date 2022/11/19 5:24 PM
     */
    public synchronized void initBossLoopGroup() {
        initBossLoopGroup(1);
    }

    /**
     * initBossLoopGroup: 初始化boss线程池<br/>
     *
     * @param size 线程池大小
     * @author Jason.Wang
     * @date 2022/11/19 5:23 PM
     */
    public synchronized void initBossLoopGroup(int size) {
        destroyBossLoopGroup();
        bossPool = Executors.newFixedThreadPool(size);
    }

    /**
     * initWorkLoopGroup: 初始化work线程池<br/>
     *
     * @author Jason.Wang
     * @date 2022/11/19 5:24 PM
     */
    public synchronized void initWorkLoopGroup() {
        initWorkLoopGroup(1);
    }

    /**
     * initWorkLoopGroup: 初始化work线程池<br/>
     *
     * @param size 线程池大小
     * @author Jason.Wang
     * @date 2022/11/19 5:24 PM
     */
    public synchronized void initWorkLoopGroup(int size) {
        destroyWorkLoopGroup();
        workPool = Executors.newFixedThreadPool(size);
    }

    /**
     * execBossTask: 执行boss任务<br/>
     *
     * @param r
     * @author Jason.Wang
     * @date 2022/11/19 5:24 PM
     */
    public void execBossTask(Runnable r) {
        if (bossPool == null) {
            initBossLoopGroup();
        }
        bossPool.execute(r);
    }

    /**
     * execWorkTask: 执行work任务<br/>
     *
     * @param r 线程对象
     * @author Jason.Wang
     * @date 2022/11/19 5:24 PM
     */
    public void execWorkTask(Runnable r) {
        if (workPool == null) {
            initWorkLoopGroup();
        }
        workPool.execute(r);
    }

    /**
     * destroyBossLoopGroup: 释放boss线程池<br/>
     *
     * @author Jason.Wang
     * @date 2022/11/19 5:25 PM
     */
    public synchronized void destroyBossLoopGroup() {
        if (bossPool != null) {
            try {
                bossPool.shutdownNow();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                bossPool = null;
            }
        }
    }

    /**
     * destroyWorkLoopGroup: 释放work线程池<br/>
     *
     * @author Jason.Wang
     * @date 2022/11/19 5:25 PM
     */
    public synchronized void destroyWorkLoopGroup() {
        if (workPool != null) {
            try {
                workPool.shutdownNow();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                workPool = null;
            }
        }
    }

    /**
     * destroy: 释放所有线程池<br/>
     *
     * @author Jason.Wang
     * @date 2022/11/19 5:25 PM
     */
    public synchronized void destroy() {
        destroyBossLoopGroup();
        destroyWorkLoopGroup();
    }
}
