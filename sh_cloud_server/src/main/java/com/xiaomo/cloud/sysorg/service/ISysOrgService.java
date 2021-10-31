package com.xiaomo.cloud.sysorg.service;

import com.xiaomo.cloud.sysorg.entity.SysOrg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统组织机构表 服务类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
public interface ISysOrgService extends IService<SysOrg> {

    /**
     * 根据数据范围类型获取当前登录用户的数据范围id集合
     *
     * @param dataScopeType 数据范围类型（1全部数据 2本部门及以下数据 3本部门数据 4仅本人数据）
     * @param orgId         组织机构id
     * @return 数据范围id集合
     * @author xuyuxiang
     * @date 2020/4/5 18:29
     */
    List<Long> getDataScopeListByDataScopeType(Integer dataScopeType, Long orgId);
}
