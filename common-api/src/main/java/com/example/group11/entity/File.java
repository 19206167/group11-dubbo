package com.example.group11.entity;

import com.example.group11.commons.utils.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "FILE")
public class File implements BaseEntity<Integer>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fileName;

    private String filePath;

    private String fileSize;

    private String fileType;

    private Boolean deleted;

    private LocalDateTime createTime;

    private String remark;

}
