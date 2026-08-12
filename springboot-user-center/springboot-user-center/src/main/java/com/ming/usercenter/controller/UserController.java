package com.ming.usercenter.controller;

import com.ming.usercenter.common.ApiResponse;
import com.ming.usercenter.dto.UserCreateRequest;
import com.ming.usercenter.dto.UserResponse;
import com.ming.usercenter.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// （用户Controller：接收与用户有关的HTTP请求）
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // （构造方法注入：Spring把UserService对象交给UserController）
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // （查询全部用户：使用统一格式返回用户列表）
    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ApiResponse.success(users);
    }

    // （根据ID查询用户：找到返回用户，找不到返回失败信息）
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(
            @PathVariable("id") Long id
    ) {
        UserResponse user = userService.getUserById(id);

        if (user == null) {
            return ApiResponse.fail(404, "用户不存在");
        }

        return ApiResponse.success(user);
    }

    // （修改用户状态：成功或失败都使用统一格式返回）
    @PatchMapping("/{id}/status")
    public ApiResponse<String> updateUserStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") Integer status
    ) {
        boolean success = userService.updateUserStatus(id, status);

        if (!success) {
            return ApiResponse.fail(
                    400,
                    "状态值不合法或用户不存在"
            );
        }

        return ApiResponse.success("用户状态修改成功");
    }

    // （新增用户：先自动校验JSON，再交给Service处理）
    @PostMapping
    public ApiResponse<UserResponse> createUser(
            @Valid @RequestBody UserCreateRequest request
    ) {
        UserResponse response = userService.createUser(request);
        return ApiResponse.success(response);
    }
}
