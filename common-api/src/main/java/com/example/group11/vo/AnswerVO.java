package com.example.group11.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("回答问题")
public class AnswerVO implements Serializable {

    private static final long serialVersionUID = 1L;

    String answerContent;

    List<String> url;
}
