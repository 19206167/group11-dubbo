package com.example.group11.service.transaction;

import java.math.BigDecimal;

/**
 * FileName: PaymentService.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 17:47
 */
public interface TransactionService {

//    向账户充值
    void topup(Long userId, BigDecimal amount);

//    创建问题支付, 返回transactionId
    Long payForCreateQuestion(Long userId, Long receiverId, BigDecimal price);

//    偷听问题支付, 返回transactionId
    Long payForEavesdropping(Long userId, Long receiverId, BigDecimal price);

//    回答问题，作者获取收益
    void getQuestionReward(Long receiverId, BigDecimal reward);
}
