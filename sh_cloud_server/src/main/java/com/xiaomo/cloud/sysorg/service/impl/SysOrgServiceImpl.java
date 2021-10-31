package com.xiaomo.cloud.sysorg.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomo.cloud.core.enums.DataScopeTypeEnum;
import com.xiaomo.cloud.sysorg.entity.SysOrg;
import com.xiaomo.cloud.sysorg.mapper.SysOrgMapper;
import com.xiaomo.cloud.sysorg.service.ISysOrgService;
import com.xiaomo.common.consts.SymbolConstant;
import com.xiaomo.common.enums.CommonStatusEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统组织机构表 服务实现类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
@Service
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper, SysOrg> implements ISysOrgService {

    @Override
    public List<Long> getDataScopeListByDataScopeType(Integer dataScopeType, Long orgId) {
        List<Long> resultList = CollectionUtil.newArrayList();

        if (ObjectUtil.isEmpty(orgId)) {
            return CollectionUtil.newArrayList();
        }

        // 如果是范围类型是全部数据，则获取当前系统所有的组织架构id
        if (DataScopeTypeEnum.ALL.getCode().equals(dataScopeType)) {
            resultList = this.getOrgIdAll();
        }
        // 如果范围类型是本部门及以下部门，则查询本节点和子节点集合，包含本节点
        else if (DataScopeTypeEnum.DEPT_WITH_CHILD.getCode().equals(dataScopeType)) {
            resultList = this.getChildIdListWithSelfById(orgId);
        }
        // 如果数据范围是本部门，不含子节点，则直接返回本部门
        else if (DataScopeTypeEnum.DEPT.getCode().equals(dataScopeType)) {
            resultList.add(orgId);
        }
        return resultList;
    }
    /*==========================================================================================*/
    /**
     * 根据条件获取组织机构id集合
     *
     * @author xuyuxiang
     * @date 2020/4/5 18:35
     */
    private List<Long> getOrgIdAll() {
        List<Long> resultList = CollectionUtil.newArrayList();

        LambdaQueryWrapper<SysOrg> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(SysOrg::getStatus, CommonStatusEnum.ENABLE.getCode());

        this.list(queryWrapper).forEach(sysOrg -> resultList.add(sysOrg.getId()));
        return resultList;
    }
    /**
     * 根据节点id获取所有子节点id集合，包含自己
     *
     * @author xuyuxiang
     * @date 2020/4/6 14:54
     */
    private List<Long> getChildIdListWithSelfById(Long id) {
        List<Long> childIdListById = this.getChildIdListById(id);
        List<Long> resultList = CollectionUtil.newArrayList(childIdListById);
        resultList.add(id);
        return resultList;
    }
    /**
     * 根据节点id获取所有子节点id集合
     *
     * @author xuyuxiang
     * @date 2020/3/26 11:31
     */
    private List<Long> getChildIdListById(Long id) {
        List<Long> childIdList = CollectionUtil.newArrayList();
        LambdaQueryWrapper<SysOrg> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(SysOrg::getPids, SymbolConstant.LEFT_SQUARE_BRACKETS + id +
                SymbolConstant.RIGHT_SQUARE_BRACKETS);

        this.list(queryWrapper).forEach(sysOrg -> childIdList.add(sysOrg.getId()));

        return childIdList;
    }
}
