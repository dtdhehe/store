package com.example.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.store.entity.Level;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/7 17:46
 * @description
 **/
@Mapper
public interface LevelMapper extends BaseMapper<Level> {

    /**
     * 根据用户积分查询等级名称
     * @param points
     * @return
     */
    @Select("select t.level_name from level t where t.valid_flag='1' and t.level_point <= #{points} order by t.level_point desc limit 1")
    String queryNameByPoints(@Param("points") BigDecimal points);

    /**
     * 根据用户积分查询等级折扣
     * @param points
     * @return
     */
    @Select("select t.discount from level t where t.valid_flag='1' and t.level_point <= #{points} order by t.level_point desc limit 1")
    String queryDiscountByPoints(@Param("points") BigDecimal points);

}
