package com.ruanfen.service.impl;

import com.ruanfen.mapper.UserMapper;
import com.ruanfen.pojo.User;
import com.ruanfen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void register(String username, String password){
        userMapper.add(username, password);
    }

    @Override
    public User findByName(String username){
        return userMapper.findByUserName(username);
    }

    @Override
    public List<User> getAllUser(){
        return userMapper.allUser();
    }
}
