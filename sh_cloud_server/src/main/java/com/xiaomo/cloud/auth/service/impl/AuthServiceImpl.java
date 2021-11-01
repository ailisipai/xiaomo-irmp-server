package com.xiaomo.cloud.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.xiaomo.cloud.auth.factory.LoginUserFactory;
import com.xiaomo.cloud.core.enums.LogSuccessStatusEnum;
import com.xiaomo.cloud.core.log.LogManager;
import com.xiaomo.cloud.sysuser.service.ISysUserService;
import com.xiaomo.common.auth.api.IAuthService;
import com.xiaomo.common.auth.entity.SysUser;
import com.xiaomo.common.cache.UserCache;
import com.xiaomo.common.cache.dbs.CurrentDataSourceContext;
import com.xiaomo.common.context.constant.ConstantContextHolder;
import com.xiaomo.common.enums.CommonStatusEnum;
import com.xiaomo.common.exception.AuthException;
import com.xiaomo.common.exception.enums.AuthExceptionEnum;
import com.xiaomo.common.jwtutil.JwtPayLoad;
import com.xiaomo.common.jwtutil.JwtTokenUtil;
import com.xiaomo.common.pojo.login.SysLoginUser;
import com.xiaomo.common.tenant.context.TenantCodeHolder;
import com.xiaomo.common.tenant.context.TenantDbNameHolder;
import com.xiaomo.common.tenant.entity.TenantInfo;
import com.xiaomo.common.tenant.exception.TenantException;
import com.xiaomo.common.tenant.exception.enums.TenantExceptionEnum;
import com.xiaomo.common.tenant.service.TenantInfoService;
import com.xiaomo.common.util.HttpServletUtil;
import com.xiaomo.common.util.IpAddressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 认证相关service实现类
 *
 * @author xiaomo
 * @date 2021/10/11 16:58
 */
@Service
public class AuthServiceImpl implements IAuthService, UserDetailsService {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private UserCache userCache ;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public void cacheTenantInfo(String tenantCode) {
        if (StrUtil.isBlank(tenantCode)) {
            return;
        }

        // 从spring容器中获取service，如果没开多租户功能，没引入相关包，这里会报错
        TenantInfoService tenantInfoService = null;
        try {
            tenantInfoService = SpringUtil.getBean(TenantInfoService.class);
        } catch (Exception e) {
            throw new TenantException(TenantExceptionEnum.TENANT_MODULE_NOT_ENABLE_ERROR);
        }

