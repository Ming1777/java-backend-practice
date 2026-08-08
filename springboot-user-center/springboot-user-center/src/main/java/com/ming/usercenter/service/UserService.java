package com.ming.usercenter.service;

import com.ming.usercenter.dto.UserCreateRequest;
import com.ming.usercenter.dto.UserResponse;
import com.ming.usercenter.entity.User;
import com.ming.usercenter.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

// （用户Service：处理与用户有关的业务）
@Service
public class UserService {

    private final UserMapper userMapper;

    // （构造方法注入：Spring把UserMapper对象交给UserService）
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // （查询用户：通过Mapper查询MySQL，再转换成返回给前端的数据）
    public UserResponse getUserById(Long id) {
        System.out.println("2. Service开始查询MySQL，id = " + id);

        User user = userMapper.findById(id);

        // （暂时处理用户不存在的情况，后面学习统一异常处理时再完善）
        if (user == null) {
            return null;
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
    public boolean updateUserStatus(Long id, Integer status) {

        // status只能是0或1
        if (status == null || (status != 0 && status != 1)) {
            return false;
        }

        // 调用Mapper执行UPDATE语句
        int affectedRows = userMapper.updateStatus(id, status);

        // 修改行数大于0，说明修改成功
        return affectedRows > 0;
    }
    // （新增用户功能目前还是临时数据，之后再改成写入MySQL）
    public UserResponse createUser(UserCreateRequest request) {
        System.out.println("2. Service开始新增用户：" + request.getUsername());

        return new UserResponse(
                1L,
                request.getUsername(),
                request.getAge()
        );
    }
}
