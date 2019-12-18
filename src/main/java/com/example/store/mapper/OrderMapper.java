package com.example.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.store.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/15 15:37
 * @description
 **/
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
