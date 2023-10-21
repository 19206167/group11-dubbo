package com.example.group11.vo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class QuestionQueryVO extends QueryVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "提问者id")
    private Long askerId;

    @ApiModelProperty(value = "回答者id")
    private Long respondentId;

    @ApiModelProperty(value = "问题价格")
    private BigDecimal price;

    @ApiModelProperty(value = "问题内容")
    private String questionContent;

    @ApiModelProperty(value = "排序种类")
    private String sort;
}
