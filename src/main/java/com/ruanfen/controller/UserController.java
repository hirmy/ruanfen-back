package com.ruanfen.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruanfen.enums.Role;
import com.ruanfen.model.Portal;
import com.ruanfen.model.Researcher;
import com.ruanfen.model.Result;
import com.ruanfen.model.User;
import com.ruanfen.result.LoginResult;
import com.ruanfen.service.PortalService;
import com.ruanfen.service.ResearcherService;
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
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private PortalService portalService;

    @Autowired
    private ResearcherService researcherService;

    @PostMapping("/sendEmail")
    @ResponseBody
    public Result sendEmail(@RequestParam("email") String email, @RequestParam("code") String code){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        List<User> users = userService.list(wrapper);
        if(!users.isEmpty()){
            return Result.error("邮箱已注册");
        }
        mailService.sendMimeMail(email,code);

        return Result.success();
    }

    @PostMapping("/register")
    public Result register(String username, String password){

        //获取表单中的提交的验证信息
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return Result.error("用户名或密码不能为空！");
        }



        userService.register(username, password);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<LoginResult> login(String username, String password){
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
            return Result.success(new LoginResult(token, existUser));
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

    @PutMapping("/claim")
    public Result claimPortal(int userId, int portalId){
        User user = userService.getById(userId);
        Portal portal = portalService.getById(portalId);
        if(user == null ){
            return Result.error("找不到用户");
        }
        if(portal == null){
            return Result.error("找不到门户");
        }

        int researcherId = portal.getScienceId();

        user.setScienceId(researcherId);
        user.setRole(Role.researcher);
        userService.updateById(user);

        portal.setBelongUserId(userId);
        portal.setIsClaimed(true);
        portal.setClaimedTime(new Date());
        portalService.updateById(portal);

        Researcher researcher = researcherService.getById(researcherId);
        if(researcher == null){
            return Result.error("找不到对应科研人员");
        }
        researcher.setClaimed(true);
        researcherService.updateById(researcher);

        return Result.success();
    }

}
