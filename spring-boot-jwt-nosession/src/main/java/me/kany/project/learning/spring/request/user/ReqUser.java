/**
 * Project Name:learning-spring
 * File Name:RequestUser.java
 * Package Name:me.kany.project.learning.spring.request.user
 * Date:2020年03月13日 16:17
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.request.user;

import lombok.Data;
import me.kany.project.learning.spring.request.base.ReqBase;

/**
 * ClassName:ReqUser<br/>
 * Function: 请求用户<br/>
 * Date:2020年03月13日 16:17<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Data
public class ReqUser extends ReqBase {
    private String userName;
}
