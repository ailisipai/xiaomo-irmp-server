package com.xiaomo.cloud.sysuserrole.service;

import com.xiaomo.cloud.sysuserrole.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统用户角色表 服务类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 获取用户的角色id集合
     *
     * @param userId 用户id
     * @return 角色id集合
     * @author xiaomo
     * @date 2020/3/20 22:29
     */
    List<Long> getUserRoleIdList(Long userId);
    /**
     * 获取用户所有角色的数据范围（组织机构id集合）
     *
     * @param userId 用户id
     * @param orgId  组织机构id
     * @return 数据范围id集合（组织机构id集合）
     * @author xiaomo
     * @date 2020/4/5 17:31
     */
    List<Long> getUserRoleDataScopeIdList(Long userId, Long orgId);
}
