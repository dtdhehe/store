package com.example.store.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.store.entity.User;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/2 16:17
 * @description
 **/
public interface UserService extends IService<User> {

    /**
     * 查询会员列表
     * @param iPage
     * @param wrapper
     * @return
     */
    IPage<User> queryCustomerList(IPage<User> iPage,QueryWrapper<User> wrapper);

}
