/**
 * Project Name:learning-spring
 * File Name:OssController.java
 * Package Name:me.kany.project.learning.spring.controller.oss
 * Date:2020年06月28日 11:11
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.controller.oss;

import me.kany.project.learning.spring.common.CommonResult;
import me.kany.project.learning.spring.module.OssCallbackResult;
import me.kany.project.learning.spring.module.OssPolicyResult;
import me.kany.project.learning.spring.service.IOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName:OssController<br/>
 * Function: OSS的控制类<br/>
 * Date:2020年06月28日 11:11<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Controller
@RequestMapping("/oss")
public class OssController {

    @Autowired
    IOssService ossService;

    @ResponseBody
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    public CommonResult<OssPolicyResult> policy() {
        OssPolicyResult result = ossService.getPolicy();
        return CommonResult.success(result);
    }

    @ResponseBody
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public CommonResult<OssCallbackResult> callback(HttpServletRequest request) {
        OssCallbackResult result = ossService.getCallback(request);
        return CommonResult.success(result);
    }
}
