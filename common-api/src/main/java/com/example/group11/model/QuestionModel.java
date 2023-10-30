package com.example.group11.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * FileName: QuestionModel.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description question model
 * @Date 2023/10/20 17:45
 */

@Data
public class QuestionModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Long askerId;

    private Long responderId;

    private String content;

    private BigDecimal reward;

    private Boolean paid;

    private String category;

    private Integer answerId;

    private Integer commentNum;

    private Integer likeNum;

    private Boolean deleted;

    private LocalDateTime createTime;

    private String remark;
}
