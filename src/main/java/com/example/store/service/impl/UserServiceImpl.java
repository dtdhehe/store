package com.example.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.store.entity.User;
import com.example.store.mapper.UserMapper;
import com.example.store.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/2 16:17
 * @description
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
}
