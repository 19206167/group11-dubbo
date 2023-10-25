package com.example.group11.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel("问题")
public class QuestionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "提问者id")
    private Long questionerId;

    @ApiModelProperty(value = "回答者id")
    private Long respondentId;

    @ApiModelProperty(value = "问题价格")
    private BigDecimal price;

    @ApiModelProperty(value = "问题内容")
    private String questionContent;

    @ApiModelProperty(value = "回答内容")
    private String answerContent;

    @ApiModelProperty(value = "音视频回答url")
    private String answerUrl;

    @ApiModelProperty(value = "答主头像url")
    private String respondentAvatarUrl;

    @ApiModelProperty(value = "喜爱人数")
    private Long upvoteCount;

    @ApiModelProperty(value = "偷听人数")
    private Long eavesdropperCount;

    @ApiModelProperty(value = "当前用户是否点赞")
    private Long upvoted;

    @ApiModelProperty(value = "是否匿名，0-非匿名，1-匿名")
    private Boolean anonymous;
}
