package com.xiaomo.wpp.controller;

import com.xiaomo.wpp.vo.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Api(value = "用户模块" ,tags={"用户模块"})
public class UserController {

    /**
     * 根据用户id查询用户信息
     * @param userid
     * @return
     */
    @ApiOperation("查询用户信息")
    @RequestMapping(value = "/admin/getUserInfo/{userid}" ,method = RequestMethod.GET)
    public Map<String, String> getUserInfo(String userid){
        Map<String ,String> map = new HashMap();
        map.put("name","xiaomo");
        return map;
    }
    @ApiOperation("查询用户信息")
    @RequestMapping(value = "/user/info2" ,method = RequestMethod.GET)
    public Map<String, String> userinfo(){
        Map<String ,String> map = new HashMap();
        map.put("name","xiaomo");
        return map;
    }
    @ApiOperation("查询用户信息")
    @RequestMapping(value = "/auth/login" ,method = RequestMethod.POST)
    public ApiResponse userLogin(@RequestBody Map<String , Object> map){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(200);
        Map<String , String> resmap = new HashMap();
        resmap.put("id","1");
        resmap.put("name","xiaomo");
        resmap.put("username","admin");
        resmap.put("avatar","https://gw.alipayobjects.com/zos/rmsportal/jZUIxmJycoymBprLOUbT.png");
        resmap.put("token","4291d7da9005377ec9aec4a71ea837f");
        apiResponse.setData(resmap);
        apiResponse.setResult(resmap);
        return apiResponse;
    }
    @RequestMapping(value = "/auth/2step-code",method = RequestMethod.POST)
    public ApiResponse login(@RequestBody Map<String,Object> map){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(200);
        Map<String , String> mapre = new HashMap();
        mapre.put("stepCode","1");
        apiResponse.setData(mapre);
        return apiResponse;
    }
    @RequestMapping(value = "auth/logout",method = RequestMethod.POST)
    public ApiResponse loginOut(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("[测试接口] 注销成功");
        return apiResponse;
    }

}
