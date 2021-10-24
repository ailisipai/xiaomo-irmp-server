package com.xiaomo.wpp.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaomo.wpp.model.entity.Permission;
import com.xiaomo.wpp.model.entity.SysRoleInfo;
import com.xiaomo.wpp.model.entity.SysUserInfo;
import com.xiaomo.wpp.service.ISysUserInfoService;
import com.xiaomo.wpp.vo.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 * @Api value    字符串	可用在class头上,class描述
 *  	description	字符串
 *                        @Api(value = "xxx", description = "xxx")
 * @ApiOpration	value	字符串	可用在方法头上.参数的描述容器
 *  	        notes	字符串
 *                      @ApiOperation(value = "xxx", notes = "xxx")
 * @ApiImplici    Params	{}	@ApiImplicitParam数组	可用在方法头上.参数的描述容器
 *            @ApiImplicitParams({@ApiImplicitParam1,@ApiImplicitParam2,...})
 * @ApiImplic tPar    m	name	字符串 与参数命名对应	可用在@ApiImplicitParams里
 *  	value	字符串	参数中文描述
 *  	required	布尔值	true/false
 *  	dataType	字符串	参数类型
 *  	paramType	字符串	参数请求方式:query/path
 *  	 	 	query:对应@RequestParam?传递
 *  	 	 	path: 对应@PathVariable{}path传递
 *  	defaultValue	字符串	在api测试中默认值
 *  	 	 	用例参见项目中的设置
 * @ApiRe    ponses	{}	@ApiResponse数组	可用在方法头上.参数的描述容器
 *            @ApiResponses({@ApiResponse1,@ApiResponse2,...})
 * @ApiR spon    e	code	整形	可用在@ApiResponses里
 *  	message	字符串	错误描述
 *            @ApiResponse(code = 200, message = "Successful")
 *
 * @author xiaomo
 * @since 2021-10-10
 */
@RestController
//@RequestMapping("/sys-user-info")
@Api(value = "系統模块" ,tags={"系統模块"})
public class SysUserInfoController {

    @Autowired
    private ISysUserInfoService sysUserInfoService;

    /**
     * @param userId
     * @return
     */
    @ApiOperation("查询系統用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value = "用户id",required = true,defaultValue = "2"),
            @ApiImplicitParam(name="username",value = "用户名",required = false,defaultValue = "xiaomo"),
            @ApiImplicitParam(name="address",value = "地址",required = false,defaultValue = "上海")
    })
    @RequestMapping(value = "/getUserInfo" ,method = RequestMethod.GET)
    public SysUserInfo getUserInfo(Long userId,String username , String address){
        return sysUserInfoService.getUserInfo(userId);
    }
    @ApiOperation("获取当前用户信息")
    @RequestMapping(value = "/user/info" ,method = RequestMethod.GET)
    public ApiResponse getUserInfo(){
        ApiResponse apiResponse = new ApiResponse();
        //获取用户信息
        SysUserInfo userInfo = new SysUserInfo();
        userInfo.setName("xiaomo");
        userInfo.setUserName("admin");
        userInfo.setRoleId("admin");
        SysRoleInfo sysRoleInfo = new SysRoleInfo();
        sysRoleInfo.setName("admin");
        sysRoleInfo.setDescribe("超级管理员");
        List<Permission> permissions = changPermission();
        sysRoleInfo.setPermissions(permissions);
        userInfo.setRole(sysRoleInfo);
        //获取角色信息
        //apiResponse.setResult();// 保存用户信息
        apiResponse.setResult(userInfo);
        return apiResponse;
    }
    @ApiOperation("加载权限菜单")
    @RequestMapping(value = "/user/nav" ,method = RequestMethod.GET)
    public ApiResponse getMenu(){
        ApiResponse apiResponse = new ApiResponse();
        JSONArray array = changMenu();
        apiResponse.setResult(array);
        return apiResponse;
    }

    public JSONArray changMenu(){
        JSONArray array = new JSONArray();
        /**
         * 第一个菜单001
         */
        JSONObject obj = new JSONObject();
        obj.put("name","dashboard");
        obj.put("parentId",0);
        obj.put("id",1);
        obj.put("title","仪表盘");
        JSONObject meta = new JSONObject();
        meta.put("icon","dashboard");
        meta.put("title","仪表盘");
        meta.put("show",true);
        obj.put("meta",meta);
        obj.put("name","dashboard");
        obj.put("component","RouteView");
        obj.put("redirect","/dashboard/workplace");
        array.add(obj);

        /**
         * 第一个菜单002
         */
        JSONObject obj2 = new JSONObject();
        obj2.put("name","workplace");
        obj2.put("parentId",1);
        obj2.put("id",1117);
        obj2.put("title","工作台");
        JSONObject meta2 = new JSONObject();
        meta2.put("title","工作台");
        meta2.put("show",true);
        obj2.put("meta",meta2);
        obj2.put("name","Workplace");
        obj.put("component","Workplace");
        array.add(obj2);

        /**
         * 第一个菜单001
         */
        JSONObject obj4 = new JSONObject();
        obj4.put("name","Analysis");
        obj4.put("parentId",1);
        obj4.put("id",2);
        obj4.put("title","分析页");
        JSONObject meta4 = new JSONObject();
        meta4.put("icon","dashboard");
        meta4.put("title","分析页");
        meta4.put("show",true);
        obj4.put("meta",meta4);
        obj.put("component","Analysis");
        obj4.put("path","/dashboard/analysis");
        array.add(obj4);

       return array;
    }
    /**
     * 加载权限
     * @return
     */
    public List<Permission> changPermission(){
        List<Permission> permissions = new ArrayList<>();

        Permission permission = new Permission();
        permission.setRoleId("12");
        permission.setPermissionId("dashboard");
        permission.setPermissionName("仪表盘");
        permission.setActions("[{'action':'add','defaultCheck':false,'describe':'新增'},{'action':'query','defaultCheck':false,'describe':'查询'},{'action':'get','defaultCheck':false,'describe':'详情'},{'action':'update','defaultCheck':false,'describe':'修改'},{'action':'delete','defaultCheck':false,'describe':'删除'}]");
        List<Object> list = new ArrayList();
        Map<String ,Object> map1 = new HashMap();
        map1.put("action","add");
        map1.put("describe","新增");
        map1.put("defaultCheck",false);

        Map<String ,Object> map2 = new HashMap();
        map2.put("action","query");
        map2.put("describe","查詢");
        map2.put("defaultCheck",false);

        Map<String ,Object> map3 = new HashMap();
        map3.put("action","get");
        map3.put("describe","详情");
        map3.put("defaultCheck",false);

        Map<String ,Object> map4 = new HashMap();
        map4.put("action","update");
        map4.put("describe","修改");
        map4.put("defaultCheck",false);

        Map<String ,Object> map5 = new HashMap();
        map5.put("action","delete");
        map5.put("describe","删除");
        map5.put("defaultCheck",false);

        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        permission.setActionEntitySet(list);
        permission.setActionList(null);
        permission.setDataAccess(null);
        permissions.add(permission);
        return permissions;
    }
    /**
     * 加载动态路由
     */
    public  ApiResponse generatorDynamicRouter(){
        ApiResponse apiResponse = new ApiResponse();
        return apiResponse;
    }
}
