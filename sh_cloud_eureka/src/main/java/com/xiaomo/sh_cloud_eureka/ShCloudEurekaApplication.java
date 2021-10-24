package com.xiaomo.sh_cloud_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ShCloudEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShCloudEurekaApplication.class, args);
    }

}
