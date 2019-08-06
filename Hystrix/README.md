# 服务容错保护 Spring Cloud Hystrix

在微服务架构中，多个服务之间相互调用，如果其中一个服务出现故障，很容易因依赖关系而导致故障蔓延，甚至存在整个系统挂断的可能性。因此为了避免出现此种情况，产生了类似于断路器等一系列的保护机制。

Hystrix 具备服务降级、服务熔断、线程和信号隔离、请求缓存、请求合并以及服务监控等强大功能。

##运行说明
首先，启动Eureka服务器注册中心，访问 http://localhost:1111

      java -jar eureka-server-0.0.1-SNAPSHOT.jar

然后，启动两个注册服务提供者，此时查看Eureka注册中心，可以看到新增的两个服务
      
      java -jar eureka-client-0.0.1-SNAPSHOT.jar --server.port=8081
      java -jar eureka-client-0.0.1-SNAPSHOT.jar --server.port=8082

最后，启动一个服务消费者程序

      java -jar ribbon-consumer-0.0.1-SNAPSHOT.jar --server.port=9000


通过浏览器访问 http://localhost:9000/ribbon-consumer 进行调用。
通过反复刷新页面的方式调用服务，发现有时候会显示 error ，这个是因为服务端做了熔断措施，因为超时直接调用了失败处理方法。


##代码变动说明
相对于Eureka的代码，本实例修改了Eureka客户端和服务消费端程序。

- Eureka客户端即服务提供程序，eureka-client 中增加了程序休眠，休眠时长为随机3000毫秒，默认2000毫秒会超时，因此当随机数字大于2000 则会调用失败处理方法。

代码如下：

    @RequestMapping("/hello")
    public String index() throws Exception{
        int sleepTime = new Random().nextInt(3000);
        System.out.println("SleepTime:"+sleepTime);
        Thread.sleep(sleepTime);
        System.out.println("hello");
        return "Hello Wold";
    }

- 服务消费端程序，增加HelloService代码

代码如下：

    @Service
    public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloFallback")
    public String helloService(){
        return restTemplate.getForEntity("http://HELLO-SERVICE/hello",String.class).getBody();

    }

    public String helloFallback(){
        return "error";
    }
}
  