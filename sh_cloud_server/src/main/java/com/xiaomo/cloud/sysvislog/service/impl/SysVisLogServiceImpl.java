package com.xiaomo.cloud.sysvislog.service.impl;

import com.xiaomo.cloud.sysvislog.entity.SysVisLog;
import com.xiaomo.cloud.sysvislog.mapper.SysVisLogMapper;
import com.xiaomo.cloud.sysvislog.service.ISysVisLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统访问日志表 服务实现类
 * </p>
 *
 * @author xiaomo
 * @since 2021-10-28
 */
@Service
public class SysVisLogServiceImpl extends ServiceImpl<SysVisLogMapper, SysVisLog> implements ISysVisLogService {

}
