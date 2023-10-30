package com.example.group11.service.transaction.util;

import com.example.group11.commons.utils.BaseServiceImpl;
import com.example.group11.entity.sql.Transaction;
import com.example.group11.model.TransactionModel;
import com.example.group11.service.transaction.TransactionService;
import com.example.group11.repository.transaction.TransactionRepository;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * FileName: TransactionServiceImpl.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 19:27
 */

@DubboService(version="1.0.0", interfaceClass = com.example.group11.service.transaction.TransactionService.class)
public class TransactionServiceImpl extends BaseServiceImpl<TransactionModel, Transaction, Long> implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    protected Class<TransactionModel> getModelType() {
        return TransactionModel.class;
    }

    @Override
    protected Class<Transaction> getEntityType() {
        return Transaction.class;
    }

    @Override
    public TransactionModel queryTransactionByQuestionId(long transactionId){
        Transaction transaction = transactionRepository.findByIdAndDeleted(transactionId, false);
        return mapBean(transaction, TransactionModel.class);
    }
}
