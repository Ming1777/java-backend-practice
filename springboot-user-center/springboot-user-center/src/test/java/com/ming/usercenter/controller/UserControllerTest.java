package com.ming.usercenter.controller;

import com.ming.usercenter.dto.UserResponse;
import com.ming.usercenter.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Test
    void shouldReturnUserList() throws Exception {
        // 1. 模拟 Service 返回一名用户
        UserService service = mock(UserService.class);
        when(service.getAllUsers()).thenReturn(
                List.of(new UserResponse(1L, "xiaoming", 20))
        );

        // 2. 把真实 Controller 放进模拟的 MVC 测试环境
        UserController controller = new UserController(service);
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        // 3. 模拟 HTTP 请求，检查响应
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].username").value("xiaoming"));

        // 4. 确认 Controller 调用了 Service
        verify(service).getAllUsers();
    }
}