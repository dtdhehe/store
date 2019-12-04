package com.example.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.store.entity.Supplier;
import com.example.store.service.SupplierService;
import com.example.store.util.ConstantUtils;
import com.example.store.util.ResultUtils;
import com.example.store.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/3 17:28
 * @description
 **/
@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    /**
     * 新增供货商
     * @param supplier
     * @return
     */
    @PostMapping("/addSupplier")
    public ResultVO addSupplier(@RequestBody Supplier supplier){
        QueryWrapper<Supplier> wrapper = new QueryWrapper<>();
        wrapper.eq("supplier_num",supplier.getSupplierNum());
        Supplier dbSupplier = supplierService.getOne(wrapper);
        if (dbSupplier != null){
            return ResultUtils.failed("该供货商编号已存在");
        }
        return supplierService.save(supplier)?ResultUtils.success("新增供货商成功"):ResultUtils.failed("新增失败");
    }

    /**
     * 更新供货商
     * @param supplier
     * @return
     */
    @PostMapping("/updateSupplier")
    public ResultVO updateSupplier(@RequestBody Supplier supplier){
        QueryWrapper<Supplier> wrapper = new QueryWrapper<>();
        wrapper.eq("supplier_num",supplier.getSupplierNum());
        Supplier dbSupplier = supplierService.getOne(wrapper);
        if (dbSupplier != null && !dbSupplier.getId().equals(supplier.getId())){
            return ResultUtils.failed("该供货商编号已存在");
        }
        return supplierService.saveOrUpdate(supplier)?ResultUtils.success("更新供货商成功"):ResultUtils.failed("更新失败");
    }

    /**
     * 删除供货商
     * @param id
     * @return
     */
    @PostMapping("/deleteSupplier")
    public ResultVO deleteSupplier(String id){
        if (StringUtils.isEmpty(id)){
            return ResultUtils.failed("传入的id不能为空");
        }
        Supplier supplier = supplierService.getById(id);
        supplier.setValidFlag(ConstantUtils.NOTACTIVE);
        return supplierService.saveOrUpdate(supplier)?ResultUtils.success("删除供货商成功"):ResultUtils.failed("删除失败");
    }

    /**
     * 查询供货商列表
     * @param queryMap
     * @return
     */
    @GetMapping("/querySupplierList")
    public ResultVO querySupplierList(@RequestParam Map<String,Object> queryMap){
        QueryWrapper<Supplier> queryWrapper = new QueryWrapper<>();
        IPage<Supplier> supplierIPage = new Page<>( Long.valueOf((String) queryMap.get("page")),Long.valueOf((String) queryMap.get("size")));
        if (!StringUtils.isEmpty(queryMap.get("supplierName"))){
            //供货商名称
            queryWrapper.like("supplier_name",queryMap.get("supplierName"));
        }
        supplierIPage = supplierService.page(supplierIPage,queryWrapper);
        Map<String,Object> resultMap = new HashMap<>(8);
        resultMap.put("rows",supplierIPage.getRecords());
        resultMap.put("pages",supplierIPage.getPages());
        resultMap.put("total",supplierIPage.getTotal());
        return ResultUtils.success("查询成功",resultMap);
    }

}
