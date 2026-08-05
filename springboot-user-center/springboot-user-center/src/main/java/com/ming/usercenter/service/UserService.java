package com.ming.usercenter.service;

import com.ming.usercenter.dto.UserCreateRequest;
import com.ming.usercenter.dto.UserResponse;
import org.springframework.stereotype.Service;

// （用户Service：处理与用户有关的业务）
@Service
public class UserService {

    // （查询用户业务：目前返回临时数据，以后改成查询MySQL）
    public UserResponse getUserById(Long id) {
        System.out.println("2. Service开始查询用户，id = " + id);

        return new UserResponse(
                id,
                "xiaoming",
                20
        );
    }

    // （新增用户业务：目前返回临时数据，以后改成保存到MySQL）
    public UserResponse createUser(UserCreateRequest request) {
        System.out.println("2. Service开始新增用户：" + request.getUsername());

        return new UserResponse(
                1L,
                request.getUsername(),
                request.getAge()
        );
    }
}
