package com.example.store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.store.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/2 16:16
 * @description
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
