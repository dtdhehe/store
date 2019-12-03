package com.example.store.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.store.util.ConstantUtils;
import com.example.store.util.DateUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/3 17:50
 * @description
 **/
@Component
public class MybatisObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("validFlag","1",metaObject);
        this.setFieldValByName("createTime",DateUtils.formatDateTime2(),metaObject);
        this.setFieldValByName("updateTime",DateUtils.formatDateTime2(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",DateUtils.formatDateTime2(),metaObject);
    }
}
