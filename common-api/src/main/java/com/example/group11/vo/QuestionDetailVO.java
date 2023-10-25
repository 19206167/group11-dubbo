package com.example.group11.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("问题详情")
public class QuestionDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问答")
    private QuestionVO questionVO;

    @ApiModelProperty(value = "答主")
    private UserVO userVO;

    @ApiModelProperty(value = "推荐问答")
    private List<QuestionVO> questionVOList;

}
