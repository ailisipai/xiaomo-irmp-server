package com.xiaomo.cloud.sysmenu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomo.cloud.sysmenu.entity.SysMenu;
import com.xiaomo.common.pojo.node.LoginMenuTreeNode;

import java.util.List;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-27
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 获取用户权限相关信息
     *
     * @param userId 用户id
     * @param menuIdList 菜单id集合
     * @return 权限集合
     * @author xiaomo
     * @date 2020/3/13 16:26
     */
    List<String> getLoginPermissions(Long userId, List<Long> menuIdList);
    /**
     * 获取用户菜单所属的应用编码集合
     *
     * @param userId 用户id
     * @param roleIdList 角色id集合
     * @return 用户菜单所属的应用编码集合
     * @author xiaomo
     * @date 2020/3/21 11:01
     */
    List<String> getUserMenuAppCodeList(Long userId, List<Long> roleIdList);

    /**
     * 获取用户AntDesign菜单相关信息，前端使用
     *
     * @param userId  用户id
     * @param appCode 应用编码
     * @param menuIdList 菜单id集合
     * @return AntDesign菜单信息结果集
     * @author yubaoshan
     * @date 2020/4/17 17:48
     */
    List<SysMenu> getLoginMenus(Long userId, String appCode, List<Long> menuIdList);

    /**
     * 将SysMenu格式菜单转换为LoginMenuTreeNode菜单
     *
     * @author xuyuxiang
     * @date 2021/6/29 13:43
     * @param sysMenuList 原始菜单集合
     * @return LoginMenuTreeNode菜单集合
     */
    List<LoginMenuTreeNode> convertSysMenuToLoginMenu(List<SysMenu> sysMenuList);
}
