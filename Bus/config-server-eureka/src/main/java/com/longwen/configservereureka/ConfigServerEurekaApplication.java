package com.longwen.configservereureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


@EnableConfigServer
@SpringBootApplication
public class ConfigServerEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerEurekaApplication.class, args);
	}

}
