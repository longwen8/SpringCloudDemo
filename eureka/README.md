# Spring Eureka Demo

Eureka作为Spring Cloud 微服务体系中重要的一部分，主要负责服务治理功能。

##服务治理
服务治理是微服务体系中最核心和最基础的模块，主要实现各个微服务实例的自动化注册和发现。


- 服务注册：在服务治理框架中，通常都有一个服务注册中心，每个服务单元向注册中心登记自己要提供的服务。


- 服务发现：服务之间调用，先通过注册中心咨询服务请求信息，然后注册中心返回具体的服务所在信息，进而调用服务。


## 运行说明
- 首先，启动Eureka服务器注册中心，访问 http://localhost:1111
   
    
        
        java -jar eureka-server-0.0.1-SNAPSHOT.jar

- 然后，启动两个注册服务提供者，此时查看Eureka注册中心，可以看到新增的两个服务

        java -jar eureka-client-0.0.1-SNAPSHOT.jar --server.port=8081
        java -jar eureka-client-0.0.1-SNAPSHOT.jar --server.port=8082

- 最后，启动一个服务消费者程序，通过浏览器访问 http://localhost:9000/ribbon-consumer 进行调用
        
        java -jar ribbon-consumer-0.0.1-SNAPSHOT.jar --server.port=9000       
   


