package com.example.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.store.entity.User;
import com.example.store.service.UserService;
import com.example.store.util.ResultUtils;
import com.example.store.vo.ResultVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/2 16:35
 * @description
 **/
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResultVO save(User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",user.getUserName());
        User dbUser = userService.getOne(queryWrapper);
        if (dbUser != null){
            return ResultUtils.failed("该用户名已被使用");
        }
        return userService.saveOrUpdate(user)?ResultUtils.success("新增用户成功"):ResultUtils.failed("新增失败");
    }

    @RequestMapping("/")
    public String unLogin(){
        return "unLogin";
    }

    /**
     * 登录页面
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResultVO login(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
        try {
            subject.login(token);
            return ResultUtils.success("login");
        }catch (UnknownAccountException e){
            e.printStackTrace();
            return ResultUtils.failed("用户名不存在");
        }catch (DisabledAccountException e){
            e.printStackTrace();
            return ResultUtils.failed("该用户未激活");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            return ResultUtils.failed("密码错误");
        }
    }

}
