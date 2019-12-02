package com.example.store.vo;

import lombok.Data;

/**
 * @author 陈珊珊
 * @version 1.0
 * @date 2019/11/5 23:30
 * @description
 **/
@Data
public class ResultVO {
    private Integer code;
    private String msg;
    private Object data;
}
