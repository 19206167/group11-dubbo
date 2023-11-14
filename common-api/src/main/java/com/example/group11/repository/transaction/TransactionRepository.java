package com.example.group11.repository.transaction;

import com.example.group11.commons.utils.BaseRepository;
import com.example.group11.entity.sql.Transaction;
import com.example.group11.model.TransactionModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Table;

/**
 * FileName: TransactionRepository.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 21:27
 */

public interface TransactionRepository extends BaseRepository<Transaction, Long> {
    Transaction findByIdAndDeleted(long transactionId, Boolean deleted);
}
