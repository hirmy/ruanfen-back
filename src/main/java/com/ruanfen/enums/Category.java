package com.ruanfen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum Category {
    REVIEW_MESSAGE(0, "Review Message"), // 审核消息
    APPLICATION_PROCESSING(1, "Application Processing"); // 申请处理信息

    private final int code;

    @EnumValue
    private final String name;

    Category(int code, String name) {
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