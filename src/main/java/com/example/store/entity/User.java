package com.example.store.entity;

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

    private String id;

    private String validFlag;

    private String createTime;

    private String updateTime;

    private String userName;

    private String password;

    private String name;

    private String phone;

    private String sex;

    private String status;

    private String userType;

    private BigDecimal shoppingPoints;

}
