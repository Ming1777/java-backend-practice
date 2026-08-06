package com.ming.usercenter.mapper;

import com.ming.usercenter.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

// （用户Mapper：负责操作MySQL中的users表）
@Mapper
public interface UserMapper {

    // （根据用户id查询一条用户记录）
    @Select("""
            SELECT id, username, password, age, status, created_at
            FROM users
            WHERE id = #{id}
            """)
    User findById(@Param("id") Long id);
}