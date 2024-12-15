package com.ruanfen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum Role {
    admin("admin"),
    user("user"),
    research("research");

    @EnumValue // MyBatis-Plus 将通过这个字段映射到数据库
    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
