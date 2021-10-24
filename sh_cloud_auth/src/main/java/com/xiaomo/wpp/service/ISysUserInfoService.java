package com.xiaomo.wpp.service;

import com.xiaomo.wpp.model.entity.SysUserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  用户信息服务类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-10
 */
public interface ISysUserInfoService extends IService<SysUserInfo> {

    /**
     * 根据用户ID查询用户信息
     * @param userId
     * @return
     */
    SysUserInfo getUserInfo(Long userId);

    /**
     * 根据用户名称查询用户信息
     * @param username
     * @return
     */
    SysUserInfo loadUserByUserName(String username);
}
