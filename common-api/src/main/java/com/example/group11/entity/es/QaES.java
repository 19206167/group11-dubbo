package com.example.group11.entity.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(indexName = "qa")
public class QaES implements Serializable {

    @Id
    private String id;

    @Field
    private Long askerId;

    @Field
    private Long responderId;

    @Field
    private String questionContent;

    @Field
    private BigDecimal reward;

    @Field
    private Boolean paid;

    @Field
    private String answerId;

    @Field
    private Integer commentNum;

    @Field
    private Integer likeNum;

    @Field
    private String answerContent;

    @Field
    private Integer type;

    @Field
    private String qaUrl;

    @Field
    private Boolean deleted;

    @Field
    private LocalDateTime createTime;

    @Field
    private String remark;

}
