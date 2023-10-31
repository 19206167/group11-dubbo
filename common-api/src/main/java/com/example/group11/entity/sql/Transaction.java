package com.example.group11.entity.sql;

import com.example.group11.commons.utils.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * FileName: TransactionHistory.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description Transaction Bean
 * @Date 2023/10/11 21:18
 */

@Entity
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "transaction")
public class Transaction implements BaseEntity<Long>, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long receiverId;

    private BigDecimal amount;

//    1表示给自己账户充值，2表示购买问题，3表示偷听问题. 4表示收益
    private Integer operation;

    private Boolean deleted;

    private LocalDateTime createTime;

    private String remark;

    public Transaction() {
    }

    public Transaction(Long userId, Long receiverId, BigDecimal amount, Integer operation) {
        this.userId = userId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.operation = operation;
    }
}

