package com.example.group11.entity.sql;

import com.example.group11.commons.utils.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * FileName: EavedropedQuestion.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description Eavedroped question bean
 * @Date 2023/10/18 21:12
 */
@Entity
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "eavedroped_question")
public class EavedropedQuestion implements BaseEntity<Integer>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long userId;

    private Integer questionId;

    private String content;

    private Integer transactionId;

    private LocalDateTime createTime;

    private String remark;

    private Boolean deleted;
}

