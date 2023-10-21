package com.example.group11.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("答主")
public class RespondentVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "User表主键")
    private Long UserId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "粉丝人数")
    private Long followerCount;

    @ApiModelProperty(value = "回答数")
    private Long answerCount;

    @ApiModelProperty(value = "答主头像url")
    private String userAvatarUrl;

    @ApiModelProperty(value = "答主音视频简介url")
    private String userAudioDescriptionUrl;

    @ApiModelProperty(value = "答主简介")
    private String userDescription;

    @ApiModelProperty(value = "领域")
    private String uniqueField;

}
