package com.longwen.eurekaclient;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String index() throws Exception{
        int sleepTime = new Random().nextInt(3000);
        System.out.println("SleepTime:"+sleepTime);
        Thread.sleep(sleepTime);
        System.out.println("hello");
        return "Hello Wold";
    }
}
