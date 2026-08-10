package com.ming.usercenter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateRequest {

    private String username;
    private String password;
    private Integer age;
}
