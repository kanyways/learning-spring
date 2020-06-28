/**
 * Project Name:learning-spring
 * File Name:OssCallbackResult.java
 * Package Name:me.kany.project.learning.spring.module
 * Date:2020年06月28日 15:28
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.module;

import lombok.Data;

/**
 * ClassName:OssCallbackResult<br/>
 * Function: OSS的回调<br/>
 * Date:2020年06月28日 15:28<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Data
public class OssCallbackResult {
    /**
     * 文件名称
     */
    private String filename;
    /**
     * 文件大小
     */
    private String size;
    /**
     * 文件的mimeType
     */
    private String mimeType;
    /**
     * 图片文件的宽
     */
    private String width;
    /**
     * 图片文件的高
     */
    private String height;
}
