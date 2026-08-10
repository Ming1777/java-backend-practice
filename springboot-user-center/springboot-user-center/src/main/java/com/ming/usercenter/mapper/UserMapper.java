package com.ming.usercenter.mapper;

import com.ming.usercenter.entity.User;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

// （用户Mapper：负责操作MySQL中的users表）
@Mapper
public interface UserMapper {

    // （根据用户id查询一条用户记录）
    @Select("""
            SELECT id, username, password, age, status, created_at
            FROM users
            WHERE id = #{id}
              AND status = 1
            """)
    User findById(@Param("id") Long id);

    // （查询全部用户）
    @Select("""
            SELECT id, username, password, age, status, created_at
            FROM users
            WHERE status = 1
            ORDER BY id
            """)
    List<User> findAll();

    // （新增用户：把User对象的数据写入MySQL）
    @Insert("""
            INSERT INTO users (username, password, age, status)
            VALUES (#{username}, #{password}, #{age}, #{status})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    // （修改用户状态：根据id把status改成0或1）
    @Update("""
            UPDATE users
            SET status = #{status}
            WHERE id = #{id}
            """)
    int updateStatus(
            @Param("id") Long id,
            @Param("status") Integer status
    );

}
