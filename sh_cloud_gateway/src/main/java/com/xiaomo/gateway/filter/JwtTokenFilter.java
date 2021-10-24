package com.xiaomo.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomo.gateway.dto.IrmpResponse;
import com.xiaomo.gateway.jwt.JwtUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 描述: JwtToken 过滤器
 * @author xiaomo
 */
@Component
@ConfigurationProperties("org.my.jwt")
@Setter
@Getter
@Slf4j
public class JwtTokenFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);

    private String[] skipAuthUrls;

    private ObjectMapper objectMapper;

    public JwtTokenFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /**
         * 获取当前请求的url
         *  目标排除不需要验证的请求
         */
        String url = exchange.getRequest().getURI().getPath();
        //跳过不需要验证的路径
        if(null != skipAuthUrls&& Arrays.asList(skipAuthUrls).contains(url)){
            return chain.filter(exchange);
        }
        /**
         *
         */
        //获取token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        ServerHttpResponse resp = exchange.getResponse();
        if(StringUtils.isBlank(token)){
            return authErro(resp,"请登陆");
        }else{
            try {
                //JwtUtil.checkToken(token,objectMapper);
                return chain.filter(exchange);
            } catch (RuntimeException e) {
                LOGGER.error(e.getMessage(),e);
                if(e.getMessage().contains("Allowed clock skew")){
                    return authErro(resp,"认证过期");
                }else{
                    return authErro(resp,"认证失败");
                }
            }catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
                return authErro(resp,"认证失败");
            }
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
    /**
     * 认证错误输出
     * @param resp 响应对象
     * @param mess 错误信息
     * @return
     */
    private Mono<Void> authErro(ServerHttpResponse resp,String mess) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        IrmpResponse response = new IrmpResponse();
        response.setCode(org.apache.http.HttpStatus.SC_UNAUTHORIZED);
        response.setMessage(mess);
        response.setData(mess);
        String returnStr = "";
        try {
            returnStr = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(),e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }
}
