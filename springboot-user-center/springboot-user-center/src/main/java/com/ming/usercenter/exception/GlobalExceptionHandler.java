package com.ming.usercenter.exception;

import com.ming.usercenter.common.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// （全局异常处理：统一捕获项目中没有处理的异常）
@RestControllerAdvice
public class GlobalExceptionHandler {

    // （处理参数不合法异常）
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegalArgumentException(
            IllegalArgumentException exception
    ) {
        return ApiResponse.fail(
                400,
                exception.getMessage()
        );
    }

    // （兜底处理：捕获其他未知异常）
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception exception) {
        exception.printStackTrace();

        return ApiResponse.fail(
                500,
                "服务器内部错误"
        );
    }
}