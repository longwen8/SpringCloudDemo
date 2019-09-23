package com.longwen.rabbitmqhello;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {


    @Autowired
    private Sender sender;



    //第一种获取配置方式
    @RequestMapping("/send")
    public String from(){
        sender.send();
        return "OK";
    }


}
