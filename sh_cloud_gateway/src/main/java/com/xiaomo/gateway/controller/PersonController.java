package com.xiaomo.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomo.gateway.annotation.JWTCheck;
import com.xiaomo.gateway.dto.IrmpResponse;
import com.xiaomo.gateway.dto.UserDTO;
import com.xiaomo.gateway.jwt.JwtModel;
import com.xiaomo.gateway.jwt.JwtUtil;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * @author LWong
 * @date 2019/12/24/024
 */
@RestController
public class PersonController {
    private ObjectMapper objectMapper;

   /* @Value("${org.my.jwt.effective-time}")
    private String effectiveTime;

    public PersonController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    *//**
     * 登陆认证接口
     *
     * @param userDTO
     * @return
     *//*
    @PostMapping("/auth/login")
    public IrmpResponse<String> login(@RequestBody UserDTO userDTO) throws Exception {
        ArrayList<String> roleIdList = new ArrayList<>(1);
        roleIdList.add("role_test_1");
        JwtModel jwtModel = new JwtModel("test", roleIdList);
        int effectivTimeInt = Integer.valueOf(effectiveTime.substring(0, effectiveTime.length() - 1));
        String effectivTimeUnit = effectiveTime.substring(effectiveTime.length() - 1, effectiveTime.length());
        String jwt = null;
        switch (effectivTimeUnit) {
            case "s": {
                //秒
                jwt = JwtUtil.createJWT("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 1000L);
                break;
            }
            case "m": {
                //分钟
                jwt = JwtUtil.createJWT("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 60L * 1000L);
                break;
            }
            case "h": {
                //小时
                jwt = JwtUtil.createJWT("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 60L * 60L * 1000L);
                break;
            }
            case "d": {
                //小时
                jwt = JwtUtil.createJWT("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 24L * 60L * 60L * 1000L);
                break;
            }
        }
        IrmpResponse rep = new IrmpResponse();
        rep.setCode(HttpStatus.SC_OK);
        rep.setMessage("认证成功");
        rep.setData(jwt);
        return rep;
    }

    *//**
     * 为授权提示
     *//*
    @GetMapping("/unorized")
    @JWTCheck
    public IrmpResponse<String> unauthorized(@RequestHeader("Authorization") String token) {
        IrmpResponse rep = new IrmpResponse();
        rep.setCode(HttpStatus.SC_UNAUTHORIZED);
        rep.setMessage("未认证,请重新登陆");
        rep.setData(null);
        return rep;
    }

    *//**
     * jwt 检查注解测试 测试
     *
     * @return
     *//*
    @GetMapping("/testJwtCheck")
    @JWTCheck
    public IrmpResponse<String> testJwtCheck(@RequestHeader("Authorization") String token, @RequestParam("name") @Valid String name) {

        IrmpResponse rep = new IrmpResponse();
        rep.setCode(HttpStatus.SC_OK);
        rep.setMessage("请求成功咯-请求成功咯");
        rep.setData(name);
        return rep;

    }*/

}
