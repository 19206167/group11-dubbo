package com.example.group11.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private String portrait;

    //    0是读者，1是作者
    private Integer role;

    private BigDecimal earnings;

    private BigDecimal balance;

    private String description;

    private Boolean deleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String remark;


}

