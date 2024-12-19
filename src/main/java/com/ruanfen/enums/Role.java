package com.ruanfen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum Role {

    user(0, "user"),
    researcher(1, "researcher"),
    admin(2, "admin");

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
