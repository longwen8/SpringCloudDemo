##声明式服务调用： Spring Cloud Feign

微服务之间实质通过http协议进行相互之间的通信。Spring Cloud Ribbon 利用了RestTemplate对Http请求进行了封装。而Spring Cloud Feign则是在此基础上的更进一步封装。只需要我们使用接口定义并通过注释的方式，即可完成对服务提供方接口的绑定。简化了开发过程。

##运行说明

首先，启动Eureka服务注册中，访问http://localhost:1111,参考 Eureka目录中

    java -jar eureka-server-0.0.1-SNAPSHOT.jar

然后，本地maven构建 hello-service-api 生成jar包到本地maven库，其他项目引用。

    mvn install

然后，启动eureka-client，作为Feign调用的服务实现。

    java -jar eureka-client-0.0.1-SNAPSHOT.jar

最后，启动 Feign-consumer 通过声明式调用 eureka服务（eureka-client）

    java -jar feign-consumer-0.0.1-SNAPSHOT.jar

访问 http://localhost:9001/feign-consumer



##调用方式说明
### 1 接口定义和SpringMVC的注释来绑定
   参考 feign-consumer项目中的com.longwen.feignconsumer.HelloService实现类，近似复制。
### 2.通过继承特性来绑定。

定义一层中间层，作为一个公共的接口定义层，可以供多个服务使用。（参考hell-service-api接口定义）

在实现一方，直接继承接口，然后实现方法。(参考 eureka-client中 com.longwen.eurekaclient.RefactorHelloController 实现)

在调用一方，直接声明一个接口即可，然后结合 FeignClient注释  直接调用接口方法（参考feign-consumer中 com.longwen.feignconsumer.RefactorHelloService）。

注意：次方式测试需要，将@feignClient的值修改为: hell-service,同时将HelloService接口中定义的hello-service 替换成其他的，(重名会导致异常)



##Ribbon配置
在配置文件中配置全局的ribbon属性，例如 ribbon.ConnectTimeout=500等

在配置文件中配置某一个服务的ribbon属性，例如 HELLO-SERVICE.ribbon.ReadTimeout=2000

	HELLO-SERVICE.ribbon.ConnectTimeout=500   
	HELLO-SERVICE.ribbon.ReadTimeout=2000   访问超时时长
	HELLO-SERVICE.ribbon.OkToRetryOnAllOperations=true  定义是否可以重试操作
	HELLO-SERVICE.ribbon.MaxAutoRetries=2  定义重试次数
	HELLO-SERVICE.ribbon.MaxAutoRetriesNextServer=1  如果出错，更换实例的访问次数。

通过在服务类中增加随机时间休眠的方式，可以验证重试策略。


##Hystrix配置

Feign中设置可以使用hystrix

	feign.hystrix.enabled=true
 
可以定义一个服务降级的接口，例如feign-consumer中的 HelloServiceFallback实现类。

然后在Feign调用类中，增加 fallbak值，如下：
   
    @FeignClient(value = "hello-service",fallback = HelloServiceFallback.class)
参考 HelloService类



##日志输出配置 

配置文件中可以根据不同的服务定义不同的日志级别,例如：
  
    logging.level.com.longwen.feignconsumer.HelloService=DEBUG

日志级别4种：

 * NONE: 不做任何记录
 * BASIC: 仅记录请求方法，URL以及响应状态码和执行时间
 * HEADRE：除BASIC记录信息外，还包括请求和响应的头信息。
 * FULL：记录所有的请求和响应明细，包括头信息、请求体、元数据等。