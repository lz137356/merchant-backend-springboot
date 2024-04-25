package com.lxtx.pay.vo;

import lombok.Data;

@Data
public class WithdrawLogStaticticsVO {

    private Long totalNeedWithdrawAmount;

    private Long totalWithdrawAmount;

    private Long totalWithdrawFee;

    private Long totalWithdrawNum;

    private Long totalPendingWithdrawAmount;

    private Long totalFailWithdrawAmount;

    private Long allWithdrawNum;
}
