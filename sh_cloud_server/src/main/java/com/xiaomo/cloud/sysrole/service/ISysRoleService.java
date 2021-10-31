package com.xiaomo.cloud.sysrole.service;

import cn.hutool.core.lang.Dict;
import com.xiaomo.cloud.sysrole.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 获取用户角色相关信息
     *
     * @param userId 用户id
     * @return 增强版hashMap，格式：[{"id":456, "code":"zjl", "name":"总经理"}]
     * @author xiaomo
     * @since 2021-10-31
     */
    List<Dict> getLoginRoles(Long userId);

    /**
     * 根据角色id集合获取数据范围id集合
     *
     * @param roleIdList 角色id集合
     * @param orgId      机构id
     * @return 数据范围id集合
     * @author xuyuxiang
     * @date 2020/4/5 17:41
     */
    List<Long> getUserDataScopeIdList(List<Long> roleIdList, Long orgId);
}
