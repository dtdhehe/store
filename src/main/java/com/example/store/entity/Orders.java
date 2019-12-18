package com.example.store.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/14 23:10
 * @description
 **/
@Data
public class Orders {

    @TableId(type = IdType.UUID)
    private String id;
    @TableField(fill = FieldFill.INSERT)
    private String validFlag;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;

    private String customerId;

    private String salesId;

    private BigDecimal orderTotal;

    private Integer orderNum;

    private BigDecimal discountTotal;
    @TableField(exist = false)
    private List<OrderInfo> infoList;
}
