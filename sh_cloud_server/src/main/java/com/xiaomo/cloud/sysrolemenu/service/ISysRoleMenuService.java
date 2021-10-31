package com.xiaomo.cloud.sysrolemenu.service;

import com.xiaomo.cloud.sysrolemenu.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统角色菜单表 服务类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 获取角色的菜单id集合
     *
     * @param roleIdList 角色id集合
     * @return 菜单id集合
     * @author xiaomo
     * @date 2020/3/21 10:17
     */
    List<Long> getRoleMenuIdList(List<Long> roleIdList);
}
