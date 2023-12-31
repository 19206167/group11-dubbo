package com.example.group11.controller.transaction;

import com.example.group11.commons.utils.JWTUtil;
import com.example.group11.commons.utils.RestResult;
import com.example.group11.model.QuestionModel;
import com.example.group11.model.TransactionModel;
import com.example.group11.service.qa.QAService;
import com.example.group11.service.transaction.TransactionService;
import com.example.group11.vo.TransactionVO;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * FileName: TransactionHController.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/11 21:01
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @DubboReference(version = "1.0.0", interfaceClass = com.example.group11.service.transaction.TransactionService.class, timeout = 10000)
    TransactionService transactionService;



    @GetMapping("/topUp")
    @ApiOperation(value = "用户为账户充值", tags = "交易")
    public RestResult topUpAccount(Double amount, HttpServletRequest httpServletRequest) {
        // 获取当前用户id
        String token = JWTUtil.getToken(httpServletRequest);
        Long userId = JWTUtil.getUserId(token);

        BigDecimal amount1 = BigDecimal.valueOf(amount);

        transactionService.topup(userId, amount1);
        return RestResult.ok();
    }
}
