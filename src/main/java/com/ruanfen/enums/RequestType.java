package com.ruanfen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum RequestType {
    CREATE(0, "Claim Portal"), // 认领门户
    UPDATE(1, "Discussion Review"); // 讨论区


    private final int code;

    @EnumValue
    private final String name;

    RequestType(int code, String name) {
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