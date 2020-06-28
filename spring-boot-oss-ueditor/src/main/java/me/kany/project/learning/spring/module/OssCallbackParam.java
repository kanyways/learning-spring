/**
 * Project Name:learning-spring
 * File Name:OssCallbackParam.java
 * Package Name:me.kany.project.learning.spring.module
 * Date:2020年06月28日 15:14
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.module;

import lombok.Data;

/**
 * ClassName:OssCallbackParam<br/>
 * Function: OSS的回调<br/>
 * Date:2020年06月28日 15:14<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Data
public class OssCallbackParam {
    /**
     * 请求的回调地址
     */
    private String callbackUrl;
    /**
     * 回调是传入request中的参数
     */
    private String callbackBody;
    /**
     * 回调时传入参数的格式，比如表单提交形式
     */
    private String callbackBodyType;
}
