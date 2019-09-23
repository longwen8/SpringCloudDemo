package com.longwen.streambinder;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.MessageChannel;


@EnableBinding(value = {Sink.class,SinkSender.class})
public class SkinkReceiver {

    private static Logger logger = LoggerFactory.getLogger(StreamBinderApplication.class);

    @StreamListener(Sink.INPUT)
    public void receive(Object playload){
        logger.info("Received:" + playload);
    }

}
