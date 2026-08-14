package com.ming.usercenter.service;

import com.ming.usercenter.common.ErrorCode;
import com.ming.usercenter.dto.UserCreateRequest;
import com.ming.usercenter.dto.UserLoginRequest;
import com.ming.usercenter.dto.UserResponse;
import com.ming.usercenter.entity.User;
import com.ming.usercenter.exception.BusinessException;
import com.ming.usercenter.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// （用户Service：处理与用户有关的业务）
@Service
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // （Spring把Mapper和密码加密器交给UserService）
    public UserService(
            UserMapper userMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // （查询用户：通过Mapper查询MySQL，再转换成返回给前端的数据）
    public UserResponse getUserById(Long id) {
        System.out.println("2. Service开始查询MySQL，id = " + id);

        User user = userMapper.findById(id);

        // 没有查到用户时抛出统一业务异常
        if (user == null) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "用户不存在"
            );
        }

        // （只返回id、用户名和年龄，不把密码返回给前端）
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getAge()
        );
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

    // （修改用户状态：先检查status，再交给Mapper修改数据库）
    public void updateUserStatus(Long id, Integer status) {

        // status只能是0或1
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException(
                    ErrorCode.BAD_REQUEST,
                    "状态值只能是0或1"
            );
        }

        // 调用Mapper执行UPDATE语句
        int affectedRows = userMapper.updateStatus(id, status);

        if (affectedRows == 0) {
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "用户不存在"
            );
        }
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
