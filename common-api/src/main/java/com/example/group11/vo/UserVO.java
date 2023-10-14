package com.example.group11.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("用户详情")
public class UserVO {

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

    @ApiModelProperty(value = "账户余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "总收益")
    private BigDecimal totalRevenue;

    @ApiModelProperty(value = "用户头像url")
    private String userAvatarUrl;

    @ApiModelProperty(value = "用户简介")
    private String userDescription;

    @ApiModelProperty(value = "个人网站")
    private String userWebsite;

    @ApiModelProperty(value = "是否删除, 0-未删除, 1-已删除")
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "备注")
    private String remark;

}
