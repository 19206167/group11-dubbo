package com.example.group11.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("用户成为答主页面")
public class UserToRespondentVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "最低金额")
    private Long minimumAmount;

    @ApiModelProperty(value = "领域")
    private String uniqueField;

    @ApiModelProperty(value = "语音自我介绍url")
    private String userAudioDescriptionUrl;
}
