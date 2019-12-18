package com.example.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.store.entity.Orders;
import com.example.store.mapper.OrderMapper;
import com.example.store.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/15 15:40
 * @description
 **/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Orders> implements OrderService {
}
