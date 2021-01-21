/**
 * Project Name:learning-spring
 * File Name:IndexController.java
 * Package Name:me.kany.project.learning.spring.controller
 * Date:2021年01月21日 16:11
 * Copyright (c) 2021, Kai.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName:IndexController<br/>
 * Function: 首页控制器<br/>
 * Date:2021年01月21日 16:11<br/>
 *
 * @author Kai.Wang
 * @version 1.0.0
 * @see
 * @since JDK 8
 */
@RestController
@RequestMapping("/")
public class IndexController {

    /**
     * execute : 验证添加XSS之后的数据是否正确<br/>
     * @author Kai.Wang
     * @date 2021/1/21 16:24
     * @param jsonObject
     * @return
     */
    @PostMapping("")
    public Object execute(@RequestBody JSONObject jsonObject) {
        return jsonObject;
    }
}