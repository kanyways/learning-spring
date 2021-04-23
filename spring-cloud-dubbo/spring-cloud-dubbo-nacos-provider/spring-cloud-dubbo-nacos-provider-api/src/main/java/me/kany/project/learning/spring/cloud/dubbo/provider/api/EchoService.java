/**
 * Project Name:learning-spring
 * File Name:EchoService.java
 * Package Name:me.kany.project.learning.spring.cloud.dubbo.provider.api
 * Date:2021年04月23日 15:54
 * Copyright (c) 2021, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.cloud.dubbo.provider.api;

/**
 * InterfaceName:EchoService<br/>
 * Function: 提供服务的接口<br/>
 * Date:2021年04月23日 15:54<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK 8
 */
public interface EchoService {
    String echo(String string);
}