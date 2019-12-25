package com.example.store.controller;

import com.example.store.entity.User;
import com.example.store.util.ResultUtils;
import com.example.store.vo.ResultVO;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/2 16:35
 * @description
 **/
@Api(tags = "登录")
@RestController
@RequestMapping("/")
public class LoginController {

    @GetMapping("/")
    public ResultVO unLogin(){
        return ResultUtils.unauth("unLogin");
    }

    /**
     * 登录页面
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResultVO login(@RequestBody User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
        try {
            subject.login(token);
            return ResultUtils.success("login",subject.getPrincipal());
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
