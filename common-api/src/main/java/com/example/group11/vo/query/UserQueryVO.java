package com.example.group11.vo.query;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserQueryVO extends QueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String loginName;

    private String userName;

    private String email;

    private String phone;

}
