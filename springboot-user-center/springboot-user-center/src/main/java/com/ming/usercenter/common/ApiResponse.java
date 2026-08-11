package com.ming.usercenter.common;

import lombok.AllArgsConstructor;
import lombok.Data;

// （统一响应类：规定所有接口返回给前端的格式）
@Data
@AllArgsConstructor
public class ApiResponse<T> {

    // 状态码，例如200表示成功
    private Integer code;

    // 提示信息
    private String message;

    // 真正返回给前端的数据
    private T data;

    // （成功响应：包装接口返回的数据）
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "success", data);
    }

    // （失败响应：包装错误码和错误信息）
    public static <T> ApiResponse<T> fail(Integer code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}