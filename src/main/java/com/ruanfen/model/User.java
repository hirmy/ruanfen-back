package com.ruanfen.model;

import com.baomidou.mybatisplus.annotation.*;
import com.ruanfen.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    @TableId(value = "user_id", type = IdType.AUTO) // 主键，自增
    private Integer userId;

    @TableField("user_name") // 显式指定字段名
    private String userName;

    private String password;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("date_joined")
    private LocalDateTime dateJoined;

    @TableField("last_login")
    private LocalDateTime lastLogin;

    @TableField("phone_number")
    private String phoneNumber;

    private String email;

    private Role role; // 对应 ENUM，映射为 String 类型

    @TableField("science_id") // 外键关联的列
    private Integer scienceId; // 外键，指向 Researcher 的 researcherId

}
