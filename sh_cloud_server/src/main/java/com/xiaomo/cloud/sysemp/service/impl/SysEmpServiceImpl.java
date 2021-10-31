package com.xiaomo.cloud.sysemp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomo.cloud.sysemp.entity.SysEmp;
import com.xiaomo.cloud.sysemp.mapper.SysEmpMapper;
import com.xiaomo.cloud.sysemp.service.ISysEmpService;
import com.xiaomo.cloud.sysempextorgpos.service.ISysEmpExtOrgPosService;
import com.xiaomo.cloud.sysemppos.service.ISysEmpPosService;
import com.xiaomo.cloud.sysuser.service.ISysUserService;
import com.xiaomo.common.pojo.login.LoginEmpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 员工表 服务实现类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
@Service
public class SysEmpServiceImpl extends ServiceImpl<SysEmpMapper, SysEmp> implements ISysEmpService {


    @Autowired
    private ISysEmpExtOrgPosService sysEmpExtOrgPosService;

    @Autowired
    private ISysEmpPosService sysEmpPosService;

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public LoginEmpInfo getLoginEmpInfo(Long empId) {

        LoginEmpInfo loginEmpInfo = new LoginEmpInfo();
        // 获取员工信息
        SysEmp sysEmp = this.getById(empId);
        if(ObjectUtil.isNotNull(sysEmp)){
            BeanUtil.copyProperties(sysEmp, loginEmpInfo);
            //获取附属机构和职位信息
            List<Dict> empExtOrgPosDictList = sysEmpExtOrgPosService.getEmpExtOrgPosDictList(sysEmp.getId(), false);
            loginEmpInfo.setExtOrgPos(empExtOrgPosDictList);

            //获取所属职位信息
            List<Dict> empEmpPosDictList = sysEmpPosService.getEmpPosDictList(sysEmp.getId(), false);
            loginEmpInfo.setPositions(empEmpPosDictList);

        }else{
            loginEmpInfo.setExtOrgPos(CollectionUtil.newArrayList());
            loginEmpInfo.setPositions(CollectionUtil.newArrayList());
        }
        return loginEmpInfo;
    }
}
