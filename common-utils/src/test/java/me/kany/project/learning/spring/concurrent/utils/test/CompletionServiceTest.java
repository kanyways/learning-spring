/**
 * Project Name:ethereum-parent
 * File Name:CompletionServiceTest.java
 * Package Name:me.kany.project.learning.spring.concurrent.utils.test
 * Date:2019年06月19日 16:04
 * Copyright (c) 2019, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.concurrent.utils.test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.concurrent.*;

/**
 * ClassName:CompletionServiceTest<br/>
 * Function: CompletionService的线程测试类<br/>
 * Date:2019年06月19日 16:04<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
public class CompletionServiceTest {


    @Test
    public void sumValue() {
        int taskSize = 3;
        /**
         * 手动创建线程池
         */
        // 创建线程工厂
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("send-email-pool-%d").build();
        // 创建通用线程池
        /**
         * 参数含义：
         *      corePoolSize : 线程池中常驻的线程数量。核心线程数，默认情况下核心线程会一直存活，即使处于闲置状态也不会受存keepAliveTime限制。除非将allowCoreThreadTimeOut设置为true。
         *      maximumPoolSize : 线程池所能容纳的最大线程数。超过这个数的线程将被阻塞。当任务队列为没有设置大小的LinkedBlockingDeque时，这个值无效。
         *      keepAliveTime : 当线程数量多于 corePoolSize 时，空闲线程的存活时长，超过这个时间就会被回收
         *      unit : keepAliveTime 的时间单位
         *      workQueue : 存放待处理任务的队列，该队列只接收 Runnable 接口
         *      threadFactory : 线程创建工厂
         *      handler : 当线程池中的资源已经全部耗尽，添加新线程被拒绝时，会调用RejectedExecutionHandler的rejectedExecution方法，参考 ThreadPoolExecutor 类中的内部策略类
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(taskSize, taskSize, 0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy());


        CompletionService<Integer> completionService = new ExecutorCompletionService<>(threadPoolExecutor);

        completionService.submit(() -> {
            return 1;
        });

        completionService.submit(() -> {
            return 2;
        });

        completionService.submit(() -> {
            return 3;
        });

        Integer total = 0;
        for (int i = 0; i < taskSize; i++) {
            try {
                Future<Integer> future = completionService.take();
                total += future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        threadPoolExecutor.shutdown();
        log.debug("最后计算值为:{}", total);
    }
}
