package com.xiaomo.cloud.sysuserdatascope.service;

import com.xiaomo.cloud.sysuserdatascope.entity.SysUserDataScope;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统用户数据范围表 服务类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
public interface ISysUserDataScopeService extends IService<SysUserDataScope> {

    /**
     * 获取用户的数据范围id集合
     *
     * @param uerId 用户id
     * @return 数据范围id集合
     * @author xiaomo
     * @date 2020/4/5 17:27
     */
    List<Long> getUserDataScopeIdList(Long uerId);
}
