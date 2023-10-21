package com.example.group11.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FollowModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long beFollowedUserId;

    private Long FollowingUserId;

    private Boolean deleted;

    private Date createTime;

    private String remark;

}
