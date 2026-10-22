package com.qz.admin.config;

import com.qz.admin.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常兜底：业务异常（IllegalArgumentException）与未知异常均转换为 success=false 的响应，
 * 保持与前端约定一致（HTTP 200 + success 字段判断）。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleBiz(IllegalArgumentException e) {
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleUnknown(Exception e) {
        return Result.error("服务器开小差了，请稍后重试");
    }
}
