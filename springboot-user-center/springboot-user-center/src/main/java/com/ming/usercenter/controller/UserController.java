package com.ming.usercenter.controller;

import com.ming.usercenter.dto.UserCreateRequest;
import com.ming.usercenter.dto.UserResponse;
import com.ming.usercenter.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // （查询用户：接收网址中的id，然后交给UserService）
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable("id") Long id) {
        System.out.println("1. Controller收到查询请求，id = " + id);
        return userService.getUserById(id);
    }

    // （新增用户：接收前端JSON，然后交给UserService）
    @PostMapping
    public UserResponse createUser(@RequestBody UserCreateRequest request) {
        System.out.println("1. Controller收到新增用户请求");
        return userService.createUser(request);
    }
}
