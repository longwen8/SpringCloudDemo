## 消息总线 Spring Cloud Bus

消息总线，是微服务各个实例相互之间通信和广播的一个重要组件。我们使用一个轻量级的消息代理来构建一个共用的消息主题让系统中所有微服务实例都连接上来，由于该主题中产生的消息会被所有的实例监听和消费，所以我们称它为消息总线。


## 运行方式

### 消息实例说明
   
   rabbitmq-hello 工程中，主要接入了RabbitMQ组件，实现了最简单的定一个序列，然后向序列中插入一个记录，同时有一个监听程序，会对生产的数据进行消费。

   默认启动的服务是一个消费程序。测试方法中可以调用 http://localhost:8001/send 也可以 在IDE中调用测试类方法 HelloApplicationTests . 控制台会输出发送的消息和接收的消息。

     java -jar rabbitmq-hello-0.0.1-SNAPSHOT.jar


  
### 消息总线结合配置中心服务

   将消息总线与配置中心结合，即可实现通过发送总线消息来更新应用的配置。具体实现参考以下说明


   1.首先启动配置中心服务端。http://localhost:7001

   启动之前，先启动euraka服务中心

     java -jar config-server-eureka-0.0.1-SNAPSHOT.jar


   2.然后启动配置中心客户端，通过http://localhost:7002/from 可以查看配置文件信息。

     jar -jar config-client-eureka-0.0.1-SNAPSHOT.jar

  

   3.Git修改配置文件，访问连接查看

   通过修改 didispace-dev.properties文件中的内容，然后Git提交，访问http://localhost:7002/from 查看值，发现没有变化


   4.通过发送消息刷新，再次访问连接查看

   使用POSTMAN工具通过POST 访问http://localhost:7002/actuator/bus-refresh 进行同步。

   再次访问http://localhost:7002/from 查看配置信息，发现已经更新。



 注意：SpringBoot2.0 以后如果要调用 /bus/refresh需要在**服务端和客户端**配置

     management.endpoints.web.exposure.include= bus-refresh



     