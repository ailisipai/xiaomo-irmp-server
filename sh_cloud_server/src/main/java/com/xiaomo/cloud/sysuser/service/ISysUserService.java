package com.xiaomo.cloud.sysuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomo.common.auth.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-27
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    SysUser getUserByUserName(String userName);

    /**
     * 获取用户的数据范围（组织机构id集合）
     *
     * @param userId 用户id
     * @param orgId  组织机构id
     * @return 数据范围id集合（组织机构id集合）
     * @author xiaomo
     * @date 2020/4/5 17:23
     */
    List<Long> getUserDataScopeIdList(Long userId, Long orgId);
}
