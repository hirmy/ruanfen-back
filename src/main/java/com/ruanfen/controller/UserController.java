package com.ruanfen.controller;


import com.ruanfen.model.Result;
import com.ruanfen.model.User;
import com.ruanfen.service.UserService;
import com.ruanfen.utils.JwtUtil;
import com.ruanfen.utils.Md5Util;
import com.ruanfen.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(String username, String password){
        userService.register(username, password);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(String username, String password){
        User existUser = userService.findByUsername(username);
        if(existUser == null){
            return Result.error("用户不存在");
        }

        //查询密码正确？
        if(Md5Util.getMD5String(password).equals(existUser.getPassword())){
            //密码正确
            Map<String, Object> claims = new HashMap<>(){
                {
                    put("id", existUser.getUserId());
                    put("userName", existUser.getUserName());
                }
            };
            String token = JwtUtil.genToken(claims);
            return Result.success(token);

        }

        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(){
        Map<String, Object> claim =  ThreadLocalUtil.get();
        String userName = (String) claim.get("userName");
        User nowUser = userService.findByUsername(userName);
        return Result.success(nowUser);
    }

    @GetMapping("/allUser")
    public Result<List<User>> allUsers(){
        List<User> users = userService.list();    //自带
        return Result.success(users);
    }


}
