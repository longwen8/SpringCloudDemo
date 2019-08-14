package com.longwen.eurekaclient;
import com.longwenapi.*;
import com.longwenapi.User;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huangxinping on 19/8/14.
 */
@RestController
public class RefactorHelloController implements HelloService{

    @Override
    public String hello(String s) {
        return "Refactor Hello " + s;
    }

    @Override
    public String hello(User user) {
        return "Refactor Hello "+user.getName() + "," + user.getAge();
    }

    @Override
    public User hello(String s, Integer integer) {
        return new User(s,integer);
    }
}
