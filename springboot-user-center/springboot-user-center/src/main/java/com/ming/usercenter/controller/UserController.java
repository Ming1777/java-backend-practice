package com.ming.usercenter.controller;

import com.ming.usercenter.dto.UserCreateRequest;
import com.ming.usercenter.dto.UserResponse;
import com.ming.usercenter.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// （用户Controller：接收与用户有关的HTTP请求）
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // （构造方法注入：Spring把UserService对象交给UserController）
    public UserController(UserService userService) {
        this.userService = userService;
    }
    // （查询全部用户：接收GET /users请求）
    @GetMapping
    public List<UserResponse> getAllUsers() {
        System.out.println("1. Controller收到查询全部用户请求");

        return userService.getAllUsers();
    }
    // （查询用户：接收网址中的id，然后交给UserService）
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable("id") Long id) {
        System.out.println("1. Controller收到查询请求，id = " + id);
        return userService.getUserById(id);
    }

    // （修改用户状态：接收PATCH请求，把id和status交给Service）
    @PatchMapping("/{id}/status")
    public boolean updateUserStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") Integer status
    ) {
        return userService.updateUserStatus(id, status);
    }

    // （新增用户：接收前端JSON，然后交给UserService）
    @PostMapping
    public UserResponse createUser(@RequestBody UserCreateRequest request) {
        System.out.println("1. Controller收到新增用户请求");
        return userService.createUser(request);
    }
}
