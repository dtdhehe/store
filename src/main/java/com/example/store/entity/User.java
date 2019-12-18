package com.example.store.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/2 16:13
 * @description
 **/
@Data
public class User {

    @TableId(type = IdType.UUID)
    private String id;
    @TableField(fill = FieldFill.INSERT)
    private String validFlag;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;

    private String userName;

    private String password;

    private String name;

    private String phone;

    private String sex;

    private String status;

    private String userType;

    private BigDecimal shoppingPoints;
    @TableField(exist = false)
    private String levelName;
    @TableField(exist = false)
    private String discount;
}
