package com.xiaomo.wpp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaomo.wpp.model.entity.SysUserInfo;
import com.xiaomo.wpp.mapper.SysUserInfoMapper;
import com.xiaomo.wpp.service.ISysUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-10
 */
@Service
public class SysUserInfoServiceImpl extends ServiceImpl<SysUserInfoMapper, SysUserInfo> implements ISysUserInfoService {

    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;
    @Override
    public SysUserInfo getUserInfo(Long userId) {
        return sysUserInfoMapper.selectById(userId);
    }

    /**
     * 从数据库中加载用户
     * @param username
     * @return
     */
    @Override
    public SysUserInfo loadUserByUserName(String username){
        QueryWrapper queryWrapper = new QueryWrapper<SysUserInfo>();
        queryWrapper.eq("user_name",username);
        SysUserInfo userInfo = sysUserInfoMapper.selectOne(queryWrapper);
        return userInfo;
    }
}
