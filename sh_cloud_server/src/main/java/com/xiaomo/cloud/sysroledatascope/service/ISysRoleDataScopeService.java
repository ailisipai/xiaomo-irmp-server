package com.xiaomo.cloud.sysroledatascope.service;

import com.xiaomo.cloud.sysroledatascope.entity.SysRoleDataScope;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统角色数据范围表 服务类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
public interface ISysRoleDataScopeService extends IService<SysRoleDataScope> {

    /**
     * 根据角色id获取角色数据范围集合
     *
     * @param roleIdList 角色id集合
     * @return 数据范围id集合
     * @author xiaomo
     * @date 2021/11/5 17:44
     */
    List<Long> getRoleDataScopeIdList(List<Long> roleIdList);
}
