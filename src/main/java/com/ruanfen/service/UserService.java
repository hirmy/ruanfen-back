package com.ruanfen.service;

import com.ruanfen.pojo.User;

import java.util.List;

public interface UserService {
    void register(String username, String password);

    User findByName(String username);
    List<User> getAllUser();
}
