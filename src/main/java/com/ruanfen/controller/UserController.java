package com.ruanfen.controller;


import com.ruanfen.pojo.Result;
import com.ruanfen.pojo.User;
import com.ruanfen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/allUser")
    public Result<List<User>> allUser(){
        List<User> users = userService.getAllUser();
        return Result.success(100, users);
    }

    @PostMapping("/register")
    public Result register(String username, String password){
        User existUser = userService.findByName(username);
        if(existUser != null){
            return Result.error(101, "用户名重复");
        }else {
            userService.register(username, password);
        }
        return Result.success(100);
    }


}
