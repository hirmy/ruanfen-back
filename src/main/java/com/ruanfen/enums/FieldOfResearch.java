package com.ruanfen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum FieldOfResearch {
    Physics(0, "Physics"),
    Chemistry(1, "Chemistry"),
    Biology(2, "Biology"),
    Engineering(3, "Engineering"),
    Mathematics(4, "Mathematics"),
    Medicine(5, "Medicine");

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
