package com.lingluoyu.service;

import com.lingluoyu.mapper.Model.User;
import com.lingluoyu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUser(Long id) {
        return userMapper.selectById(id);
    }

    public int setUser(User user) {
        return userMapper.insert(user);
    }
}
