package com.example.group11.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("问题详情")
public class QuestionDetailVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问答")
    private QuestionVO questionVO;

    @ApiModelProperty(value = "答主")
    private RespondentVO respondentVO;

    @ApiModelProperty(value = "推荐问答")
    private List<QuestionVO> questionVOList;

}
