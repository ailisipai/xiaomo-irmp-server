package com.xiaomo.cloud.sysvislog.mapper;

import com.xiaomo.cloud.sysvislog.entity.SysVisLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统访问日志表 Mapper 接口
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-28
 */
@Mapper
public interface SysVisLogMapper extends BaseMapper<SysVisLog> {

}
