/**
 * Project Name:learning-spring
 * File Name:ConstantCode.java
 * Package Name:me.kany.project.learning.spring.entity
 * Date:2020年03月13日 14:24
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.entity;

/**
 * InterfaceName:ConstantCode<br/>
 * Function: ConstantCode<br/>
 * Date:2020年03月13日 14:24<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
public interface ConstantCode {
    /**
     * 执行成功
     */
    RetCode SUCCESS = RetCode.mark(0, "success");
    /**
     * 系统异常
     */
    RetCode SYSTEM_EXCEPTION = RetCode.mark(102000, "system exception");

    /**
     * Business exception.
     */
    RetCode INVALID_TOKEN = RetCode.mark(202052, "invalid token");
    RetCode TOKEN_EXPIRE = RetCode.mark(202053, "token expire");

    /**
     * Auth Exception
     */
    RetCode USER_NOT_LOGGED_IN = RetCode.mark(302000, "user not logged in");
    RetCode ACCESS_DENIED = RetCode.mark(302001, "access denied");

    /**
     * param exception
     */
    RetCode PARAM_EXCEPTION = RetCode.mark(402000, "param exception");
}
