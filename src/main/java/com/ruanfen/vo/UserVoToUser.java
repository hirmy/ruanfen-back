package com.ruanfen.vo;

import com.ruanfen.model.User;

/**
 * *****************************************************************************
 *
 * @ClassName: UserVoToUser
 * @Description: 请添加对此类的描述
 * @author: chenx
 * @time: 2021/4/7 17:11
 * ****************************************************************************
 */
public class UserVoToUser {

    /**
     * 将表单中的对象转化为数据库中存储的用户对象（剔除表单中的code）
     * @param userVo
     * @return
     */
    public static User toUser(UserVo userVo) {

        //创建一个数据库中存储的对象
        User user = new User();

        //传值
        user.setUserName(userVo.getUsername());
        user.setPassword(userVo.getPassword());
        user.setEmail(userVo.getEmail());

        // 返回包装后的对象
        return user;
    }
}
