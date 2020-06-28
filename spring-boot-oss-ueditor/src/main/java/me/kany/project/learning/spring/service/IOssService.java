/**
 * Project Name:learning-spring
 * File Name:IOssService.java
 * Package Name:me.kany.project.learning.spring.service
 * Date:2020年06月28日 11:55
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.service;

import me.kany.project.learning.spring.module.OssCallbackResult;
import me.kany.project.learning.spring.module.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * InterfaceName:IOssService<br/>
 * Function: OSS存储的接口<br/>
 * Date:2020年06月28日 11:55<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public interface IOssService {
    /**
     * getAliyunOSS : 获取子帐号授权信息 <br/>
     *
     * @return
     * @author Jason
     * @date 2020-04-14 23:50
     */
    Map<String, String> getStsOSS();

    /**
     * getPolicy: 获取当前的OSS签名<br/>
     *
     * @return
     * @author Jason.Wang
     * @createTime 2020/6/28 15:04
     */
    OssPolicyResult getPolicy();

    /**
     * getCallback: OSS上传之后的回调<br/>
     *
     * @param request
     * @return
     * @author Jason.Wang
     * @createTime 2020/6/28 15:29
     */
    OssCallbackResult getCallback(HttpServletRequest request);
}
