package com.example.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
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


    @RequestMapping("/test")
    public String test(){
        return "test";
    }

}
