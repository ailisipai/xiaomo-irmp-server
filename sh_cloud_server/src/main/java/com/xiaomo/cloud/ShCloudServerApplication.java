package com.xiaomo.cloud;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages ={"com.xiaomo"})
@EnableEurekaClient
@EnableSwagger2Doc
public class ShCloudServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShCloudServerApplication.class, args);
    }

}
