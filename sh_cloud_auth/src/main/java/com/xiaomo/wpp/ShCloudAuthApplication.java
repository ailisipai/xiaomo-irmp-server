package com.xiaomo.wpp;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableSwagger2Doc
public class ShCloudAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShCloudAuthApplication.class, args);
    }

}
