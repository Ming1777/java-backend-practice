package com.ming.usercenter.common;

// （统一错误码：集中管理项目中的常见错误）
public enum ErrorCode {

    BAD_REQUEST(400, "请求参数不合法"),
    UNAUTHORIZED(401, "未登录或登录已失效"),
    FORBIDDEN(403, "没有权限执行该操作"),
    NOT_FOUND(404, "请求的数据不存在"),
    SYSTEM_ERROR(500, "服务器内部错误");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
