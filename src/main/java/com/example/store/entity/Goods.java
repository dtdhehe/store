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
 * @date 2019/11/9 17:17
 * @description
 **/
@Data
public class Goods {
    @TableId(type = IdType.UUID)
    private String id;
    @TableField(fill = FieldFill.INSERT)
    private String validFlag;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;

    private String categoryId;

    private String categoryName;

    private String goodsCode;

    private String goodsName;

    private String goodsUnit;

    private String supplierId;

    private String supplierName;

    private BigDecimal goodsBid;

    private BigDecimal goodsPrice;

    private BigDecimal goodsPoints;

    private BigDecimal goodsStock;

    private String goodsStatus;
}
