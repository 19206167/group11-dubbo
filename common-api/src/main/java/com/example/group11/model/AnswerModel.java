package com.example.group11.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AnswerModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Long userId;

    private Integer questionId;

    private String content;

    //    0表示文字回答，1表示语音回答
    private Integer type;

    private List<String> url;

    private Boolean deleted;

    private LocalDateTime createTime;

    private String remark;

    public AnswerModel() {
    }

    public AnswerModel(Integer questionId, String content, Integer type, List<String> url) {
        this.questionId = questionId;
        this.content = content;
        this.type = type;
        this.url = url;
    }

    public AnswerModel(Integer questionId, String content, Integer type) {
        this.questionId = questionId;
        this.content = content;
        this.type = type;
    }
}