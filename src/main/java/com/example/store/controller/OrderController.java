package com.example.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.store.entity.*;
import com.example.store.service.GoodsService;
import com.example.store.service.OrderInfoService;
import com.example.store.service.OrderService;
import com.example.store.util.ConstantUtils;
import com.example.store.util.ResultUtils;
import com.example.store.vo.ResultVO;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/15 15:42
 * @description
 **/
@Api(tags = "订单")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 购买商品
     * @param map
     * @return
     */
    @PostMapping("")
    public ResultVO buyGoods(@RequestBody Map<String,Object> map){
        //当前销售人员
        User sales = (User)SecurityUtils.getSubject().getPrincipal();
        List<Map> goodsList = (List<Map>) map.get("goods");
        String customerId = (String) map.get("id");
        if (goodsList.size() == 0){
            return ResultUtils.failed("数据不能为空");
        }
        Orders order = new Orders();
        order.setCustomerId(customerId);
        order.setSalesId(sales.getId());
        order.setOrderNum(goodsList.size());
        order.setDiscountTotal(new BigDecimal((String)map.get("discountTotal")));
        orderService.save(order);
        //订单总额
        BigDecimal total = new BigDecimal(0);
        for (Map item : goodsList){
            OrderInfo info = new OrderInfo();
            info.setOrderId(order.getId());
            //商品id
            String goodsId = (String) item.get("goodsId");
            Goods goods = goodsService.getById(goodsId);
            info.setGoodsId(goodsId);
            info.setGoodsName(goods.getGoodsName());
            info.setGoodsPrice(goods.getGoodsPrice());
            //该商品数量
            Integer num = Integer.valueOf((String)item.get("soldNum"));
            info.setInfoNum(num);
            //该商品总额
            BigDecimal infoTotal = goods.getGoodsPrice();
            infoTotal = infoTotal.multiply(new BigDecimal(num));
            info.setInfoTotal(infoTotal);
            orderInfoService.save(info);
            total = total.add(infoTotal);
        }
        order.setOrderTotal(total);
        orderService.saveOrUpdate(order);
        return ResultUtils.success("付款成功");
    }

    @GetMapping("")
    public ResultVO querySaleList(@RequestParam Map<String,Object> queryMap){
        QueryWrapper<Map> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t.valid_flag",ConstantUtils.ACTIVE);
        queryWrapper.orderByDesc("t.create_time");
        IPage<Map> iPage = new Page<>( Long.valueOf((String) queryMap.get("page")),Long.valueOf((String) queryMap.get("size")));
        if (!StringUtils.isEmpty(queryMap.get("supplierId"))){
            //供货商id
            queryWrapper.eq("t.supplier_id",queryMap.get("supplierId"));
        }
        if (!StringUtils.isEmpty(queryMap.get("goodsName"))){
            //商品名称
            queryWrapper.like("t.goods_name",queryMap.get("goodsName"));
        }
        if (!StringUtils.isEmpty(queryMap.get("categoryCode"))){
            //商品类别id
            queryWrapper.likeRight("t.category_code",queryMap.get("categoryCode"));
        }
        iPage = orderInfoService.queryInfoList(iPage,queryWrapper);
        Map<String,Object> resultMap = new HashMap<>(8);
        resultMap.put("rows",iPage.getRecords());
        resultMap.put("pages",iPage.getPages());
        resultMap.put("total",iPage.getTotal());
        return ResultUtils.success("查询成功",resultMap);
    }
}
