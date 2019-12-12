package com.example.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.store.entity.Goods;
import com.example.store.mapper.GoodsMapper;
import com.example.store.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/9 17:37
 * @description
 **/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper,Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public IPage<Goods> queryGoodsList(IPage<Goods> iPage, QueryWrapper<Goods> wrapper) {
        return goodsMapper.queryGoodsList(iPage,wrapper);
    }
}
