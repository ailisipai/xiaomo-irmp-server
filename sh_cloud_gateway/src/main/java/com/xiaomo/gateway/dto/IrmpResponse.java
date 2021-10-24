package com.xiaomo.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 接口统一响应信息
 * @author wpp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IrmpResponse<T> implements Serializable {


    /**
     * 返回消息
     */
    private String message;

    /**
     * 请求响应码
     */
    private Integer code ;

    /**
     * 请求信息头
     */
    private Object headers;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应参数
     */
    private Object result;

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setHeaders(Object headers) {
        this.headers = headers;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public Object getHeaders() {
        return headers;
    }

    public T getData() {
        return data;
    }
}
