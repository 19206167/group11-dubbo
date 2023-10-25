package com.example.group11.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IndexEnum {

    PRODUCT_INFO("user", "_doc");

    private String index;
    private String type;
}

