package com.ming.usercenter.exception;

import com.ming.usercenter.common.ErrorCode;

// （业务异常：表示用户名重复、未登录等可以预料的业务错误）
public class BusinessException extends RuntimeException {

    private final Integer code;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
