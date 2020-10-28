package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages={"com"})
public class SpringCloudAlibabaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudAlibabaConsumerApplication.class, args);
	}

}
