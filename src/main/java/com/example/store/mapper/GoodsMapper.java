package com.example.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.store.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/9 17:25
 * @description
 **/
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
}
