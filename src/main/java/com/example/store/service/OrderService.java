package com.example.store.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.store.entity.Orders;

import java.util.Map;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/15 15:39
 * @description
 **/
public interface OrderService extends IService<Orders> {

    /**
     * 查询会员数量或销售额
     * @param queryWrapper
     * @return
     */
    Map queryAmount(QueryWrapper<Map> queryWrapper);

}
