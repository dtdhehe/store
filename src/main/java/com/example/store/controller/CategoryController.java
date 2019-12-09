package com.example.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.store.entity.Category;
import com.example.store.service.CategoryService;
import com.example.store.util.ConstantUtils;
import com.example.store.util.ResultUtils;
import com.example.store.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author 罗蕾
 * @version 1.0
 * @date 2019/11/4 22:03
 * @description
 **/
@Api(tags = "商品类别")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增类别
     * @param category
     * @return
     */
    @PostMapping("/addCategory")
    public ResultVO addCategory(@RequestBody Category category){
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("valid_flag",ConstantUtils.ACTIVE);
        if (!StringUtils.isEmpty(category.getCategoryPcode())){
            //父编码不为空，则为新增二级类别
            wrapper.eq("category_pcode",category.getCategoryPcode());
            wrapper.orderByDesc("category_code");
            List<Category> supplierList = categoryService.list(wrapper);
            //当前类别编码
            category.setCategoryCode(category.getCategoryPcode()+"01");
            if (supplierList.size() != 0){
                //二级类别取编码后两位计算
                String currCode = (Integer.valueOf(supplierList.get(0).getCategoryCode().substring(2)) + 1) + "";
                if (currCode.length() == 1){
                    currCode = "0"+currCode;
                }
                category.setCategoryCode(category.getCategoryPcode()+currCode);
            }
        }else{
            //父编码为空，新增一级类别
            wrapper.isNull("category_pcode");
            wrapper.orderByDesc("category_code");
            List<Category> supplierList = categoryService.list(wrapper);
            //当前类别编码
            //第一个类别
            category.setCategoryCode("01");
            if (supplierList.size() != 0){
                String currCode = (Integer.valueOf(supplierList.get(0).getCategoryCode()) + 1) + "";
                if (currCode.length() == 1){
                    currCode = "0"+currCode;
                }
                category.setCategoryCode(currCode);
            }
        }
        return categoryService.save(category)?ResultUtils.success("新增类别成功",category.getCategoryCode()):ResultUtils.failed("新增失败");
    }

    /**
     * 更新类别
     * @param category
     * @return
     */
    @PutMapping("/updateCategory/{id}")
    public ResultVO updateCategory(@RequestBody Category category){
        if (StringUtils.isEmpty(category.getId())){
            return ResultUtils.failed("传入的id不能为空");
        }
        return categoryService.saveOrUpdate(category)?ResultUtils.success("更新类别成功"):ResultUtils.failed("更新失败");
    }

    /**
     * 删除类别
     * @param id
     * @return
     */
    @DeleteMapping("/deleteCategory/{id}")
    public ResultVO deleteCategory(@PathVariable String id){
        if (StringUtils.isEmpty(id)){
            return ResultUtils.failed("传入的id不能为空");
        }
        Category category = categoryService.getById(id);
        category.setValidFlag(ConstantUtils.NOTACTIVE);
        return categoryService.saveOrUpdate(category)?ResultUtils.success("删除类别成功"):ResultUtils.failed("删除失败");
    }

    /**
     * 查询供货商列表
     * @param queryMap
     * @return
     */
    @GetMapping("/queryCategoryList")
    public ResultVO queryCategoryList(@RequestParam Map<String,Object> queryMap){
        List<Map<String,Object>> resultList = new ArrayList<>();
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("valid_flag",ConstantUtils.ACTIVE);
        queryWrapper.orderByAsc("category_code");
        List<Category> categoryList = categoryService.list(queryWrapper);
        for (Category item : categoryList){
            if (item.getCategoryCode().length() > 2){
                //二级类别
                Optional<Map<String, Object>> optional = resultList.stream().filter(e -> e.get("categoryCode").equals(item.getCategoryPcode())).findFirst();
                if (optional.isPresent()){
                    Map<String, Object> parentMap = optional.get();
                    Map<String,Object> map = new HashMap<>(8);
                    map.put("id",item.getId());
                    map.put("categoryName",item.getCategoryName());
                    map.put("categoryDesc",item.getCategoryDesc());
                    map.put("categoryCode",item.getCategoryCode());
                    map.put("categoryPcode",item.getCategoryPcode());
                    ((List)parentMap.get("children")).add(map);
                }
            }else {
                //一级类别
                Map<String,Object> map = new HashMap<>(8);
                map.put("id",item.getId());
                map.put("categoryName",item.getCategoryName());
                map.put("categoryDesc",item.getCategoryDesc());
                map.put("categoryCode",item.getCategoryCode());
                List<Map<String,Object>> childrenList = new ArrayList<>();
                map.put("children",childrenList);
                resultList.add(map);
            }
        }
        return ResultUtils.success("查询成功",resultList);
    }

}
