package com.example.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.store.entity.OrderInfo;
import com.example.store.mapper.OrderInfoMapper;
import com.example.store.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/15 21:22
 * @description
 **/
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper,OrderInfo> implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public IPage<Map> queryInfoList(IPage<Map> iPage, QueryWrapper<Map> wrapper) {
        return orderInfoMapper.queryInfoList(iPage,wrapper);
    }
}
