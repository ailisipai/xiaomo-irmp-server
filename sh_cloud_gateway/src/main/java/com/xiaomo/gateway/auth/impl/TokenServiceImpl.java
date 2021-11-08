package com.xiaomo.gateway.auth.impl;

import cn.hutool.core.util.ObjectUtil;
import com.xiaomo.common.cache.UserCache;
import com.xiaomo.common.consts.CommonConstant;
import com.xiaomo.common.exception.AuthException;
import com.xiaomo.common.exception.enums.AuthExceptionEnum;
import com.xiaomo.common.jwtutil.JwtPayLoad;
import com.xiaomo.common.jwtutil.JwtTokenUtil;
import com.xiaomo.common.pojo.login.SysLoginUser;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * token相关service实现类
 * @author xiaomo
 *
 */
@Service
public class TokenServiceImpl {

    @Resource
    private UserCache userCache;

    /**
     * 从请求中获取token
     */
    public String getTokenFromRequest(HttpServletRequest request){
        // 获取请求头中的token
        String authToken = request.getHeader(CommonConstant.AUTHORIZATION);
        if(ObjectUtil.isEmpty(authToken)){
            return null;
        }
        //判断token格式
        if(!authToken.startsWith(CommonConstant.TOKEN_TYPE_BEARER)){
            throw new AuthException(AuthExceptionEnum.NOT_VALID_TOKEN_TYPE);
        }
        try {
            authToken = authToken.substring(CommonConstant.TOKEN_TYPE_BEARER.length() + 1);
        } catch (StringIndexOutOfBoundsException e){
            throw new AuthException(AuthExceptionEnum.NOT_VALID_TOKEN_TYPE);
        }
        return authToken;
    }

    /**
     * 根据请求token获取登录用户信息
     * @param token
     * @return
     */
    public SysLoginUser getLoginUserByToken(String token){
        /**
         * 1、token检查
         * 2、根据token获取JwtPayLoad部分
         * 3、从redis缓存中获取登录用户
         * 4、用户不存在则表示登录已过期
         * 5、转换成登录用户
         * 6、用户存在, 无痛刷新缓存，在登录过期前活动的用户自动刷新缓存时间
         */
        this.checkToken(token);
        JwtPayLoad jwtPayLoad = JwtTokenUtil.getJwtPayLoad(token);
        Object cacheObject = userCache.get(jwtPayLoad.getUuid());
        //用户不存在则表示登录已过期
        if (ObjectUtil.isEmpty(cacheObject)) {
            throw new AuthException(AuthExceptionEnum.LOGIN_EXPIRED);
        }
        //转换成登录用户
        SysLoginUser sysLoginUser = (SysLoginUser) cacheObject;
        //用户存在, 无痛刷新缓存，在登录过期前活动的用户自动刷新缓存时间
        this.cacheLoginUser(jwtPayLoad, sysLoginUser);
        //返回用户
        return sysLoginUser;
    }

    /**
     * 检查token
     * @param token
     * @return
     */
    public void checkToken(String token){
        Boolean tokenCorrect = JwtTokenUtil.checkToken(token);
        if (!tokenCorrect) {
            throw new AuthException(AuthExceptionEnum.REQUEST_TOKEN_ERROR);
        }
        //校验token是否失效
        Boolean tokenExpired = JwtTokenUtil.isTokenExpired(token);
        if (tokenExpired) {
            throw new AuthException(AuthExceptionEnum.LOGIN_EXPIRED);
        }
    }

    /**
     * 根据用户标识刷新redis缓存
     * @param jwtPayLoad
     * @param sysLoginUser
     */
    private void cacheLoginUser(JwtPayLoad jwtPayLoad, SysLoginUser sysLoginUser) {
        String redisLoginUserKey = jwtPayLoad.getUuid();
        userCache.put(redisLoginUserKey, sysLoginUser);
    }

}
