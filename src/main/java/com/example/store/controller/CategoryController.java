package com.example.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.store.entity.Category;
import com.example.store.service.CategoryService;
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
 * @version 1.0
 * @date 2019/11/4 22:03
 * @description
 **/
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增供货商
     * @param category
     * @return
     */
    @PostMapping("/addCategory")
    public ResultVO addCategory(@RequestBody Category category){
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("category_pcode",category.getCategoryPcode());
        wrapper.orderByDesc("category_code");
        List<Category> supplierList = categoryService.list(wrapper);
        if (supplierList.size() != 0){
            return ResultUtils.failed("该供货商编号已存在");
        }
        return categoryService.save(category)?ResultUtils.success("新增类别成功"):ResultUtils.failed("新增失败");
    }

    /**
     * 更新类别
     * @param category
     * @return
     */
    @PostMapping("/updateCategory")
    public ResultVO updateCategory(@RequestBody Category category){
        return categoryService.saveOrUpdate(category)?ResultUtils.success("更新类别成功"):ResultUtils.failed("更新失败");
    }

    /**
     * 删除类别
     * @param id
     * @return
     */
    @PostMapping("/deleteCategory")
    public ResultVO deleteCategory(String id){
        if (StringUtils.isEmpty(id)){
            return ResultUtils.failed("传入的id不能为空");
        }
        Category category = categoryService.getById(id);
        category.setValidFlag(ConstantUtils.NOTACTIVE);
        return categoryService.saveOrUpdate(category)?ResultUtils.success("删除类别成功"):ResultUtils.failed("删除失败");
    }

//    /**
//     * 查询供货商列表
//     * @param queryMap
//     * @return
//     */
//    @GetMapping("/querySupplierList")
//    public ResultVO querySupplierList(@RequestParam Map<String,Object> queryMap){
//        QueryWrapper<Supplier> queryWrapper = new QueryWrapper<>();
//        IPage<Supplier> supplierIPage = new Page<>( Long.valueOf((String) queryMap.get("page")),Long.valueOf((String) queryMap.get("size")));
//        if (!StringUtils.isEmpty(queryMap.get("supplierName"))){
//            //供货商名称
//            queryWrapper.like("supplier_name",queryMap.get("supplierName"));
//        }
//        supplierIPage = supplierService.page(supplierIPage,queryWrapper);
//        Map<String,Object> resultMap = new HashMap<>(8);
//        resultMap.put("rows",supplierIPage.getRecords());
//        resultMap.put("pages",supplierIPage.getPages());
//        resultMap.put("total",supplierIPage.getTotal());
//        return ResultUtils.success("查询成功",resultMap);
//    }

}
