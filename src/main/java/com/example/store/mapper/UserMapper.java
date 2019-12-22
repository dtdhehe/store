package com.example.store.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.store.entity.User;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/2 16:16
 * @description
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询会员列表
     * @param iPage
     * @param wrapper
     * @return
     */
    @Select("select * from user ${ew.customSqlSegment}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "shoppingPoints",column = "shopping_points"),
            @Result(property = "levelName",column = "shopping_points",
                    one = @One(select = "com.example.store.mapper.LevelMapper.queryNameByPoints")),
            @Result(property = "discount",column = "shopping_points",
                    one = @One(select = "com.example.store.mapper.LevelMapper.queryDiscountByPoints"))
    })
    IPage<User> queryCustomerList(IPage<User> iPage,@Param(Constants.WRAPPER) Wrapper wrapper);

    /**
     * 查询会员数量
     * @param wrapper
     * @return
     */
    @Select("SELECT COUNT(id) total FROM user ${ew.customSqlSegment}")
    Map queryAmount(@Param(Constants.WRAPPER) Wrapper wrapper);

}
