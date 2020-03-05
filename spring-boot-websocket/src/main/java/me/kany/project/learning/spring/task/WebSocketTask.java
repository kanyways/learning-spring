/**
 * Project Name:learning-spring
 * File Name:WebSocketTask.java
 * Package Name:me.kany.project.learning.spring.task
 * Date:2020年03月05日 11:07
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:WebSocketTask<br/>
 * Function: WebSocket的任务<br/>
 * Date:2020年03月05日 11:07<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Slf4j
@Component
public class WebSocketTask {
    @Resource
    private SimpMessagingTemplate template;

    @Scheduled(fixedRateString = "${constant.sendMessageFixedRate}")
    public void taskStart() {
        taskSendMessageStart();
    }

    /**
     * taskSendMessageStart: 调度任务的核心方法<br/>
     *
     * @author Jason.Wang
     * @createTime 2020/3/5 11:10
     */
    public synchronized void taskSendMessageStart() {
        Map<String, Object> map = new HashMap<>();
        map.put("now", LocalDateTime.now());
        template.convertAndSend("/topic/getResponse", map);
    }
}
