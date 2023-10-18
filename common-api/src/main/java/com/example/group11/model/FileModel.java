package com.example.group11.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FileModel implements Serializable {

    private Long id;

    private String fileName;

    private String filePath;

    private String fileSize;

    private String fileType;

    private Boolean deleted;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String remark;

}
