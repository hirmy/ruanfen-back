package com.ruanfen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum PatentType {
    UtilityModel("UtilityModel"),   // 实用新型
    Invention("Invention"),         // 发明
    Design("Design");               // 外观设计

    @EnumValue // 指定该字段用于映射到数据库
    private final String value;

    PatentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
