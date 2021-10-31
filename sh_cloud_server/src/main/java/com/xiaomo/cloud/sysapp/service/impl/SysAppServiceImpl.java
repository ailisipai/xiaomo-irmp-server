package com.xiaomo.cloud.sysapp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomo.cloud.core.enums.AdminTypeEnum;
import com.xiaomo.cloud.sysapp.entity.SysApp;
import com.xiaomo.cloud.sysapp.mapper.SysAppMapper;
import com.xiaomo.cloud.sysapp.service.ISysAppService;
import com.xiaomo.cloud.sysmenu.service.ISysMenuService;
import com.xiaomo.cloud.sysuser.service.ISysUserService;
import com.xiaomo.common.auth.entity.SysUser;
import com.xiaomo.common.consts.CommonConstant;
import com.xiaomo.common.enums.CommonStatusEnum;
import com.xiaomo.common.enums.YesOrNotEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * 系统应用表 服务实现类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
@Service
public class SysAppServiceImpl extends ServiceImpl<SysAppMapper, SysApp> implements ISysAppService {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysMenuService sysMenuService;
    @Override
    public List<Dict> getLoginApps(Long userId, List<Long> roleIdList) {
        List<Dict> userAppDictList = CollectionUtil.newArrayList();

        LambdaQueryWrapper<SysApp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysApp::getStatus, CommonStatusEnum.ENABLE.getCode());


        SysUser sysUser = sysUserService.getById(userId);
        Integer adminType = sysUser.getAdminType();

        //如果不是超级管理员则有自己的菜单对应的应用编码
        if (!AdminTypeEnum.SUPER_ADMIN.getCode().equals(adminType)) {
            //获取用户菜单对应的应用编码集合
            List<String> appCodeList = sysMenuService.getUserMenuAppCodeList(userId, roleIdList);
            //当应用编码不为空时，则限制查询范围
            if (ObjectUtil.isNotEmpty(appCodeList)) {
                queryWrapper.in(SysApp::getCode, appCodeList);
            } else {
                //没查到应用编码则直接返回
                return userAppDictList;
            }
        }
        //定义是否有默认激活的应用标志
        AtomicBoolean hasDefaultActive = new AtomicBoolean(false);
        //遍历
        this.list(queryWrapper).forEach(sysApp -> {
            Dict dict = Dict.create();
            dict.put(CommonConstant.CODE, sysApp.getCode());
            dict.put(CommonConstant.NAME, sysApp.getName());
            //如果有默认激活的
            if (YesOrNotEnum.Y.getCode().equals(sysApp.getActive())) {
                hasDefaultActive.set(true);
                dict.put("active", true);
                //将其放在第一个
                userAppDictList.add(0, dict);
            } else {
                dict.put("active", false);
                userAppDictList.add(dict);
            }

        });
        if (ObjectUtil.isNotEmpty(userAppDictList)) {
            //如果默认激活的系统没有，则第一个为默认激活的系统
            if (!hasDefaultActive.get()) {
                Dict dict = userAppDictList.get(0);
                dict.put("active", true);
            }
        }
        return userAppDictList;
    }
}
