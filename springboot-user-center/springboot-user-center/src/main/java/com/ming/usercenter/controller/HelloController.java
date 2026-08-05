package com.ming.usercenter.controller;

import com.ming.usercenter.dto.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        return "你好，" + name;
    }

    @GetMapping("/user")
    public UserResponse getUser() {
        return new UserResponse(1L, "xiaoming", 20);
    }


    }
