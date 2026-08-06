package com.ming.usercenter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// （用户实体类：对应MySQL中的users表）
@Data
@NoArgsConstructor
public class User {

    // （对应数据库的id）
    private Long id;

    // （对应数据库的username）
    private String username;

    // （对应数据库的password）
    private String password;

    // （对应数据库的age）
    private Integer age;

    // （对应数据库的status）
    private Integer status;

    // （对应数据库的created_at）
    private LocalDateTime createdAt;
}