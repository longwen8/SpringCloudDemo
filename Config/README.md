## 分布式配置中心  Spring cloud Config

 Spring Cloud Config 用来为分布式系统中基础设施和微服务应用提供集中化的外部配置服务支持。可以使用连接仓库（Git/SVN）或者本地文件的方式，进行微服务相关的配置文件管理。


## 运行方式

### 启动分布式配置中心服务

   启动服务，访问http://localhost:7001/didispace/dev 表示访问配置中的 didispace-dev.properties文件。

     java -jar config-server-0.0.1-SNAPSHOT.jar

   
### 启动客服端程序

   启动客户端，访问http://localhost:7002/from和http://localhost:7002/from2 

   访问服务内容，获取配置中心服务的didispace-dev.properties文件from属性值。

     java -jar config-client-0.0.1-SNAPSHOT.jar


## 配置简介
在配置中心服务项目中配置仓库的连接信息，和访问路径，用户名和密码，例如：
		
    spring.cloud.config.server.git.uri=https://github.com/longwen8/SpringCloudDemo/
	spring.cloud.config.server.git.search-paths=Config/config-repo
	spring.cloud.config.server.git.username=longwen8
	spring.cloud.config.server.git.password=password #此处应为访问密码


在客户端创建bootstarp.properties文件，配置需要访问服务的配置，例如：

    spring.application.name=didispace
	spring.cloud.config.profile=dev
	spring.cloud.config.label=master
	spring.cloud.config.uri=http://localhost:7001/



## 其他内容

除此之外，配置内容可以使用通配符的方式。

SVN配置，本地文件配置。

安全保护（通过配置用户名和密码，加密方式）

将配置中心结合Eruka，可以作为一个服务使用，并可以升级多个节点。

客户端可以使用 actuator监控模块，对服务中心的设置变化进行监控，通过refresh访问，刷新配置数据。


