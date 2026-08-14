package com.ming.usercenter.exception;

import com.ming.usercenter.common.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// （全局异常处理：统一捕获项目中没有处理的异常）
@RestControllerAdvice
public class GlobalExceptionHandler {

    // （处理可预料的业务异常）
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleBusinessException(
            BusinessException exception
    ) {
        return ApiResponse.fail(
                exception.getCode(),
                exception.getMessage()
        );
    }

    // （处理@Valid校验失败：提取第一条校验提示）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("请求参数不合法");

        return ApiResponse.fail(400, message);
    }

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
