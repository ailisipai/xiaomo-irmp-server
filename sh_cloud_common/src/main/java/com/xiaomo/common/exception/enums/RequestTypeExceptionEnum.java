package com.xiaomo.common.exception.enums;

import com.xiaomo.common.annotion.ExpEnumType;
import com.xiaomo.common.consts.ExpEnumConstant;
import com.xiaomo.common.exception.enums.abs.AbstractBaseExceptionEnum;
import com.xiaomo.common.factory.ExpEnumCodeFactory;

/**
 * 请求类型相关异常枚举
 *
 * @author xiaomo
 * @date 2021/4/2 15:42
 */
@ExpEnumType(module = ExpEnumConstant.SNOWY_CORE_MODULE_EXP_CODE, kind = ExpEnumConstant.REQUEST_TYPE_EXCEPTION_ENUM)
public enum RequestTypeExceptionEnum implements AbstractBaseExceptionEnum {

    /**
     * 参数传递格式不支持
     */
    REQUEST_TYPE_IS_JSON(1, "参数传递格式不支持，请使用JSON格式传参"),

    /**
     * 请求JSON参数格式不正确
     */
    REQUEST_JSON_ERROR(2, "请求JSON参数格式不正确，请检查参数格式");

    private final Integer code;

    private final String message;

    RequestTypeExceptionEnum(Integer code, String message) {
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
