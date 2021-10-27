package com.xiaomo.common.context.system;

import cn.hutool.extra.spring.SpringUtil;

/**
 * 使用system模块相关功能的接口
 *
 * @author xiaomo
 * @date 2021/5/6 14:56
 */
public class SystemContextHolder {

    public static SystemContext me() {
        return SpringUtil.getBean(SystemContext.class);
    }

}
