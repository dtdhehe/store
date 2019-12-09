package com.example.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.store.entity.User;
import com.example.store.mapper.UserMapper;
import com.example.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/2 16:17
 * @description
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<User> queryCustomerList(IPage<User> iPage, QueryWrapper<User> wrapper) {
        return userMapper.queryCustomerList(iPage,wrapper);
    }
}
