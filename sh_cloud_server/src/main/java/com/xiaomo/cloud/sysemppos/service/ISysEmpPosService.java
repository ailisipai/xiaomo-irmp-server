package com.xiaomo.cloud.sysemppos.service;

import cn.hutool.core.lang.Dict;
import com.xiaomo.cloud.sysemppos.entity.SysEmpPos;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 员工职位关联表 服务类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
public interface ISysEmpPosService extends IService<SysEmpPos> {

    /**
     * 获取所属职位信息
     *
     * @param empId    员工id（用户id）
     * @param isFillId 是否需要返回id信息
     * @return 增强版hashMap，格式：[{"posId":456, "posCode":"zjl", "posName":"总经理"}]
     * @author xiaomo
     * @date 2021/11/2 20:07
     */
    List<Dict> getEmpPosDictList(Long empId, boolean isFillId);
}
