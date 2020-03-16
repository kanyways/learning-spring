/**
 * Project Name:learning-spring
 * File Name:ExceptionsHandler.java
 * Package Name:me.kany.project.learning.spring.handler
 * Date:2020年03月16日 10:41
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import me.kany.project.learning.spring.entity.BaseResponse;
import me.kany.project.learning.spring.entity.ConstantCode;
import me.kany.project.learning.spring.entity.RetCode;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ClassName:ExceptionsHandler<br/>
 * Function: 异常控制器<br/>
 * Date:2020年03月16日 10:41<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Log4j2
public class ExceptionsHandler {

    /**
     * typeMismatchExceptionHandler: 类型不匹配异常<br/>
     *
     * @param ex
     * @return
     * @author Jason.Wang
     * @createTime 2020/3/12 22:11
     */
    @ResponseBody
    @ExceptionHandler(value = TypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponse typeMismatchExceptionHandler(TypeMismatchException ex) {
        log.warn("catch typeMismatchException", ex);

        RetCode retCode = new RetCode(ConstantCode.PARAM_EXCEPTION.getCode(), ex.getMessage());
        BaseResponse bre = new BaseResponse(retCode);
        log.warn("typeMismatchException return:{}", JSON.toJSONString(bre));
        return bre;
    }


    /**
     * accessDeniedExceptionHandler: 访问权限异常<br/>
     *
     * @param exception
     * @return
     * @throws Exception
     * @author Jason.Wang
     * @createTime 2020/3/12 22:12
     */
    @ResponseBody
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public BaseResponse accessDeniedExceptionHandler(AccessDeniedException exception) throws Exception {
        log.warn("catch accessDenied exception", exception);
        BaseResponse bre = new BaseResponse(ConstantCode.ACCESS_DENIED);
        log.warn("accessDenied exception return:{}", JSON.toJSONString(bre));
        return bre;
    }

    /**
     * exceptionHandler: 运行时异常<br/>
     *
     * @param exc
     * @return
     * @author Jason.Wang
     * @createTime 2020/3/12 22:12
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse exceptionHandler(RuntimeException exc) {
        log.warn("catch RuntimeException", exc);
        // 默认系统异常
        RetCode retCode = ConstantCode.SYSTEM_EXCEPTION;
        BaseResponse bre = new BaseResponse(retCode);
        log.warn("system RuntimeException return:{}", JSON.toJSONString(bre));
        return bre;
    }
}
