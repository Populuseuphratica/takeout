package com.killstan.takeout.service.impl;

import com.killstan.takeout.entity.po.User;
import com.killstan.takeout.mapper.po.UserMapper;
import com.killstan.takeout.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author killStan
 * @since 2022-10-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
