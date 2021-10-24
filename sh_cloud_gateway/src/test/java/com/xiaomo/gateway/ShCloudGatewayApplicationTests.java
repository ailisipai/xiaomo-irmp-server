package com.xiaomo.gateway;

import com.xiaomo.gateway.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static com.xiaomo.gateway.jwt.JwtUtil.parseJWT;

@SpringBootTest
class ShCloudGatewayApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShCloudGatewayApplicationTests.class);
    @Test
    void contextLoads() {
        try {
            String token = JwtUtil.createJWT("1","xiaomo","admin:xiaoxiao",70000L);
            LOGGER.info("通过工具生成token:"+token);
            Claims claims = JwtUtil.parseJWT(token);
            LOGGER.info("通过工具解析token:"+claims);
            LOGGER.info("通过工具解析token:"+claims.getSubject());
            LOGGER.info("通过工具解析token:"+claims.getIssuer());
            LOGGER.info("通过工具解析token:"+claims.getId());
            LOGGER.info("通过工具解析token:"+claims.getIssuedAt());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
