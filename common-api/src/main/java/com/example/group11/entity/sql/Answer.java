package com.example.group11.entity.sql;

import com.example.group11.commons.utils.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
// handle json类型数据
@TypeDef(name="json",typeClass = JsonStringType.class)
public class Answer implements BaseEntity<Integer>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long userId;

    private Integer questionId;

    private String content;

//    0表示文字回答，1表示语音回答
    private Integer type;

    @Type(type = "json")
    @Column(name = "url", columnDefinition = "json")
    private List<String> url;

    private Boolean deleted;

    private LocalDateTime createTime;

    private String remark;

    public Answer() {}

    public Answer(Integer questionId, String content, Integer type, List<String> url) {
        this.questionId = questionId;
        this.content = content;
        this.type = type;
        this.url = url;
    }

    public Answer(Integer questionId, String content, Integer type) {
        this.questionId = questionId;
        this.content = content;
        this.type = type;
    }
}