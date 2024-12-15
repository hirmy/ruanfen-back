package com.ruanfen.enums;

public enum FieldOfResearch {
    Physics("physics"),
    Chemistry("chemistry"),
    Biology("biology"),
    Engineering("engineering"),
    Mathematics("mathematics"),
    Medicine("medicine");

    private final String value;

    FieldOfResearch(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FieldOfResearch fromValue(String value) {
        for (FieldOfResearch field : FieldOfResearch.values()) {
            if (field.value.equalsIgnoreCase(value)) {
                return field;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
