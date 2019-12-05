package com.example.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.store.entity.User;
import com.example.store.service.UserService;
import com.example.store.util.ConstantUtils;
import com.example.store.util.ResultUtils;
import com.example.store.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

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
            User dbUser = userService.getOne(queryWrapper);
            if (dbUser != null){
                return ResultUtils.failed("该用户名已被使用");
            }
            user.setStatus(ConstantUtils.ACTIVE);
        }else if (ConstantUtils.CUSTOMER.equals(user.getUserType())){
            //新增会员
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

}
