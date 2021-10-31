package com.xiaomo.cloud.sysmenu.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomo.cloud.core.enums.AdminTypeEnum;
import com.xiaomo.cloud.core.enums.MenuOpenTypeEnum;
import com.xiaomo.cloud.core.enums.MenuTypeEnum;
import com.xiaomo.cloud.core.enums.MenuWeightEnum;
import com.xiaomo.cloud.sysmenu.entity.SysMenu;
import com.xiaomo.cloud.sysmenu.mapper.SysMenuMapper;
import com.xiaomo.cloud.sysmenu.service.ISysMenuService;
import com.xiaomo.cloud.sysrolemenu.service.ISysRoleMenuService;
import com.xiaomo.cloud.sysuser.service.ISysUserService;
import com.xiaomo.common.auth.entity.SysUser;
import com.xiaomo.common.consts.SymbolConstant;
import com.xiaomo.common.enums.CommonStatusEnum;
import com.xiaomo.common.enums.YesOrNotEnum;
import com.xiaomo.common.pojo.node.LoginMenuTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-27
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @Autowired
    private ISysUserService sysUserService;
    @Override
    public List<String> getLoginPermissions(Long userId, List<Long> menuIdList) {
        Set<String> permissions = CollectionUtil.newHashSet();
        if (ObjectUtil.isNotEmpty(menuIdList)) {
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysMenu::getId, menuIdList).ne(SysMenu::getType, MenuTypeEnum.DIR.getCode())
                    .eq(SysMenu::getStatus, CommonStatusEnum.ENABLE.getCode());

            this.list(queryWrapper).forEach(sysMenu -> {
                if(MenuTypeEnum.BTN.getCode().equals(sysMenu.getType())) {
                    permissions.add(sysMenu.getPermission());
                } else {
                    String removePrefix = StrUtil.removePrefix(sysMenu.getRouter(), SymbolConstant.LEFT_DIVIDE);
                    String permission = removePrefix.replaceAll(SymbolConstant.LEFT_DIVIDE, SymbolConstant.COLON);
                    permissions.add(permission);
                }
            });
        }
        return CollectionUtil.newArrayList(permissions);
    }

    @Override
    public List<String> getUserMenuAppCodeList(Long userId, List<Long> roleIdList) {
        Set<String> appCodeSet = CollectionUtil.newHashSet();

        if (ObjectUtil.isNotEmpty(roleIdList)) {
            List<Long> menuIdList = sysRoleMenuService.getRoleMenuIdList(roleIdList);

            if (ObjectUtil.isNotEmpty(menuIdList)) {
                LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.in(SysMenu::getId, menuIdList)
                        .eq(SysMenu::getStatus, CommonStatusEnum.ENABLE.getCode());
                appCodeSet = this.list(queryWrapper).stream().map(SysMenu::getApplication).collect(Collectors.toSet());
            }
        }

        return CollectionUtil.newArrayList(appCodeSet);
    }

    @Override
    public List<SysMenu> getLoginMenus(Long userId, String appCode, List<Long> menuIdList) {
        //如果是超级管理员则展示所有系统权重菜单，不能展示业务权重菜单
        SysUser sysUser = sysUserService.getById(userId);
        Integer adminType = sysUser.getAdminType();

        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();

        if (AdminTypeEnum.SUPER_ADMIN.getCode().equals(adminType)) {

            //查询权重不为业务权重的且类型不是按钮的
            queryWrapper.eq(SysMenu::getStatus, CommonStatusEnum.ENABLE.getCode())
                    .eq(SysMenu::getApplication, appCode)
                    .notIn(SysMenu::getType, MenuTypeEnum.BTN.getCode())
                    .notIn(SysMenu::getWeight, MenuWeightEnum.DEFAULT_WEIGHT.getCode())
                    .orderByAsc(SysMenu::getSort);
        } else {

            //非超级管理员则获取自己角色所拥有的菜单集合
            if (ObjectUtil.isNotEmpty(menuIdList)) {
                queryWrapper.in(SysMenu::getId, menuIdList)
                        .eq(SysMenu::getStatus, CommonStatusEnum.ENABLE.getCode())
                        .eq(SysMenu::getApplication, appCode)
                        .notIn(SysMenu::getType, MenuTypeEnum.BTN.getCode())
                        .orderByAsc(SysMenu::getSort);
            } else {
                //如果角色的菜单为空，则查不到菜单
                return CollectionUtil.newArrayList();
            }
        }
        //查询列表
        return this.list(queryWrapper);
    }

    @Override
    public List<LoginMenuTreeNode> convertSysMenuToLoginMenu(List<SysMenu> sysMenuList) {
        List<LoginMenuTreeNode> antDesignMenuTreeNodeList = CollectionUtil.newArrayList();
        sysMenuList.forEach(sysMenu -> {
            LoginMenuTreeNode loginMenuTreeNode = new LoginMenuTreeNode();
            loginMenuTreeNode.setComponent(sysMenu.getComponent());
            loginMenuTreeNode.setId(sysMenu.getId());
            loginMenuTreeNode.setName(sysMenu.getCode());
            loginMenuTreeNode.setPath(sysMenu.getRouter());
            loginMenuTreeNode.setPid(sysMenu.getPid());
            LoginMenuTreeNode.Meta mateItem = new LoginMenuTreeNode().new Meta();
            mateItem.setIcon(sysMenu.getIcon());
            mateItem.setTitle(sysMenu.getName());
            mateItem.setLink(sysMenu.getLink());
            //是否可见
            mateItem.setShow(!YesOrNotEnum.N.getCode().equals(sysMenu.getVisible()));
            //设置的首页，默认打开此链接
            loginMenuTreeNode.setRedirect(sysMenu.getRedirect());
            //是否是外链
            if (MenuOpenTypeEnum.OUTER.getCode().equals(sysMenu.getOpenType())) {
                //打开外链
                mateItem.setTarget("_blank");
                loginMenuTreeNode.setPath(sysMenu.getLink());
                loginMenuTreeNode.setRedirect(sysMenu.getLink());
            }
            loginMenuTreeNode.setMeta(mateItem);
            antDesignMenuTreeNodeList.add(loginMenuTreeNode);
        });
        return antDesignMenuTreeNodeList;
    }
}
