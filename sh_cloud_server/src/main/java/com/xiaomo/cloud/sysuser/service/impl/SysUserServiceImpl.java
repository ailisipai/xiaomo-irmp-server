package com.xiaomo.cloud.sysuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomo.cloud.sysuser.mapper.SysUserMapper;
import com.xiaomo.cloud.sysuser.service.ISysUserService;
import com.xiaomo.common.auth.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-27
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
