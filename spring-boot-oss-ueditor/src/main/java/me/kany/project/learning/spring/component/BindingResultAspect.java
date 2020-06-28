/**
 * Project Name:learning-spring
 * File Name:BindingResultAspect.java
 * Package Name:me.kany.project.learning.spring.component
 * Date:2020年06月28日 11:29
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.component;

import me.kany.project.learning.spring.common.CommonResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * ClassName:BindingResultAspect<br/>
 * Function: 返回的处理<br/>
 * Date:2020年06月28日 11:29<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Aspect
@Component
@Order(1)
public class BindingResultAspect {

    @Pointcut("execution(public * me.kany.project.learning.spring.*.*(..))")
    public void BindingResult() {
    }

    @Around("BindingResult()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;
                if (result.hasErrors()) {
                    FieldError fieldError = result.getFieldError();
                    if(fieldError!=null){
                        return CommonResult.validateFailed(fieldError.getDefaultMessage());
                    }else{
                        return CommonResult.validateFailed();
                    }
                }
            }
        }
        return joinPoint.proceed();
    }
}
