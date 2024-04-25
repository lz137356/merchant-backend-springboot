package com.lxtx.pay.vo;

import lombok.Data;

@Data
public class CpHomeStaticticsVO {


    private Long id;

    private Long todayPayAmount;

    private Long todayPayFee;

    private Integer todayPayNum;

    private Long yesterdayPayAmount;

    private Long yesterdayPayFee;

    private Integer yesterdayPayNum;

    private Long totalPayAmount;

    private Long totalPayFee;

    private Integer totalPayNum;

    private Long todayWithdrawAmount;

    private Long todayWithdrawFee;

    private Integer todayWithdrawNum;

    private Long yesterdayWithdrawAmount;

    private Long yesterdayWithdrawFee;

    private Integer yesterdayWithdrawNum;

    private Long totalWithdrawAmount;

    private Long totalWithdrawFee;

    private Integer totalWithdrawNum;
}
