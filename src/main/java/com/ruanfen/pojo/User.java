package com.ruanfen.pojo;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Data
@Validated
public class User {
    private Integer id;

    @Pattern(regexp = "^\\S{5,16}$")
    private String username;
    @Pattern(regexp = "^\\S{5,16}$")
    private String password;
    private String nickname;
    private String email;
    private String userPic;//头像
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
