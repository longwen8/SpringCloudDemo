package com.longwen.streambinder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableBinding(value = {SinkSender.class})
@SpringBootTest(classes= StreamBinderApplication.class)
public class HelloApplicationTest2 {


    @Autowired
    private MessageChannel input;


    @Test
    public void contextLoads(){
        input.send(MessageBuilder.withPayload("From MessageChennel").build());
        System.out.println("send over");
    }

}
