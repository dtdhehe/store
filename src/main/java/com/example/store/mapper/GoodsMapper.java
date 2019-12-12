package com.example.store.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.store.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/9 17:25
 * @description
 **/
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 查询商品列表
     * @param iPage
     * @param wrapper
     * @return
     */
    @Select("select t.*,c.category_name,s.supplier_name from goods t left join category c on c.id=t.category_id " +
            "left join supplier s on s.id=t.supplier_id ${ew.customSqlSegment}")
    IPage<Goods> queryGoodsList(IPage<Goods> iPage, @Param(Constants.WRAPPER) Wrapper wrapper);

}
