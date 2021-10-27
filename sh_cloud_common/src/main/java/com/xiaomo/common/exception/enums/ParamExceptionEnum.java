package com.xiaomo.common.exception.enums;

import com.xiaomo.common.annotion.ExpEnumType;
import com.xiaomo.common.consts.ExpEnumConstant;
import com.xiaomo.common.exception.enums.abs.AbstractBaseExceptionEnum;
import com.xiaomo.common.factory.ExpEnumCodeFactory;


/**
 * 参数校验异常枚举
 *
 * @author xiaomo
 * @date 2021/10/25 20:11
 */
@ExpEnumType(module = ExpEnumConstant.SNOWY_CORE_MODULE_EXP_CODE, kind = ExpEnumConstant.PARAM_EXCEPTION_ENUM)
public enum ParamExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 参数错误
     */
    PARAM_ERROR(1, "参数错误");

    private final Integer code;

    private final String message;

    ParamExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return ExpEnumCodeFactory.getExpEnumCode(this.getClass(), code);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
