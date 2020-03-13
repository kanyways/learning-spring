/**
 * Project Name:learning-spring
 * File Name:UserDetailsServiceImpl.java
 * Package Name:me.kany.project.learning.spring.service.impl
 * Date:2020年03月13日 15:54
 * Copyright (c) 2020, Jason.Wang All Rights Reserved.
 */
package me.kany.project.learning.spring.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.kany.project.learning.spring.entity.Users;
import me.kany.project.learning.spring.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

/**
 * ClassName:UserDetailsServiceImpl<br/>
 * Function: UserDetailsService的实现类，使用Spring Boot Security 要有<br/>
 * Date:2020年03月13日 15:54<br/>
 *
 * @author Jason.Wang
 * @version 1.0.0.0
 * @see
 * @since JDK1.8
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", username).or().eq("mobile", username);
        Users users = usersMapper.selectOne(queryWrapper);
        return new User(users.getLoginName(), users.getPassWord(), emptyList());
    }
}
