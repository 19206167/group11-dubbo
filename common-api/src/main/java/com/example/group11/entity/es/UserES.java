package com.example.group11.entity.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(indexName = "user")
public class UserES implements Serializable {

    @Id
    private String id;

    @Field
    private String loginName;

    @Field
    private String userName;

    @Field
    private String password;

    @Field
    private String salt;

    @Field
    private String email;

    @Field
    private String phone;

    @Field
    private String portrait;

    @Field
    private Integer role;

    @Field
    private BigDecimal earnings;

    @Field
    private BigDecimal balance;

    @Field
    private String description;

    @Field
    private Long minimumAmount;

    @Field
    private String uniqueField;

    @Field
    private String userAudioDescriptionUrl;

    @Field
    private Long followerCount;

    @Field
    private Long answerCount;

    @Field
    private Boolean deleted;

    @Field
    private LocalDateTime createTime;

    @Field
    private LocalDateTime updateTime;

    @Field
    private String remark;

}
