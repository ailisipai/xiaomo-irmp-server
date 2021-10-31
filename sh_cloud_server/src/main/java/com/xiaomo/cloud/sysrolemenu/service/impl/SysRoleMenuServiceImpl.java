package com.xiaomo.cloud.sysrolemenu.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaomo.cloud.sysrolemenu.entity.SysRoleMenu;
import com.xiaomo.cloud.sysrolemenu.mapper.SysRoleMenuMapper;
import com.xiaomo.cloud.sysrolemenu.service.ISysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色菜单表 服务实现类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Override
    public List<Long> getRoleMenuIdList(List<Long> roleIdList) {
        if(ObjectUtil.isNotEmpty(roleIdList)) {
            LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysRoleMenu::getRoleId, roleIdList);
            return this.list(queryWrapper).stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        }
        return CollectionUtil.newArrayList();
    }
}
