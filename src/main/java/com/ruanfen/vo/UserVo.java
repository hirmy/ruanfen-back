package com.ruanfen.vo;

/**
 * *****************************************************************************
 *
 * @ClassName: UserVo
 * @Description: 请添加对此类的描述
 * @author: chenx
 * @time: 2021/4/7 17:17
 * ****************************************************************************
 */
public class UserVo {
    private String username;

    private String password;

    private String email;
    //    验证码
    private String code;

    //省略了get和set方法，自己生成一下


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
