package com.example.group11.vo.query;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RespondentQueryVO extends QueryVO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "领域")
    private String uniqueField;

    @ApiModelProperty(value = "排序种类")
    private String sort;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "答主简介")
    private String respondentDescription;


}
