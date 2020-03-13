/**
 * Project Name:learning-spring
 * File Name:BaseResponse.java
 * Package Name:me.kany.project.learning.spring.entity
 * Date:2020年03月13日 14:23
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.entity;

import lombok.Data;

/**
 * ClassName:BaseResponse<br/>
 * Function: BaseResponse<br/>
 * Date:2020年03月13日 14:23<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Data
public class BaseResponse {

    private int code;
    private String message;
    private Object data;

    public BaseResponse() {
    }

    public BaseResponse(RetCode retcode) {
        this.code = retcode.getCode();
        this.message = retcode.getMessage();
    }

    public BaseResponse(RetCode retcode, Object data) {
        this.code = retcode.getCode();
        this.message = retcode.getMessage();
        this.data = data;
    }
}
