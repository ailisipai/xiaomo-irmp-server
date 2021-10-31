package com.xiaomo.cloud.sysemp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomo.cloud.sysemp.entity.SysEmp;
import com.xiaomo.common.pojo.login.LoginEmpInfo;

/**
 * <p>
 * 员工表 服务类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-31
 */
public interface ISysEmpService extends IService<SysEmp> {

    LoginEmpInfo getLoginEmpInfo(Long empId);
}
