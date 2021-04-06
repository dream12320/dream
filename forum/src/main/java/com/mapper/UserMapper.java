package com.mapper;

import com.dto.User;

import java.util.List;

public interface UserMapper {
    User findUserById(int id);
    User findUserByEmail(String email);
    int insertUser(User user);
    int updateUserById(User user);
    List<User> findAllUser();
    List<User> findUserBySearch(User user);
    int deleteUserById(int id);
    int deleteManyUser(int items[]);
}
