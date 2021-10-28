package com.xiaomo.cloud.sysuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomo.cloud.sysuser.mapper.SysUserMapper;
import com.xiaomo.cloud.sysuser.service.ISysUserService;
import com.xiaomo.common.auth.entity.SysUser;
import com.xiaomo.common.enums.CommonStatusEnum;
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

    @Override
    public SysUser getUserByUserName(String userName) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, userName);
        queryWrapper.ne(SysUser::getStatus, CommonStatusEnum.DELETED.getCode());
        return this.getOne(queryWrapper);
    }
}
