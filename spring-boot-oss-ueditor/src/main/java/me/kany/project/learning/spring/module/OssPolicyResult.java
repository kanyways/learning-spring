/**
 * Project Name:learning-spring
 * File Name:OssPolicyResult.java
 * Package Name:me.kany.project.learning.spring.module
 * Date:2020年06月28日 15:00
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.module;

import lombok.Data;

/**
 * ClassName:OssPolicyResult<br/>
 * Function: 返回数据<br/>
 * Date:2020年06月28日 15:00<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Data
public class OssPolicyResult {

    /**
     * 访问身份验证中用到用户标识
     */
    private String accessKeyId;
    /**
     * 用户表单上传的策略,经过base64编码过的字符串
     */
    private String policy;
    /**
     * 对policy签名后的字符串
     */
    private String signature;
    /**
     * 上传文件夹路径前缀
     */
    private String dir;
    /**
     * oss对外服务的访问域名
     */
    private String host;
    /**
     * 上传成功后的回调设置
     */
    private String callback;
}
