package com.example.group11.enums;

public enum RoleEnum {

    READER(0, "读者"),
    WRITER(1,"作者");

    private final Integer number;
    private final String name;

    RoleEnum(Integer number, String name) {
        this.number = number;
        this.name = name;
    }

    public Integer getKey() {
        return number;
    }

    public String getValue() {
        return name;
    }


}
