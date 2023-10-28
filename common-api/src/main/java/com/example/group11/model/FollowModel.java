package com.example.group11.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class FollowModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long beFollowedUserId;

    private Long followingUserId;

    private Boolean deleted;

    private LocalDateTime createTime;

    private String remark;

}
