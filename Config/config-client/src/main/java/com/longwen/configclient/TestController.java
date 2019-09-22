package com.longwen.configclient;

import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class TestController {

    @Value("${from}")
    private String from;

    @Autowired
    private org.springframework.core.env.Environment env;


    //第一种获取配置方式
    @RequestMapping("/from")
    public String from(){
        return from;
    }
    //第二种获取配置方式
    @RequestMapping("from2")
    public String from2(){
        return env.getProperty("from","undefined");
    }
}
