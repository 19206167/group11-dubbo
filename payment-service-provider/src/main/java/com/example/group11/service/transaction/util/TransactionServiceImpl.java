package com.example.group11.service.transaction.util;

import com.example.group11.commons.utils.ErrorCode;
import com.example.group11.commons.utils.Group11Exception;
import com.example.group11.entity.sql.Transaction;
import com.example.group11.entity.sql.User;
import com.example.group11.repository.transaction.TransactionRepository;
import com.example.group11.repository.user.UserRepository;
import com.example.group11.commons.utils.BaseServiceImpl;
import com.example.group11.entity.sql.Transaction;
import com.example.group11.model.TransactionModel;
import com.example.group11.service.transaction.TransactionService;
import com.example.group11.repository.transaction.TransactionRepository;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerMethodMappingNamingStrategy;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;


/**
 * FileName: TransactionServiceImpl.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/14 19:27
 */

@DubboService(version="1.0.0", interfaceClass = com.example.group11.service.transaction.TransactionService.class)

public class TransactionServiceImpl implements TransactionService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    protected Class<TransactionModel> getModelType() {
        return TransactionModel.class;
    }

    protected Class<Transaction> getEntityType() {
        return Transaction.class;
    }

    @Override
    @Transactional
//    向账户充值
    public void topup(Long userId, BigDecimal amount) {
//        创建一条transaction信息
        Transaction transaction = new Transaction(userId, userId, amount, 1);
        transactionRepository.save(transaction);
//        增加账户余额中的信息
        Optional<User> user = userRepository.findById(userId);
        user.get().setBalance(user.get().getBalance().add(amount));
        userRepository.save(user.get());
    }


    @Override
    @Transactional
//    为创建问题支付
    public Long payForCreateQuestion(Long userId, Long receiverId, BigDecimal price) {
        // 创建一条transaction信息
        Transaction transaction = new Transaction(userId, receiverId, price, 2);
        transactionRepository.save(transaction);
        // 减少账户余额中的信息
        Optional<User> asker = userRepository.findById(userId);
        BigDecimal account = asker.get().getBalance().subtract(price);
        if (account.compareTo(new BigDecimal(0)) == -1) {
            throw new Group11Exception(ErrorCode.COMMON_ERROR, "账户余额不足，无法提问");
        }
//        提问者扣款
        asker.get().setBalance(account);
        userRepository.save(asker.get());
//        回答者回答后收钱
        return transaction.getId();
    }

    @Override
    @Transactional
//    偷听问题支付
    public Long payForEavesdropping(Long userId, Long receiverId, BigDecimal price) {
        // 创建2条transaction信息, 一个支出信息，一个收益信息
        Transaction transaction = new Transaction(userId, receiverId, price, 3);
        transactionRepository.save(transaction);
        Transaction transaction1 = new Transaction(receiverId, receiverId, price, 4);
        transactionRepository.save(transaction1);
        // 减少账户余额中的信息
        Optional<User> asker = userRepository.findById(userId);
        BigDecimal account = asker.get().getBalance().subtract(price);
        if (account.compareTo(new BigDecimal(0)) == -1) {
            throw new Group11Exception(ErrorCode.COMMON_ERROR, "账户余额不足，无法偷听");
        }
//        提问者扣款，回答者收钱
        asker.get().setBalance(account);
        userRepository.save(asker.get());
        Optional<User> receiver = userRepository.findById(receiverId);
        receiver.get().setBalance(receiver.get().getBalance().add(account));
        userRepository.save(receiver.get());
        return transaction.getId();
    }

    @Override
    public void getQuestionReward(Long receiverId, BigDecimal reward) {
//        第一个userId规定为receiverId目的是方便通过第一个userId查出所有该用户相关的信息
        Transaction transaction = new Transaction(receiverId, receiverId, reward, 4);
        transactionRepository.save(transaction);
//        账户余额增加
        Optional<User> receiver = userRepository.findById(receiverId);
        receiver.get().setBalance(receiver.get().getBalance().add(reward));
        userRepository.save(receiver.get());
    }
}