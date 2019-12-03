package com.example.store.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/3 17:24
 * @description
 **/
@Data
public class Supplier {

    @TableId(type = IdType.UUID)
    private String id;
    @TableField(fill = FieldFill.INSERT)
    private String validFlag;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;

    private String supplierNum;

    private String supplierName;

    private String supplierLinkname;

    private String supplierLinktel;
}
