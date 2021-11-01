package com.xiaomo.cloud;

import com.xiaomo.common.auth.api.IAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;

@SpringBootTest
@ComponentScan(basePackages ={"com.xiaomo"})
public class ShCloudServerApplicationTests {

    @Resource
    private IAuthService authService;
    @Test
    void contextLoads() {
        String  token = authService.login("laoyu","123456");
        System.out.println("====="+token);
    }
}
