package com.example.group11.entity;

import com.example.group11.commons.utils.BaseEntity;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * FileName: TransactionHistory.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description Transaction
 * @Date 2023/10/11 21:18
 */

@Data
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long receiverId;

    private BigDecimal amount;

    private Integer operation;

    private Boolean deleted;

    private LocalDateTime createTime;

    private String remark;
}

