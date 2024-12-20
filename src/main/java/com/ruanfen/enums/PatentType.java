package com.ruanfen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum PatentType {
    UtilityModel(0, "UtilityModel"),   // 实用新型
    Invention(1, "Invention"),         // 发明
    Design(2, "Design");               // 外观设计

    private final int code;

    @EnumValue
    private final String name;

    PatentType(int code, String name) {
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
