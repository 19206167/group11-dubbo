package com.example.group11.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String loginName;

    private String userName;

    private String password;

    private String salt;

    private String email;

    private String phone;

    private String role;

    private BigDecimal balance;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;

    private String remark;

}

