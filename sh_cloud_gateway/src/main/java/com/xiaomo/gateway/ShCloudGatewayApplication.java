package com.xiaomo.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ShCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShCloudGatewayApplication.class, args);
    }

}
