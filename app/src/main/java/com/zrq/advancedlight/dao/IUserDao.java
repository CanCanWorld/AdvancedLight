package com.zrq.advancedlight.dao;

import com.zrq.advancedlight.entity.User;

import java.util.List;

public interface IUserDao {
    /**
     * 添加用户
     */
    long addUser(User user);

    /**
     * 根据id删除用户
     */
    int deleteUserById(int id);

    /**
     * 根据id更改相应的User条目
     */
    int updateUserById(User user);

    /**
     * 根据id获取用户
     */
    User getUserById(int id);

    /**
     * 获取全部的用户
     */
    List<User> listAllUsers();
}
