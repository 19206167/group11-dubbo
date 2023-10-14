package com.example.group11.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户成为答主页面")
public class UserToRespondentVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "最低金额")
    private Long minimumAmount;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "领域")
    private String uniqueField;

    @ApiModelProperty(value = "语音自我介绍url")
    private String userAudioDescriptionUrl;
}
