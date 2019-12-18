package com.example.store.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.store.entity.Goods;
import com.example.store.service.GoodsService;
import com.example.store.util.ConstantUtils;
import com.example.store.util.ResultUtils;
import com.example.store.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/16 21:02
 * @description
 **/
@Api(tags = "库存")
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 更新库存
     * @param id
     * @param stock
     * @return
     */
    @PutMapping("/{id}")
    public ResultVO editStock(@PathVariable String id,@RequestBody String stock){
        if (StringUtils.isEmpty(id)){
            return ResultUtils.failed("id不能为空");
        }
        Goods goods = goodsService.getById(id);
        if (goods == null){
            return ResultUtils.failed("id有误");
        }
        JSONObject json = JSON.parseObject(stock);
        goods.setGoodsStock(new BigDecimal((String)json.get("stock")));
        goodsService.saveOrUpdate(goods);
        goods.setEdit(false);
        return ResultUtils.success("修改成功",goods);
    }

}
