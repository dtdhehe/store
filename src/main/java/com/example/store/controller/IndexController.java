package com.example.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 罗蕾
 * @version 1.0.0
 * @date 2019/11/20 16:59
 * @description
 **/
@Controller
@RequestMapping("")
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
