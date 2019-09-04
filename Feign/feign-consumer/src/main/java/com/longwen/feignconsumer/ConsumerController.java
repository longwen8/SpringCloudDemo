package com.longwen.feignconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

@RestController
public class ConsumerController {

    @Resource
    HelloService helloService;

    @Autowired
    RefactorHelloService refactorHelloService;

    @RequestMapping(value = "/feign-consumer",method = RequestMethod.GET)
    public String helloConsumer() throws InterruptedException {

        return helloService.hello();
    }

    @RequestMapping(value = "/feign-consumer2",method = RequestMethod.GET)
    public String helloConsumer2(){
        StringBuffer sb = new StringBuffer();
        sb.append("hello:").append(helloService.hello()).append("\n");
        sb.append("hello1:").append(helloService.hello("longwen")).append("\n");
        sb.append("hello2:").append(helloService.hello("longwen",25)).append("\n");
        sb.append("hello3:").append(helloService.hello(new User("longwen",30))).append("\n");

        return sb.toString();
    }

    @RequestMapping(value = "/feign-consumer3",method = RequestMethod.GET)
    public String helloConsumer3(){
        StringBuffer sb = new StringBuffer();
        sb.append("hello1:").append(refactorHelloService.hello("r_longwen")).append("\n");
        sb.append("hello2:").append(refactorHelloService.hello("r_longwen",25)).append("\n");
        sb.append("hello3:").append(refactorHelloService.hello(new com.longwenapi.User("r_longwen",30)));
        return sb.toString();


    }



}
