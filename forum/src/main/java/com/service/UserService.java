package com.service;

import com.dto.User;

import java.util.List;

public interface UserService {
    User findUserById(int id);
    User findUserByEmail(String email);
    int insertUser(User user);
    int updateUserById(User user);
    List<User> findAllUser();
    List<User> findUserBySearch(User user);
    int deleteUserById(int id);
    int deleteManyUser(int items[]);
}
