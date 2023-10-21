package com.example.group11.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * FileName: TransactionVO.java
 *
 * @author 刘梓健
 * @version 1.0
 * @Description ToDo
 * @Date 2023/10/11 21:23
 */
public class TransactionVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "交易人相关人Id")
    private Long userId;

    @ApiModelProperty(value = "本次交易金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "操作：operation为1表示充值，operation表示购买问题，" +
            "operation为3表示偷听问题，operation为4表示提现，operation为5是收益")
    private Integer operation;

    @ApiModelProperty(value = "收款人id")
    private Long receiverId;

    @ApiModelProperty(value = "是否删除, 0-未删除, 1-已删除")
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
