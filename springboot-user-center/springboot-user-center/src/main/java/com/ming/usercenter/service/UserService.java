package com.ming.usercenter.service;

import com.ming.usercenter.common.ErrorCode;
import com.ming.usercenter.dto.UserCreateRequest;
import com.ming.usercenter.dto.UserLoginRequest;
import com.ming.usercenter.dto.UserResponse;
import com.ming.usercenter.entity.User;
import com.ming.usercenter.exception.BusinessException;
import com.ming.usercenter.mapper.UserMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

// （用户Service：处理与用户有关的业务）
@Service
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    // （Spring把Mapper、密码加密器、Redis工具和JSON工具交给UserService）
    public UserService(
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            StringRedisTemplate stringRedisTemplate,
            ObjectMapper objectMapper
    ) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    // （查询用户：通过Mapper查询MySQL，再转换成返回给前端的数据）
    public UserResponse getUserById(Long id) {
        String cacheKey = "user:detail:" + id;
        String cachedJson = stringRedisTemplate.opsForValue().get(cacheKey);
        System.out.println(
                "1. Service查询Redis，key = "
                        + cacheKey
                        + "，结果 = "
                        + cachedJson
        );
        // Redis查询到了JSON
        if (cachedJson != null) {
            // Redis保存的是空值标记，说明这个用户之前已经查过，确实不存在
            if ("NULL".equals(cachedJson)) {
                System.out.println("2. 命中空值缓存，不再查询MySQL");

                throw new BusinessException(
                        ErrorCode.NOT_FOUND,
                        "用户不存在"
                );
            }

            try {
                // 把JSON字符串还原成UserResponse对象
                UserResponse cachedUser = objectMapper.readValue(
                        cachedJson,
                        UserResponse.class
                );

                System.out.println("2. Redis缓存命中，直接返回");

                // return执行后，方法立即结束，不再查询MySQL
                return cachedUser;
            } catch (JacksonException e) {
                // 如果缓存里的JSON损坏，就删除错误缓存
                stringRedisTemplate.delete(cacheKey);

                System.out.println("Redis缓存格式错误，已删除");
            }
        }
        System.out.println("2. Redis未命中，开始查询MySQL，id = " + id);

        User user = userMapper.findById(id);

        // MySQL没有查到用户
        if (user == null) {
            // 缓存空值2分钟，防止相同的无效请求不断查询MySQL
            stringRedisTemplate.opsForValue().set(
                    cacheKey,
                    "NULL",
                    Duration.ofMinutes(2)
            );

            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "用户不存在"
            );
        }

        // 【手敲】把数据库中的User转换成安全的UserResponse
        UserResponse response = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getAge()
        );

        try {
            // 把UserResponse对象转换成JSON字符串
            String userJson = objectMapper.writeValueAsString(response);

            // 把JSON写入Redis，并设置10分钟过期
            stringRedisTemplate.opsForValue().set(
                    cacheKey,
                    userJson,
                    Duration.ofMinutes(10)
            );

            System.out.println("3. MySQL结果已经写入Redis");
        } catch (JacksonException e) {
            // Redis缓存失败不能影响正常查询
            System.out.println("写入Redis失败，本次仍然返回MySQL结果");
        }

        // 最终把安全的用户数据返回给Controller
        return response;
    }

    // （查询全部用户：查询数据库并隐藏密码）
    public List<UserResponse> getAllUsers() {
        // Mapper从MySQL查询全部用户
        List<User> users = userMapper.findAll();
        // 创建一个准备返回给前端的集合
        List<UserResponse> responses = new ArrayList<>();
        // 把每个User转换成不包含密码的UserResponse
        for (User user : users) {
            UserResponse response = new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getAge()
            );
            responses.add(response);
        }

        return responses;
    }

    // （修改用户状态：先更新MySQL，成功后删除该用户的Redis缓存）
    public void updateUserStatus(Long id, Integer status) {

        // status只能是0或1
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException(
                    ErrorCode.BAD_REQUEST,
                    "状态值只能是0或1"
            );
        }

        // 第一步：修改MySQL中的用户状态
        int affectedRows = userMapper.updateStatus(id, status);

        // 修改了0行，说明用户不存在
        if (affectedRows == 0) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "用户不存在"
            );
        }

        // 【手敲】第二步：MySQL更新成功后，删除该用户的旧缓存
        String cacheKey = "user:detail:" + id;
        stringRedisTemplate.delete(cacheKey);
        System.out.println("MySQL更新成功，已删除缓存：" + cacheKey);
    }
    // （用户登录：根据用户名查询，并校验密码）
    public UserResponse login(UserLoginRequest request) {

        // 根据用户名查询数据库
        User user =
                userMapper.findByUsername(request.getUsername());

        // 没有找到用户
        if (user == null) {
            throw new BusinessException(
                    ErrorCode.BAD_REQUEST,
                    "用户名或密码错误"
            );
        }

        // 账号被禁用
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BusinessException(
                    ErrorCode.FORBIDDEN,
                    "账号已被禁用"
            );
        }

        // 使用BCrypt校验：明文密码是否与数据库哈希匹配
        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new BusinessException(
                    ErrorCode.BAD_REQUEST,
                    "用户名或密码错误"
            );
        }

        // 登录成功，只返回安全字段
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getAge()
        );
    }

    // （新增用户：检查数据、加密密码、写入MySQL）
    public UserResponse createUser(UserCreateRequest request) {
        // 查询用户名是否已经存在
        User existingUser =
                userMapper.findByUsername(request.getUsername());

        if (existingUser != null) {
            throw new BusinessException(
                    ErrorCode.BAD_REQUEST,
                    "用户名已经存在"
            );
        }

        // 创建一个与users表对应的User对象
        User user = new User();

        user.setUsername(request.getUsername());

        // 加密密码，数据库不能保存明文密码
        String encodedPassword =
                passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);

        user.setAge(request.getAge());
        user.setStatus(1);

        // Mapper执行INSERT，并把MySQL生成的id放回user.id
        int affectedRows = userMapper.insert(user);

        if (affectedRows == 0) {
            throw new BusinessException(
                    ErrorCode.SYSTEM_ERROR,
                    "新增用户失败"
            );
        }

        // 返回安全数据，不返回密码
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getAge()
        );
    }
}
