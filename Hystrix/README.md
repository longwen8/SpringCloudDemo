# 服务容错保护 Spring Cloud Hystrix

在微服务架构中，多个服务之间相互调用，如果其中一个服务出现故障，很容易因依赖关系而导致故障蔓延，甚至存在整个系统挂断的可能性。因此为了避免出现此种情况，产生了类似于断路器等一系列的保护机制。

Hystrix 具备服务降级、服务熔断、线程和信号隔离、请求缓存、请求合并以及服务监控等强大功能。

## 运行说明
首先，启动Eureka服务器注册中心，访问 http://localhost:1111

      java -jar eureka-server-0.0.1-SNAPSHOT.jar

然后，启动两个注册服务提供者，此时查看Eureka注册中心，可以看到新增的两个服务
      
      java -jar eureka-client-0.0.1-SNAPSHOT.jar --server.port=8081
      java -jar eureka-client-0.0.1-SNAPSHOT.jar --server.port=8082

最后，启动一个服务消费者程序

      java -jar ribbon-consumer-0.0.1-SNAPSHOT.jar --server.port=9000


通过浏览器访问 http://localhost:9000/ribbon-consumer 进行调用。
通过反复刷新页面的方式调用服务，发现有时候会显示 error ，这个是因为服务端做了熔断措施，因为超时直接调用了失败处理方法。


## 代码变动说明
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
# Hystrix 仪表盘

Spring Cloud 完美地整合了它的仪表盘组件 Hystrix Dashboard,它主要用来实时监控 Hystrix的各项指标信息。

首先部署一个 Hystrix Dishboard 服务器，参考例子 hystrix-dashboard

启动命令：

       java -jar hystrix-dashboard-0.0.1-SNAPSHOT.jar --server.port=2001

访问页面 http://localhost:2001/hystrix 

如下图所示：

![image](https://github.com/longwen8/SpringCloudDemo/blob/master/Hystrix/images/hystrix.stream_data.png)


Hystrix Dashboard 支持三种监控方式，如下所示

- 单个应用监控：通过URL http://hystrix-app:port/hystrix.stream 开启。
- 默认集群监控：通过URL http://turbine-hostname:port/turbine.stream。
- 指定集群监控：通过URL http://turbine-hostname:port/turbine.stream?cluster=[clusterName]开启，实现对clusterName集群的监控。

其中 hystrix-app 表示一个hystrix的单独应用，turbine 表示的是一个集群监控名称。


### 单个应用监控

参考例子 ribbon-consumer。Spring Cloud 2.0 与之前的不通，需要通过actuator 访问，在application.properties 配置文件中配置 

    management.endpoints.web.exposure.include=hystrix.stream

并且在启动类里配置 @EnableCircuitBreaker

    @EnableCircuitBreaker
    @EnableDiscoveryClient
    @SpringBootApplication
    public class RibbonConsumerApplication {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(RibbonConsumerApplication.class, args);
	}

    }

然后可以通过访问 http://localhost:9000/actuator/hystrix.stream 会有数据产生。

如下图所示：

![image](https://github.com/longwen8/SpringCloudDemo/blob/master/Hystrix/images/hystrix_stream_dashboard.png)

**注意，如果只有ping 没有数据，需要访问一下接口服务。**
### 默认集群监控

集群监控需要使用Turbine 来汇集监控信息，在maven库中增加 turbine，参考例子 turbine.

    <dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-netflix-turbine</artifactId>
	</dependency>

启动 turbine 命令：
   
    java jar turbine-0.0.1-SNAPSHOT.jar --server.port=8989

访问数据页面 http://localhost:8989/turbine.stream 

如下图所示：

![image](https://github.com/longwen8/SpringCloudDemo/blob/master/Hystrix/images/turbine_stream_data.png)

访问监控页面 http://localhost:2001/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8989%2Fturbine.stream 

如下图所示：

![image](https://github.com/longwen8/SpringCloudDemo/blob/master/Hystrix/images/turbine_stream_dashboard.png)