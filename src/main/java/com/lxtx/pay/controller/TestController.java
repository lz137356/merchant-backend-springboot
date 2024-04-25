package com.lxtx.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.service.StaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private StaticService staticService;


    //@RequestMapping("/statictics")
    public JSONObject statictics(){

        return staticService.statictics();
    }

}
