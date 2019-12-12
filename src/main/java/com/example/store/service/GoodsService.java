package com.example.store.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.store.entity.Goods;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/9 17:36
 * @description
 **/
public interface GoodsService extends IService<Goods> {

    /**
     * 查询商品列表
     * @param iPage
     * @param wrapper
     * @return
     */
    IPage<Goods> queryGoodsList(IPage<Goods> iPage, QueryWrapper<Goods> wrapper);

}
