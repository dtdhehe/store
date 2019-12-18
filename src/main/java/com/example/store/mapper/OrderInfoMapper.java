package com.example.store.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.store.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/15 21:21
 * @description
 **/
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    /**
     * 查询商品列表
     * @param iPage
     * @param wrapper
     * @return
     */
    @Select("SELECT t.create_time,t.goods_name,c.category_name,s.supplier_name,u1.`name` sales_name," +
            "t.info_num,t.goods_price,u2.`name` customer_name,u2.phone,(t.info_num * t.goods_price) amount " +
            "FROM order_info t " +
            "LEFT JOIN goods g ON g.id = t.goods_id " +
            "LEFT JOIN category c ON c.category_code = g.category_code " +
            "LEFT JOIN supplier s ON s.id = g.supplier_id " +
            "LEFT JOIN orders o ON o.id = t.order_id " +
            "LEFT JOIN `user` u1 ON u1.id = o.sales_id " +
            "LEFT JOIN `user` u2 ON u2.id = o.customer_id ${ew.customSqlSegment}")
    IPage<Map> queryInfoList(IPage<Map> iPage, @Param(Constants.WRAPPER) Wrapper wrapper);

}
