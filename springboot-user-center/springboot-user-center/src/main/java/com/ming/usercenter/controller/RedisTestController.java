package com.ming.usercenter.controller;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis-test")
public class RedisTestController {
    private final StringRedisTemplate stringRedisTemplate;

    public RedisTestController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @PostMapping("/string")
    public String setString(@RequestParam String key,
                            @RequestParam String value) {
        stringRedisTemplate.opsForValue()
                .set(key, value, Duration.ofMinutes(5));

        return "写入成功";
    }

    // 【手敲】根据Key删除Redis数据
    @DeleteMapping("/string")
    public String deleteString(@RequestParam String key) {
        Boolean deleted = stringRedisTemplate.delete(key);

        if (Boolean.TRUE.equals(deleted)) {
            return "删除成功";
        }

        return "Key不存在";
    }

    @GetMapping("/string")
    public String getString(@RequestParam String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
