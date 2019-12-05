package com.example.store.controller;

import com.example.store.util.ResultUtils;
import com.example.store.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Wrapper;
import java.util.List;

/**
 * @author Xie_东
 * @version 1.0.0
 * @date 2019/12/1 23:29
 * @description
 **/
@RestController("/")
public class TestController {


    @GetMapping("/test")
    public ResultVO test(){
        return ResultUtils.success("test");
    }

}
