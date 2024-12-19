package com.ruanfen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum Role {

    ADMIN(1, "user"),
    USER(2, "researcher"),
    GUEST(3, "admin");

    private final int code;

    @EnumValue
    private final String name;

    Role(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
