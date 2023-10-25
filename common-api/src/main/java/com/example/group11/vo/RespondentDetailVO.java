package com.example.group11.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("答主详情")
public class RespondentDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "答主")
    private UserVO userVO;

    @ApiModelProperty(value = "历史回答")
    private List<QuestionVO> answerHisList;

    @ApiModelProperty(value = "历史提问")
    private List<QuestionVO> questionHisList;

}
