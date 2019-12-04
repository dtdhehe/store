package com.example.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.store.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 罗蕾
 * @version 1.0
 * @date 2019/11/4 21:59
 * @description
 **/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
