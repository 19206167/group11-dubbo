package com.example.group11.entity;

import com.example.group11.commons.utils.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * FileName: Question.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description Question Bean
 * @Date 2023/10/18 20:52
 */

@Entity
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "question")
public class Question implements BaseEntity<Integer>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long askerId;

    private Long responderId;

    private String content;

    private BigDecimal reward;

    private Boolean paid;

    private Integer answerId;

    private Integer commentNum;

    private Integer likeNum;

    private Boolean deleted;

    private LocalDateTime createTime;

    private String remark;

    public Question(Long askerId, Long responderId, String content, BigDecimal reward, Boolean paid, Integer answerId, Integer commentNum, Integer likeNum) {
        this.askerId = askerId;
        this.responderId = responderId;
        this.content = content;
        this.reward = reward;
        this.paid = paid;
        this.answerId = answerId;
        this.commentNum = commentNum;
        this.likeNum = likeNum;
    }
}
