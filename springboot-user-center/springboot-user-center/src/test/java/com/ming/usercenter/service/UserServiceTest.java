package com.ming.usercenter.service;
import com.ming.usercenter.common.ErrorCode;
import com.ming.usercenter.exception.BusinessException;
import com.ming.usercenter.dto.UserResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import tools.jackson.databind.ObjectMapper;
import java.time.Duration;
import com.ming.usercenter.mapper.UserMapper;
import com.ming.usercenter.entity.User;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest {
    @Test
    void shouldThrowWhenUserDoesNotExist() {
        // 一、准备模拟依赖，不连接真实 MySQL 或 Redis
        UserMapper mapper = mock(UserMapper.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        StringRedisTemplate redis = mock(StringRedisTemplate.class);
        ValueOperations<String, String> values = mock(ValueOperations.class);
        ObjectMapper objectMapper = new ObjectMapper();

        when(redis.opsForValue()).thenReturn(values);
        when(values.get("user:detail:1")).thenReturn(null);
        // 数据库也查不到用户
        when(mapper.findById(1L)).thenReturn(null);

        UserService service =
                new UserService(mapper, encoder, redis, objectMapper);

        // 二、执行真实 Service，预期它抛出业务异常
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.getUserById(1L)
        );

        // 三、检查异常类型之外的错误码和提示
        assertEquals(ErrorCode.NOT_FOUND.getCode(), exception.getCode());
        assertEquals("用户不存在", exception.getMessage());
        verify(mapper).findById(1L);

        // 写入的是字符串 "NULL" 标记，过期时间为 2 分钟
        verify(values).set(
                "user:detail:1",
                "NULL",
                Duration.ofMinutes(2)
        );
    }

    @Test
    void shouldReturnUserWhenCacheMissAndUserExists() {
        // 一、准备依赖
        UserMapper mapper = mock(UserMapper.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        StringRedisTemplate redis = mock(StringRedisTemplate.class);
        ValueOperations<String, String> values = mock(ValueOperations.class);

        // JSON 转换使用真实工具
        ObjectMapper objectMapper = new ObjectMapper();

        // 让 redis.opsForValue() 返回模拟的字符串操作对象
        when(redis.opsForValue()).thenReturn(values);

        // 模拟 Redis 没有缓存
        when(values.get("user:detail:1")).thenReturn(null);

        // 模拟数据库查到了用户
        User user = new User();
        user.setId(1L);
        user.setUsername("xiaoming");
        user.setAge(20);
        when(mapper.findById(1L)).thenReturn(user);

        // 二、创建真实的 Service，执行真实方法
        UserService service =
                new UserService(mapper, encoder, redis, objectMapper);

        UserResponse result = service.getUserById(1L);

        // 三、检查返回数据
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals("xiaoming", result.getUsername());
        assertEquals(Integer.valueOf(20), result.getAge());

        // 检查确实查询了 Mapper
        verify(mapper).findById(1L);

        // 检查把正确的 JSON 写入缓存，过期时间为 10 分钟
        verify(values).set(
                "user:detail:1",
                objectMapper.writeValueAsString(result),
                Duration.ofMinutes(10)
        );
    }

    @Test
    void firstTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    void mockMapperTest() {
        // 1. 创建模拟的 Mapper，不连接 MySQL
        UserMapper mapper = mock(UserMapper.class);

        // 2. 准备一名测试用户，仅存在于内存中
        User user = new User();
        user.setId(1L);
        user.setUsername("xiaoming");

        // 3. 设定规则：查询 ID 为 1 的用户时，返回上面的对象
        when(mapper.findById(1L)).thenReturn(user);

        // 4. 调用模拟对象，并检查结果
        User result = mapper.findById(1L);
        assertEquals("xiaoming", result.getUsername());
    }

}
