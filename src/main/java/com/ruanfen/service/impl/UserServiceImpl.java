package com.ruanfen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruanfen.mapper.UserMapper;
import com.ruanfen.model.User;
import com.ruanfen.service.UserService;
import com.ruanfen.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;



    @Override
    public User findByUsername(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", name);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public void register(String name, String password) {
        String securePwd = Md5Util.getMD5String(password);
        userMapper.addUser(name, securePwd);
    }


}
