/**
 * Project Name:learning-spring
 * File Name:IErrorCode.java
 * Package Name:me.kany.project.learning.spring.common
 * Date:2020年06月28日 11:34
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.common;

/**
 * InterfaceName:IErrorCode<br/>
 * Function: 错误的接口对象<br/>
 * Date:2020年06月28日 11:34<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public interface IErrorCode {
    /**
     * getCode: 状态编码<br/>
     *
     * @return
     * @author Jason.Wang
     * @createTime 2020/6/28 11:35
     */
    Long getCode();

    /**
     * getMessage: 信息<br/>
     *
     * @return
     * @author Jason.Wang
     * @createTime 2020/6/28 11:35
     */
    String getMessage();
}
