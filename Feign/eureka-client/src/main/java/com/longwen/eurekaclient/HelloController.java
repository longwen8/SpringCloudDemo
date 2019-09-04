package com.longwen.eurekaclient;

import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String index() throws InterruptedException {
        System.out.println("hello");
        int sleepTime = new Random().nextInt(3000);
        System.out.println("sleepTime:"+sleepTime);
        Thread.sleep(sleepTime);
        return "Hello Wold";
    }

    @RequestMapping(value = "/hello1",method = RequestMethod.GET)
    public String hello(@RequestParam String name) {

        return "Hello "+ name;
    }


    @RequestMapping(value = "/hello2",method = RequestMethod.GET)
    public User hello(@RequestHeader String name,@RequestHeader Integer age){
        return new User(name,age);
    }


    @RequestMapping(value = "/hello3",method = RequestMethod.POST)
    public String hello(@RequestBody User user){
        return user.toString();
    }


}
