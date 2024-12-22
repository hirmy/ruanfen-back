package com.ruanfen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruanfen.model.Result;
import com.ruanfen.model.User;
import com.ruanfen.service.UserService;
import com.ruanfen.service.impl.MailServiceImpl;
import com.ruanfen.utils.JwtUtil;
import com.ruanfen.utils.Md5Util;
import com.ruanfen.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MailServiceImpl mailService;

    @PostMapping("/sendEmail")
    @ResponseBody
    public Result sendEmail(@RequestParam("email") String email,@RequestParam("code") String code, HttpSession httpSession){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        List<User> users = userService.list(wrapper);
        if(!users.isEmpty()){
            return Result.error("邮箱已注册");
        }
        mailService.sendMimeMail(email,code, httpSession);

        return Result.success();
    }

    @PostMapping("/register")
    public Result register(String username, String password, String code, HttpSession session){
        String email = (String) session.getAttribute("email");
        String trueCode = (String) session.getAttribute("code");

        //获取表单中的提交的验证信息
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return Result.error("用户名或密码不能为空！");
        }

        if(code == null || code.isEmpty()){
            return Result.error("请填写验证码");
        }

        //如果email数据为空，或者不一致，注册失败
        if (email == null || email.isEmpty()){
            return Result.error("请重新注册。");
        }else if (!code.equals(trueCode)){
            //return "error,请重新注册";
            return Result.error("验证码不正确！");
        }
        userService.register(username, password, email);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(String username, String password){
        User existUser = userService.findByUsername(username);
        if(existUser == null){
            return Result.error("用户不存在");
        }

        //更新lastLogin字段
        existUser.setLastLogin(LocalDateTime.now());
        boolean isUpdated = userService.updateById(existUser);
        if(!isUpdated){
            return Result.error("更新用户状态失败");
        }

        //查询密码正确？
        if(Md5Util.getMD5String(password).equals(existUser.getPassword())){
            //密码正确
            Map<String, Object> claims = new HashMap(){
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

    @DeleteMapping("/remove")
    public Result removeUser(@RequestParam("userId") int userId){
        // 检查用户是否存在
        User user = userService.getById(userId);
        if (user == null) {
            return Result.error("用户不存在，无法删除");
        }

        boolean isRemoved = userService.removeById(userId);
        if (isRemoved) {
            return Result.success("用户删除成功");
        } else {
            return Result.error("用户删除失败，请重试");
        }

    }

    @GetMapping("/userInfo/find")
    public Result<User> finduser(@RequestParam("userId") int userId){
        User user = userService.getById(userId);
        if(user == null){
            return Result.error("用户不存在");
        }

        return Result.success(user);
    }

    @PutMapping("/update")
    public Result updateUser(@RequestBody User user){
        int userId = user.getUserId();
        User existUser = userService.getById(userId);
        if(existUser == null){
            return Result.error("用户不存在");
        }

        user.setUpdateTime(LocalDateTime.now());

        boolean isUpdated = userService.updateById(user);
        if (isUpdated) {
            return Result.success("用户更新成功");
        } else {
            return Result.error("用户更新失败，请重试");
        }
    }

}
