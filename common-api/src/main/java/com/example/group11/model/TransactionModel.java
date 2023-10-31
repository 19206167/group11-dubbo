package com.example.group11.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long receiverId;

    private BigDecimal amount;

    private Integer operation;

    private Boolean deleted;

    private LocalDateTime createTime;

    private String remark;
}
