package com.ming.usercenter.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateRequest {

    // 用户名不能为null、空字符串或只有空格
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度必须在3到50之间")
    private String username;

    // 密码不能是空值，长度至少6位
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 50, message = "密码长度必须在6到50之间")
    private String password;

    // Integer可能为null，所以需要@NotNull
    @NotNull(message = "年龄不能为空")
    @Min(value = 1, message = "年龄不能小于1")
    @Max(value = 150, message = "年龄不能大于150")
    private Integer age;
}