package com.xiaomo.cloud.sysrole.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomo.cloud.core.enums.DataScopeTypeEnum;
import com.xiaomo.cloud.sysorg.service.ISysOrgService;
import com.xiaomo.cloud.sysrole.entity.SysRole;
import com.xiaomo.cloud.sysrole.mapper.SysRoleMapper;
import com.xiaomo.cloud.sysrole.service.ISysRoleService;
import com.xiaomo.cloud.sysroledatascope.service.ISysRoleDataScopeService;
import com.xiaomo.cloud.sysuserrole.service.ISysUserRoleService;
import com.xiaomo.common.consts.CommonConstant;
import com.xiaomo.common.enums.CommonStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ISysOrgService sysOrgService;
    @Autowired
    private ISysRoleDataScopeService sysRoleDataScopeService;

    @Override
    public List<Dict> getLoginRoles(Long userId) {
        List<Dict> dictList = CollectionUtil.newArrayList();
        //获取用户角色id集合
        List<Long> roleIdList = sysUserRoleService.getUserRoleIdList(userId);
        if (ObjectUtil.isNotEmpty(roleIdList)) {
            LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysRole::getId, roleIdList).eq(SysRole::getStatus, CommonStatusEnum.ENABLE.getCode());
            //根据角色id集合查询并返回结果
            dictList = this.list(queryWrapper).stream().map(sysRole -> {
                Dict dict = Dict.create();
                dict.put(CommonConstant.ID, sysRole.getId());
                dict.put(CommonConstant.CODE, sysRole.getCode());
                dict.put(CommonConstant.NAME, sysRole.getName());
                return dict;
            }).collect(Collectors.toList());
        }
        return dictList;
    }

    @Override
    public List<Long> getUserDataScopeIdList(List<Long> roleIdList, Long orgId) {
        Set<Long> resultList = CollectionUtil.newHashSet();

        //定义角色中最大数据范围的类型，目前系统按最大范围策略来，如果你同时拥有ALL和SELF的权限，系统最后按ALL返回
        Integer strongerDataScopeType = DataScopeTypeEnum.SELF.getCode();

        //获取用户自定义数据范围的角色集合
        List<Long> customDataScopeRoleIdList = CollectionUtil.newArrayList();
        if (ObjectUtil.isNotEmpty(roleIdList)) {
            List<SysRole> sysRoleList = this.listByIds(roleIdList);
            for (SysRole sysRole : sysRoleList) {
                if (DataScopeTypeEnum.DEFINE.getCode().equals(sysRole.getDataScopeType())) {
                    customDataScopeRoleIdList.add(sysRole.getId());
                } else {
                    if (sysRole.getDataScopeType() <= strongerDataScopeType) {
                        strongerDataScopeType = sysRole.getDataScopeType();
                    }
                }
            }
        }

        //自定义数据范围的角色对应的数据范围
        List<Long> roleDataScopeIdList = sysRoleDataScopeService.getRoleDataScopeIdList(customDataScopeRoleIdList);

        //角色中拥有最大数据范围类型的数据范围
        List<Long> dataScopeIdList = sysOrgService.getDataScopeListByDataScopeType(strongerDataScopeType, orgId);

        resultList.addAll(dataScopeIdList);
        resultList.addAll(roleDataScopeIdList);
        return CollectionUtil.newArrayList(resultList);
    }
}
