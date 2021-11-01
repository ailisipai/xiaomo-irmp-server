package com.xiaomo.cloud.sysuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomo.common.auth.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-27
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
