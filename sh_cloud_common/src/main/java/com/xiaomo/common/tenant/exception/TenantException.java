package com.xiaomo.common.tenant.exception;


import com.xiaomo.common.exception.ServiceException;
import com.xiaomo.common.exception.enums.abs.AbstractBaseExceptionEnum;

/**
 * 多租户的异常
 *
 * @author xiaomo
 * @date 2020/9/3
 */
public class TenantException extends ServiceException {

    public TenantException(AbstractBaseExceptionEnum exception) {
        super(exception);
    }

}
