package com.example.store.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.store.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/15 15:37
 * @description
 **/
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

    /**
     * 查询销售额
     * @param wrapper
     * @return
     */
    @Select("SELECT SUM(order_total) total,SUM(discount_total) dis_total,SUM(order_num) num FROM orders ${ew.customSqlSegment}")
    Map queryAmount(@Param(Constants.WRAPPER) Wrapper wrapper);

}
