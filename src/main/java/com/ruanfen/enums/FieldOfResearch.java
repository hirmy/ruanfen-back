package com.ruanfen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum FieldOfResearch {
    Physics(1, "physics"),
    Chemistry(2, "chemistry"),
    Biology(3, "biology"),
    Engineering(4, "engineering"),
    Mathematics(5, "mathematics"),
    Medicine(6, "medicine");

    private final int code;

    @EnumValue
    private final String name;

    FieldOfResearch(int code, String name) {
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
