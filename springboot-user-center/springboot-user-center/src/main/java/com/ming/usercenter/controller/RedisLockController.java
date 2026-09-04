package com.ming.usercenter.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/redis-lock")
public class RedisLockController {

    private final RedissonClient redissonClient;

    public RedisLockController(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @GetMapping(value = "/test", produces = "text/plain;charset=UTF-8")
    public String testLock(
            @RequestParam(defaultValue = "A") String requestId) {

        // 不同请求使用同一个名称，竞争同一把锁
        RLock lock = redissonClient.getLock("study:lock:demo");
        boolean acquired = false;

        try {
            // 尝试获取锁；抢不到就返回false，不等待别人释放
            acquired = lock.tryLock(0, TimeUnit.SECONDS);

            if (!acquired) {
                return requestId + "：未获得锁，请稍后再试";
            }

            log.info("获得锁，开始处理，requestId = {}", requestId);

            // 模拟处理任务，耗时10秒
            Thread.sleep(10000);

            return requestId + "：处理完成";

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return requestId + "：请求被中断";

        } finally {
            // 只释放自己获得、且仍由当前线程持有的锁
            if (acquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("已释放锁，requestId = {}", requestId);
            }
        }
    }
}
