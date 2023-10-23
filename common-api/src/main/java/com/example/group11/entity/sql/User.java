package com.example.group11.entity.sql;


import com.example.group11.commons.utils.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "USER")
public class User implements BaseEntity<Long>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginName;

    private String userName;

    private String password;

    private String salt;

    private String email;

    private String phone;

    private String portrait;

    private Integer role;

    private BigDecimal earnings;

    private BigDecimal balance;

    private String description;

    private Long minimumAmount;

    private String uniqueField;

    private String userAudioDescriptionUrl;

    private Long followerCount;

    private Long answerCount;

    private Boolean deleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String remark;
}
