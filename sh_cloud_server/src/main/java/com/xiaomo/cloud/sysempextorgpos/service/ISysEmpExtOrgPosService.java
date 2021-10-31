package com.xiaomo.cloud.sysempextorgpos.service;

import cn.hutool.core.lang.Dict;
import com.xiaomo.cloud.sysempextorgpos.entity.SysEmpExtOrgPos;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 员工附属机构岗位表 服务类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
public interface ISysEmpExtOrgPosService extends IService<SysEmpExtOrgPos> {

    /**
     * 获取附属机构和职位信息
     *
     * @param empId    员工id（用户id）
     * @param isFillId 是否需要返回id信息
     * @return 增强版hashMap，格式：[{"orgId":123, "orgCode":"yfb", "orgName":"研发部", "posId":456, "posCode":"zjl", "posName":"总经理"}]
     * @author xiaomo
     * @date 2020/4/2 20:07
     */
    List<Dict> getEmpExtOrgPosDictList(Long empId, boolean isFillId);
}
