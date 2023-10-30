package com.example.group11.service.transaction;

import com.example.group11.model.TransactionModel;

/**
 * FileName: PaymentService.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 17:47
 */
public interface TransactionService {
    TransactionModel queryTransactionByQuestionId(long transactionId);
}
