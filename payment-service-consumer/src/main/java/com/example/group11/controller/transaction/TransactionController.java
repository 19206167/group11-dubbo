package com.example.group11.controller.transaction;

import com.example.group11.commons.utils.RestResult;
import com.example.group11.vo.TransactionVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
public class TransactionController {

    @GetMapping("transaction/payForQuestion/{userId}/{questionId}")
    @ApiOperation(notes = "问题可以未，已支付和未支付状态", value = "用户为提问某问题付款", tags = "交易")
    public RestResult payForQuestions(@PathVariable String userId, @PathVariable String questionId) {
        return RestResult.ok();
    }

    @GetMapping("transaction/eavesdrop/{userId}/{questionId}")
    @ApiOperation(notes = "问题会变为已购买问题", value = "用户为偷听某问题付款", tags = "交易")
    public RestResult eavesdrop(@PathVariable String userId, @PathVariable String questionId) {
        return RestResult.ok();
    }

    @GetMapping("transaction/topUp/{userId}/{amount}")
    @ApiOperation(value = "用户为账户充值", tags = "交易")
    public RestResult topUpAccount(@PathVariable String userId, @PathVariable double amount) {
        return RestResult.ok();
    }

    @GetMapping("transaction/view/balance/{userId}")
    @ApiOperation(value = "用户查看账户金额明细", tags = "交易")
    public RestResult<List<TransactionVO>> viewAccountBalanceHistory(@PathVariable String userId){
        return RestResult.ok();
    }

    @GetMapping("transaction/view/earnings/{userId}")
    @ApiOperation(value = "用户查看账户收益明细", tags = "交易")
    public RestResult<List<TransactionVO>> viewAccountEarningsHistory(@PathVariable String userId){
        return RestResult.ok();
    }

    @GetMapping("transaction/withdraw/{userId}/{amount}")
    @ApiOperation(value = "用户提现", tags = "交易")
    public RestResult withdraw(@PathVariable String userId, @PathVariable double amount){
        return RestResult.ok();
    }
}
