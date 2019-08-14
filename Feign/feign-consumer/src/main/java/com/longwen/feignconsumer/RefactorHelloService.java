package com.longwen.feignconsumer;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by huangxinping on 19/8/15.
 */
@FeignClient(value = "hello-service")
public interface RefactorHelloService extends com.longwenapi.HelloService {


}
