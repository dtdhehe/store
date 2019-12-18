package com.example.store.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.store.entity.OrderInfo;

import java.util.Map;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/15 21:22
 * @description
 **/
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     * 查询销售记录列表
     * @param iPage
     * @param wrapper
     * @return
     */
    IPage<Map> queryInfoList(IPage<Map> iPage, QueryWrapper<Map> wrapper);

}
