package com.example.store.controller;

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

import java.util.HashMap;
import java.util.Map;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/9 17:40
 * @description
 **/
@Api(tags = "商品")
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 新增商品
     * @param goods
     * @return
     */
    @PostMapping("/addGoods")
    public ResultVO addGoods(@RequestBody Goods goods){
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("valid_flag",ConstantUtils.ACTIVE);
        wrapper.eq("goods_code",goods.getGoodsCode());
        Goods dbGoods = goodsService.getOne(wrapper);
        if (dbGoods != null){
            return ResultUtils.failed("该商品已存在");
        }
        return goodsService.save(goods)?ResultUtils.success("新增商品成功"):ResultUtils.failed("新增失败");
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @DeleteMapping("/deleteGoods/{id}")
    public ResultVO deleteGoods(@PathVariable String id){
        if (StringUtils.isEmpty(id)){
            return ResultUtils.failed("传入的id不能为空");
        }
        Goods goods = goodsService.getById(id);
        goods.setValidFlag(ConstantUtils.NOTACTIVE);
        return goodsService.saveOrUpdate(goods)?ResultUtils.success("删除商品成功"):ResultUtils.failed("删除失败");
    }

    /**
     * 更新商品
     * @param goods
     * @return
     */
    @PutMapping("/updateGoods/{id}")
    public ResultVO updateGoods(@RequestBody Goods goods,@PathVariable String id){
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("valid_flag",ConstantUtils.ACTIVE);
        wrapper.eq("goods_code",goods.getGoodsCode());
        Goods dbGoods = goodsService.getOne(wrapper);
        if (dbGoods != null){
            return ResultUtils.failed("该商品已存在");
        }
        return goodsService.saveOrUpdate(goods)?ResultUtils.success("更新商品成功"):ResultUtils.failed("更新失败");
    }

    /**
     * 根据id获得商品
     * @param id
     * @return
     */
    @GetMapping("/getGoods/{id}")
    public ResultVO getGoods(@PathVariable("id") String id){
        if (StringUtils.isEmpty(id)){
            return ResultUtils.failed("传入的id不能为空");
        }
        Goods goods = goodsService.getById(id);
        return ResultUtils.success("查询成功",goods);
    }

    /**
     * 查询商品列表
     * @param queryMap
     * @return
     */
    @GetMapping("/queryGoodsList")
    public ResultVO queryGoodsList(@RequestParam Map<String,Object> queryMap){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("valid_flag",ConstantUtils.ACTIVE);
        queryWrapper.orderByDesc("create_time");
        IPage<Goods> iPage = new Page<>( Long.valueOf((String) queryMap.get("page")),Long.valueOf((String) queryMap.get("size")));
        if (!StringUtils.isEmpty(queryMap.get("supplierName"))){
            //供货商名称
            queryWrapper.like("supplier_name",queryMap.get("supplierName"));
        }
        iPage = goodsService.page(iPage,queryWrapper);
        Map<String,Object> resultMap = new HashMap<>(8);
        resultMap.put("rows",iPage.getRecords());
        resultMap.put("pages",iPage.getPages());
        resultMap.put("total",iPage.getTotal());
        return ResultUtils.success("查询成功",resultMap);
    }

}
