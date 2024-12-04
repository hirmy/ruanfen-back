package com.ruanfen.controller;


import com.ruanfen.model.Result;
import com.ruanfen.model.User;
import com.ruanfen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/allUser")
    public Result<List<User>> allUsers(){
        List<User> users = userService.list();    //自带
        return Result.success(users);
    }


}
