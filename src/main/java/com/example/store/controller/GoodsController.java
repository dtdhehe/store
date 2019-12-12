package com.example.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.store.entity.Goods;
import com.example.store.service.GoodsService;
import com.example.store.util.CodeUtils;
import com.example.store.util.ConstantUtils;
import com.example.store.util.ResultUtils;
import com.example.store.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
        queryWrapper.eq("t.valid_flag",ConstantUtils.ACTIVE);
        queryWrapper.orderByDesc("create_time");
        IPage<Goods> iPage = new Page<>( Long.valueOf((String) queryMap.get("page")),Long.valueOf((String) queryMap.get("size")));
        if (!StringUtils.isEmpty(queryMap.get("supplierId"))){
            //供货商id
            queryWrapper.eq("t.supplier_id",queryMap.get("supplierId"));
        }
        if (!StringUtils.isEmpty(queryMap.get("goodsName"))){
            //商品名称
            queryWrapper.like("t.goods_name",queryMap.get("goodsName"));
        }
        if (!StringUtils.isEmpty(queryMap.get("categoryId"))){
            //商品类别id
            queryWrapper.eq("t.category_id",queryMap.get("categoryId"));
        }
        iPage = goodsService.queryGoodsList(iPage,queryWrapper);
        Map<String,Object> resultMap = new HashMap<>(8);
        resultMap.put("rows",iPage.getRecords());
        resultMap.put("pages",iPage.getPages());
        resultMap.put("total",iPage.getTotal());
        return ResultUtils.success("查询成功",resultMap);
    }

    /**
     * 根据上传图片返回编码
     * @param file
     * @return
     */
    @PostMapping("/getCodeByImg")
    public ResultVO getCodeByImg(MultipartFile file){
        String decode = "";
        if (file.isEmpty()){
            return ResultUtils.failed("上传图片为空");
        }
        try {
            File toFile = null;
            InputStream is = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            OutputStream os = new FileOutputStream(toFile);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1){
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            is.close();
            decode = CodeUtils.decode(toFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResultUtils.success("扫描成功",decode);
    }

    /**
     * 生成10张条形码
     */
    @GetMapping("/code")
    public void createCodeImg(@RequestParam("path")String path){
        for (int i = 0;i<10;i++){
            long num = (long)(Math.random() * (9999999999999L - 1000000000000L)) + 1000000000000L;
            String msg = Long.toString(num);
            int width = 105, height = 50;
            String filePath = path + "\\" + msg + ".png";
            CodeUtils.encode(msg, width, height, filePath);
        }
    }

}
