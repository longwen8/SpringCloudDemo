package com.longwen.feignconsumer;

import org.springframework.stereotype.Component;

@Component
public class HelloServiceFallback implements HelloService {


    @Override
    public String hello() {
        return "error";
    }

    @Override
    public String hello(User user) {
        return "error";
    }

    @Override
    public String hello(String name) {
        return "error";
    }

    @Override
    public User hello(String name, Integer age) {
        return new User("未知",0);
    }
}
