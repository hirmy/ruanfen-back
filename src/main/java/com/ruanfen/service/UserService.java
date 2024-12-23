package com.ruanfen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruanfen.mapper.UserMapper;
import com.ruanfen.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService extends IService<User> {

    User findByUsername(String name);
    void register(String name, String password);
}
