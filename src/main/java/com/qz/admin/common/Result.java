package com.qz.admin.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 统一响应结构：前端约定通过 success 判断成功与否，data 为业务数据。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    private boolean success;
    private String message;
    private T data;

    public Result() {
    }

    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.success = true;
        r.message = "ok";
        r.data = data;
        return r;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> error(String message) {
        Result<T> r = new Result<>();
        r.success = false;
        r.message = message;
        return r;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
