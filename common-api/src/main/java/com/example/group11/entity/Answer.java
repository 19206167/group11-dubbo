package com.example.group11.entity;

import com.example.group11.commons.utils.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * FileName: Answer.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description answer bean
 * @Date 2023/10/18 21:08
 */

@Entity
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "answer")
public class Answer implements BaseEntity<Integer>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer questionId;

    private String content;

    private Integer type;

    private String url;

    private Boolean deleted;

    private LocalDateTime createTime;

    private String remark;
}
