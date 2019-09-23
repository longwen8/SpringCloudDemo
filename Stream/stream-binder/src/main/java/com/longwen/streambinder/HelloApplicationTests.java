package com.longwen.streambinder;

import org.junit.Test;
import org.junit.runner.RunWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= StreamBinderApplication.class)
public class HelloApplicationTests {

    @Autowired
    private SinkSender sender;


    @Test
    public void contextLoads(){


        sender.output().send(MessageBuilder.withPayload("From SinkSender").build());
    }
}
