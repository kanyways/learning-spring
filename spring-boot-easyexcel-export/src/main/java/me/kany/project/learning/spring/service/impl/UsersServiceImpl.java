package me.kany.project.learning.spring.service.impl;

import me.kany.project.learning.spring.entity.Users;
import me.kany.project.learning.spring.mapper.UsersMapper;
import me.kany.project.learning.spring.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jason.Wang
 * @since 2019-10-25
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
