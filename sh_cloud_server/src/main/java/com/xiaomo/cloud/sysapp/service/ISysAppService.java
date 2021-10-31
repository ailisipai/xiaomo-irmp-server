package com.xiaomo.cloud.sysapp.service;

import cn.hutool.core.lang.Dict;
import com.xiaomo.cloud.sysapp.entity.SysApp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统应用表 服务类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
public interface ISysAppService extends IService<SysApp> {

    /**
     * 获取用户应用相关信息
     *
     * @param userId 用户id
     * @param roleIdList 角色id集合
     * @return 用户拥有的应用信息，格式：[{"code":"system","name":"系统应用","active":true}]
     * @author xiaomo
     * @date 2020/3/13 16:25
     */
    List<Dict> getLoginApps(Long userId, List<Long> roleIdList);
}
