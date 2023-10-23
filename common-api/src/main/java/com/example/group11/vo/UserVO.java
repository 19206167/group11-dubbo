package com.example.group11.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel("用户详情")
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "登录名称")
    private String loginName;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "salt")
    private String salt;

    @ApiModelProperty(value = "电子邮件")
    private String email;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "头像图片路径")
    private String portrait;

    @ApiModelProperty(value = "角色")
    private String role;

    @ApiModelProperty(value = "账户收益")
    private BigDecimal earnings;

    @ApiModelProperty(value = "账户余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "个人描述")
    private String description;

    @ApiModelProperty(value = "接收提问的最低金额")
    private Long minimumAmount;

    @ApiModelProperty(value = "答主领域")
    private String uniqueField;

    @ApiModelProperty(value = "答主语音自我介绍url")
    private String userAudioDescriptionUrl;

    @ApiModelProperty(value = "粉丝人数")
    private Long followerCount;

    @ApiModelProperty(value = "回答数")
    private Long answerCount;

    @ApiModelProperty(value = "是否删除, 0-未删除, 1-已删除")
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

}
