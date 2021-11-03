package com.xiaomo.common.pojo.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 失败响应结果
 *
 * @author xiaomo
 * @date 2021/11/30 15:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponseData extends ResponseData {

    /**
     * 异常的具体类名称
     */
    private String exceptionClazz;

    ErrorResponseData(String message) {
        super(false, DEFAULT_ERROR_CODE, message, null);
    }

    public ErrorResponseData(Integer code, String message) {
        super(false, code, message, null);
    }

    ErrorResponseData(Integer code, String message, Object object) {
        super(false, code, message, object);
    }
}
