package com.ming.usercenter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.ming.usercenter.controller.RedisLockController;
import com.ming.usercenter.dto.UserResponse;
import com.ming.usercenter.entity.User;
import com.ming.usercenter.exception.BusinessException;
import com.ming.usercenter.exception.GlobalExceptionHandler;
import com.ming.usercenter.mapper.UserMapper;
import com.ming.usercenter.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/** 日志整理的隔离回归：所有数据库、Redis和分布式锁均为模拟对象。 */
class LoggingCleanupTest {
    private static final Long ID = 7L;
    private static final String KEY = "user:detail:7";
    private static final String PRIVATE_VALUE = "test-only-password-must-not-be-logged";
    private final List<LoggerState> loggerStates = new ArrayList<>();
    private ListAppender<ILoggingEvent> logs;
    private UserMapper mapper;
    private StringRedisTemplate redis;
    private ValueOperations<String, String> values;
    private UserService service;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        logs = new ListAppender<>();
        logs.start();
        for (Class<?> type : List.of(UserService.class, GlobalExceptionHandler.class,
                RedisLockController.class)) {
            Logger logger = (Logger) LoggerFactory.getLogger(type);
            loggerStates.add(new LoggerState(logger, logger.getLevel(), logger.isAdditive()));
            logger.setLevel(Level.INFO);
            logger.setAdditive(false);
            logger.addAppender(logs);
        }
        mapper = mock(UserMapper.class);
        redis = mock(StringRedisTemplate.class);
        values = mock(ValueOperations.class);
        when(redis.opsForValue()).thenReturn(values);
        service = new UserService(mapper, mock(PasswordEncoder.class), redis, new ObjectMapper());
    }

    @AfterEach
    void tearDown() {
        Thread.interrupted();
        for (LoggerState state : loggerStates) {
            state.logger().detachAppender(logs);
            state.logger().setLevel(state.level());
            state.logger().setAdditive(state.additive());
        }
        logs.stop();
    }

    @Test
    void cachedUserReturnsWithoutQueryingMysql() {
        when(values.get(KEY)).thenReturn("{\"id\":7,\"username\":\"sample\",\"age\":20}");
        assertEquals(ID, service.getUserById(ID).getId());
        verifyNoInteractions(mapper);
        requireLog(Level.INFO, "Redis缓存命中，直接返回，id = 7", false);
    }

    @Test
    void nullMarkerStillRejectsWithoutQueryingMysql() {
        when(values.get(KEY)).thenReturn("NULL");
        assertThrows(BusinessException.class, () -> service.getUserById(ID));
        verifyNoInteractions(mapper);
        requireLog(Level.INFO, "命中空值缓存，不再查询MySQL，id = 7", false);
    }

    @Test
    void cacheMissQueriesMysqlAndCachesOnlySafeFields() {
        when(mapper.findById(ID)).thenReturn(sampleUser());
        UserResponse response = service.getUserById(ID);
        assertEquals("sample", response.getUsername());
        verify(mapper).findById(ID);
        verify(values).set(eq(KEY), argThat(json -> !json.contains("password")
                && !json.contains(PRIVATE_VALUE)), eq(Duration.ofMinutes(10)));
        requireLog(Level.INFO, "MySQL查询结果已写入Redis，id = 7", false);
        assertTrue(logs.list.stream().noneMatch(e -> e.getFormattedMessage().contains(PRIVATE_VALUE)));
    }

    @Test
    void missingUserCachesShortLivedNullMarker() {
        assertThrows(BusinessException.class, () -> service.getUserById(ID));
        verify(values).set(KEY, "NULL", Duration.ofMinutes(2));
    }

    @Test
    void malformedCacheWarnsWithExceptionThenFallsBackToMysql() {
        when(values.get(KEY)).thenReturn("not-json");
        when(mapper.findById(ID)).thenReturn(sampleUser());
        assertEquals(ID, service.getUserById(ID).getId());
        verify(redis).delete(KEY);
        verify(mapper).findById(ID);
        requireLog(Level.WARN, "Redis缓存JSON格式错误", true);
    }

    @Test
    void serializationFailureWarnsButKeepsMysqlResponse() {
        ObjectMapper brokenJson = mock(ObjectMapper.class);
        when(brokenJson.writeValueAsString(any())).thenThrow(jsonFailure());
        when(mapper.findById(ID)).thenReturn(sampleUser());
        UserService subject = new UserService(mapper, mock(PasswordEncoder.class), redis, brokenJson);
        assertEquals(ID, subject.getUserById(ID).getId());
        verify(values, never()).set(anyString(), anyString(), any(Duration.class));
        requireLog(Level.WARN, "用户数据转换为缓存JSON失败", true);
    }

    @Test
    void updatingStatusStillInvalidatesCacheAndLogsContext() {
        when(mapper.updateStatus(ID, 0)).thenReturn(1);
        service.updateUserStatus(ID, 0);
        verify(mapper).updateStatus(ID, 0);
        verify(redis).delete(KEY);
        requireLog(Level.INFO, "用户状态更新成功，已删除缓存，id = 7, status = 0", false);
    }

    @Test
    void fallbackHandlerLogsThrowableWithoutReturningItsDetails() {
        var response = new GlobalExceptionHandler().handleException(new IllegalStateException("test failure"));
        assertEquals(500, response.getCode());
        assertEquals("服务器内部错误", response.getMessage());
        assertNull(response.getData());
        requireLog(Level.ERROR, "请求处理发生未预期异常", true);
    }

    @Test
    void failedLockAcquisitionNeverUnlocks() throws InterruptedException {
        RedissonClient client = mock(RedissonClient.class);
        RLock lock = mock(RLock.class);
        when(client.getLock("study:lock:demo")).thenReturn(lock);
        when(lock.tryLock(0, TimeUnit.SECONDS)).thenReturn(false);
        assertTrue(new RedisLockController(client).testLock("B").contains("未获得锁"));
        verify(lock, never()).unlock();
    }

    @Test
    void interruptedOwnerReleasesLockAndKeepsInterruptFlag() throws InterruptedException {
        RedissonClient client = mock(RedissonClient.class);
        RLock lock = mock(RLock.class);
        when(client.getLock("study:lock:demo")).thenReturn(lock);
        when(lock.tryLock(0, TimeUnit.SECONDS)).thenReturn(true);
        when(lock.isHeldByCurrentThread()).thenReturn(true);
        // 提前设置中断，让示例中的10秒sleep立即结束，不等待也不访问真实Redis。
        Thread.currentThread().interrupt();
        assertTrue(new RedisLockController(client).testLock("A").contains("请求被中断"));
        assertTrue(Thread.currentThread().isInterrupted());
        verify(lock).unlock();
        requireLog(Level.INFO, "获得锁，开始处理，requestId = A", false);
        requireLog(Level.INFO, "已释放锁，requestId = A", false);
    }

    @Test
    void sqlDebugLoggingIsLimitedToParameterlessListQuery() throws Exception {
        Properties properties = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/application.properties")) {
            assertNotNull(input);
            properties.load(input);
        }
        assertEquals("org.apache.ibatis.logging.slf4j.Slf4jImpl",
                properties.getProperty("mybatis.configuration.log-impl"));
        assertEquals("INFO", properties.getProperty("logging.level.com.ming.usercenter.mapper"));
        assertEquals("DEBUG", properties.getProperty("logging.level.com.ming.usercenter.mapper.UserMapper.findAll"));
        assertNull(properties.getProperty("logging.level.com.ming.usercenter.mapper.UserMapper.insert"));
    }

    private void requireLog(Level level, String messagePart, boolean withThrowable) {
        ILoggingEvent event = logs.list.stream().filter(e -> e.getLevel().equals(level)
                && e.getFormattedMessage().contains(messagePart)).findFirst()
                .orElseThrow(() -> new AssertionError("Missing " + level + " log: " + messagePart));
        if (withThrowable) assertNotNull(event.getThrowableProxy());
    }

    private User sampleUser() {
        User user = new User();
        user.setId(ID);
        user.setUsername("sample");
        user.setPassword(PRIVATE_VALUE);
        user.setAge(20);
        user.setStatus(1);
        return user;
    }

    private JacksonException jsonFailure() {
        try {
            new ObjectMapper().readValue("{", UserResponse.class);
            throw new AssertionError("Invalid JSON unexpectedly parsed");
        } catch (JacksonException expected) {
            return expected;
        }
    }

    private record LoggerState(Logger logger, Level level, boolean additive) {}
}