        // 获取租户信息
        TenantInfo tenantInfo = tenantInfoService.getByCode(tenantCode);
        if (tenantInfo != null) {
            String dbName = tenantInfo.getDbName();

            // 租户编码的临时存放
            TenantCodeHolder.put(tenantCode);

            // 租户的数据库名称临时缓存
            TenantDbNameHolder.put(dbName);

            // 数据源信息临时缓存
            CurrentDataSourceContext.setDataSourceType(dbName);
        } else {
            throw new TenantException(TenantExceptionEnum.CNAT_FIND_TENANT_ERROR);
        }
    }
    /**
     * 用户登录
     * @param username  账号
     * @param password 密码
     * @return
     */
    @Override
    public String login(String username, String password) {
        //账号密码验证
        if(ObjectUtil.hasEmpty(username,password)){
            LogManager.me().executeLoginLog(username, LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_PWD_EMPTY.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_PWD_EMPTY);
        }
        SysUser sysUser = sysUserService.getUserByUserName(username);
        //用户不存在或者账户密码错误
        if (ObjectUtil.isEmpty(sysUser)) {
            LogManager.me().executeLoginLog(username, LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_PWD_ERROR.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_PWD_ERROR);
        }
        //获取用户登录密码做验证
        String passwordBcrypt = sysUser.getPassword();
        //验证账号密码是否正确
        if (ObjectUtil.isEmpty(passwordBcrypt) || !BCrypt.checkpw(password, passwordBcrypt)) {
            LogManager.me().executeLoginLog(sysUser.getAccount(), LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_PWD_ERROR.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_PWD_ERROR);
        }
        return doLogin(sysUser);
    }
    @Override
    public String doLogin(SysUser sysUser) {

        Integer sysUserStatus = sysUser.getStatus();

        //验证账号是否被冻结
        if (CommonStatusEnum.DISABLE.getCode().equals(sysUserStatus)) {
            LogManager.me().executeLoginLog(sysUser.getAccount(), LogSuccessStatusEnum.FAIL.getCode(), AuthExceptionEnum.ACCOUNT_FREEZE_ERROR.getMessage());
            throw new AuthException(AuthExceptionEnum.ACCOUNT_FREEZE_ERROR);
        }

        //构造SysLoginUser
        SysLoginUser sysLoginUser = this.genSysLoginUser(sysUser);

        //构造jwtPayLoad
        JwtPayLoad jwtPayLoad = new JwtPayLoad(sysUser.getId(), sysUser.getAccount());

        //生成token
        String token = JwtTokenUtil.generateToken(jwtPayLoad);

        //缓存token与登录用户信息对应, 默认2个小时
        this.cacheLoginUser(jwtPayLoad, sysLoginUser);

        //设置最后登录ip和时间
        sysUser.setLastLoginIp(IpAddressUtil.getIp(HttpServletUtil.getRequest()));
        sysUser.setLastLoginTime(DateTime.now());

        //更新用户登录信息
        sysUserService.updateById(sysUser);

        //登录成功，记录登录日志
        LogManager.me().executeLoginLog(sysUser.getAccount(), LogSuccessStatusEnum.SUCCESS.getCode(), null);

        //登录成功，设置SpringSecurityContext上下文，方便获取用户
        this.setSpringSecurityContextAuthentication(sysLoginUser);

        //如果开启限制单用户登陆，则踢掉原来的用户
        Boolean enableSingleLogin = ConstantContextHolder.getEnableSingleLogin();
        if (enableSingleLogin) {

            //获取所有的登陆用户
            Map<String, SysLoginUser> allLoginUsers = userCache.getAllKeyValues();
            for (Map.Entry<String, SysLoginUser> loginedUserEntry : allLoginUsers.entrySet()) {

                String loginedUserKey = loginedUserEntry.getKey();
                SysLoginUser loginedUser = loginedUserEntry.getValue();

                //如果账号名称相同，并且redis缓存key和刚刚生成的用户的uuid不一样，则清除以前登录的
                if (loginedUser.getName().equals(sysUser.getName())
                        && !loginedUserKey.equals(jwtPayLoad.getUuid())) {
                    this.clearUser(loginedUserKey, loginedUser.getAccount());
                }
            }
        }
        //返回token
        return token;
    }

    @Override
    public void setSpringSecurityContextAuthentication(SysLoginUser sysLoginUser) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        sysLoginUser,
                        null,
                        sysLoginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
    /**
     * 构造登录用户信息
     *
     * @author xiaomo
     * @date 2020/3/12 17:32
     */
    @Override
    public SysLoginUser genSysLoginUser(SysUser sysUser) {
        SysLoginUser sysLoginUser = new SysLoginUser();
        BeanUtil.copyProperties(sysUser, sysLoginUser);
        LoginUserFactory.fillLoginUserInfo(sysLoginUser);
        return sysLoginUser;
    }
    /*==========================================工具方法=============================================*/


    /**
     * 缓存token与登录用户信息对应, 默认2个小时
     *
     * @author xiaomo
     * @date 2020/3/13 14:51
     */
    private void cacheLoginUser(JwtPayLoad jwtPayLoad, SysLoginUser sysLoginUser) {
        String redisLoginUserKey = jwtPayLoad.getUuid();
        userCache.put(redisLoginUserKey, sysLoginUser, Convert.toLong(ConstantContextHolder.getSessionTokenExpireSec()));
    }

    /**
     * 根据key清空登陆信息
     *
     * @author xiaomo
     * @date 2020/6/19 12:28
     */
    private void clearUser(String loginUserKey, String account) {
        //获取缓存的用户
        Object cacheObject = userCache.get(loginUserKey);

        //如果缓存的用户存在，清除会话，否则表示该会话信息已失效，不执行任何操作
        if (ObjectUtil.isNotEmpty(cacheObject)) {
            //清除登录会话
            userCache.remove(loginUserKey);
            //创建退出登录日志
            LogManager.me().executeExitLog(account);
        }
    }
}
