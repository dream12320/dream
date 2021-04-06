package com.serviceImp;

import com.dto.User;
import com.mapper.UserMapper;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    @Override
    public int insertUser(User user) {
        userMapper.insertUser(user);
        return 0;
    }

    @Override
    public int updateUserById(User user) {
        userMapper.updateUserById(user);
        return 0;
    }

    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }

    @Override
    public List<User> findUserBySearch(User user) {
        return userMapper.findUserBySearch(user);
    }

    @Override
    public int deleteUserById(int id) {
        userMapper.deleteUserById(id);
        return 0;
    }

    @Override
    public int deleteManyUser(int[] items) {
        userMapper.deleteManyUser(items);
        return 0;
    }
}
