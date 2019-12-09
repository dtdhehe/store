package com.example.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.store.entity.Supplier;
import com.example.store.entity.User;
import com.example.store.service.UserService;
import com.example.store.util.ConstantUtils;
import com.example.store.util.ResultUtils;
import com.example.store.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/1 23:29
 * @description
 **/
@Api(tags = "用户")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("/addUser")
    public ResultVO addUser(@RequestBody User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (ConstantUtils.SALES.equals(user.getUserType())){
            //新增店员
            queryWrapper.eq("user_name",user.getUserName());
            queryWrapper.eq("valid_flag",ConstantUtils.ACTIVE);
            User dbUser = userService.getOne(queryWrapper);
            if (dbUser != null){
                return ResultUtils.failed("该用户名已被使用");
            }
            //密码加密
            user.setPassword(ConstantUtils.getPassword(user.getPassword(),user.getUserName()));
            user.setStatus(ConstantUtils.ACTIVE);
        }else if (ConstantUtils.CUSTOMER.equals(user.getUserType())){
            //新增会员
            queryWrapper.eq("phone",user.getPhone());
            queryWrapper.eq("valid_flag",ConstantUtils.ACTIVE);
            User dbUser = userService.getOne(queryWrapper);
            if (dbUser != null){
                return ResultUtils.failed("该会员已添加过，请勿重复添加");
            }
            //默认购物份数
            user.setShoppingPoints(BigDecimal.TEN);
            user.setStatus(ConstantUtils.ACTIVE);
        }
        return userService.saveOrUpdate(user)?ResultUtils.success("新增用户成功"):ResultUtils.failed("新增失败");
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PutMapping("/updateUser/{id}")
    public ResultVO updateUser(@RequestBody User user,@PathVariable String id){
        if (StringUtils.isEmpty(id)){
            return ResultUtils.failed("传入的id不能为空");
        }
        if (!StringUtils.isEmpty(user.getUserName())){
            //密码加密
            user.setPassword(ConstantUtils.getPassword(user.getPassword(),user.getUserName()));
        }
        return userService.saveOrUpdate(user)?ResultUtils.success("修改用户成功"):ResultUtils.failed("修改失败");
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/deleteUser/{id}")
    public ResultVO deleteUser(@PathVariable String id){
        if (StringUtils.isEmpty(id)){
            return ResultUtils.failed("传入的id不能为空");
        }
        User user = userService.getById(id);
        user.setValidFlag(ConstantUtils.NOTACTIVE);
        return userService.saveOrUpdate(user)?ResultUtils.success("删除用户成功"):ResultUtils.failed("删除失败");
    }

    /**
     * 根据id获得用户
     * @param id
     * @return
     */
    @GetMapping("/getUser/{id}")
    public ResultVO getUser(@PathVariable("id") String id){
        if (StringUtils.isEmpty(id)){
            return ResultUtils.failed("传入的id不能为空");
        }
        User user = userService.getById(id);
        return ResultUtils.success("查询成功",user);
    }

    /**
     * 查询店员列表
     * @param queryMap
     * @return
     */
    @GetMapping("/querySalesList")
    public ResultVO querySalesList(@RequestParam Map<String,Object> queryMap){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("valid_flag",ConstantUtils.ACTIVE);
        queryWrapper.eq("user_type",ConstantUtils.SALES);
        queryWrapper.orderByDesc("create_time");
        IPage<User> iPage = new Page<>( Long.valueOf((String) queryMap.get("page")),Long.valueOf((String) queryMap.get("size")));
        if (!StringUtils.isEmpty(queryMap.get("name"))){
            //店员名字
            queryWrapper.like("name",queryMap.get("name"));
        }
        iPage = userService.page(iPage,queryWrapper);
        Map<String,Object> resultMap = new HashMap<>(8);
        resultMap.put("rows",iPage.getRecords());
        resultMap.put("pages",iPage.getPages());
        resultMap.put("total",iPage.getTotal());
        return ResultUtils.success("查询成功",resultMap);
    }

    /**
     * 查询会员列表
     * @param queryMap
     * @return
     */
    @GetMapping("/queryCustomerList")
    public ResultVO queryCustomerList(@RequestParam Map<String,Object> queryMap){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("valid_flag",ConstantUtils.ACTIVE);
        queryWrapper.eq("user_type",ConstantUtils.CUSTOMER);
        queryWrapper.orderByDesc("create_time");
        IPage<User> iPage = new Page<>( Long.valueOf((String) queryMap.get("page")),Long.valueOf((String) queryMap.get("size")));
        if (!StringUtils.isEmpty(queryMap.get("name"))){
            //会员名字
            queryWrapper.like("name",queryMap.get("name"));
        }
        iPage = userService.queryCustomerList(iPage,queryWrapper);
        Map<String,Object> resultMap = new HashMap<>(8);
        resultMap.put("rows",iPage.getRecords());
        resultMap.put("pages",iPage.getPages());
        resultMap.put("total",iPage.getTotal());
        return ResultUtils.success("查询成功",resultMap);
    }

}
