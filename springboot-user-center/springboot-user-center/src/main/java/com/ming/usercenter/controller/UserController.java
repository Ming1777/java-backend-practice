package com.ming.usercenter.controller;

import com.ming.usercenter.common.ApiResponse;
import com.ming.usercenter.common.ErrorCode;
import com.ming.usercenter.common.SessionConstants;
import com.ming.usercenter.dto.UserCreateRequest;
import com.ming.usercenter.dto.UserLoginRequest;
import com.ming.usercenter.dto.UserResponse;
import com.ming.usercenter.exception.BusinessException;
import com.ming.usercenter.service.UserService;
import jakarta.servlet.http.HttpSession;
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
        return ApiResponse.success(user);
    }

    // （修改用户状态：成功或失败都使用统一格式返回）
    @PatchMapping("/{id}/status")
    public ApiResponse<String> updateUserStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") Integer status
    ) {
        userService.updateUserStatus(id, status);
        return ApiResponse.success("用户状态修改成功");
    }

    // （用户登录：接收用户名和密码，交给Service校验）
    @PostMapping("/login")
    public ApiResponse<UserResponse> login(
            @Valid @RequestBody UserLoginRequest request,
            HttpSession session
    ) {
        UserResponse response = userService.login(request);

        // 登录成功后，把安全的用户信息保存到当前会话
        session.setAttribute(
                SessionConstants.LOGIN_USER,
                response
        );

        return ApiResponse.success(response);
    }

    // （查询当前登录用户：从Session读取登录状态）
    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser(
            HttpSession session
    ) {
        Object loginUser = session.getAttribute(
                SessionConstants.LOGIN_USER
        );

        if (!(loginUser instanceof UserResponse userResponse)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }

        return ApiResponse.success(userResponse);
    }

    // （退出登录：销毁当前Session）
    @PostMapping("/logout")
    public ApiResponse<String> logout(HttpSession session) {
        session.invalidate();
        return ApiResponse.success("退出登录成功");
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
