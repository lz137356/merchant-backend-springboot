package com.lxtx.pay.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CpInfo {
    private int appId;
    private String key;
    private Integer payFeeFix;
    private BigDecimal payFeeRate;
    private Integer withdrawFeeFix;
    private BigDecimal withdrawFeeRate;
    private int status;
    private Long totalPay;
    private Long totalWithdraw;
    private Long totalPayFee;
    private Long totalWithdrawFee;
    private Long remain;
    private int canover;
    private String UserName;
    private String UserPass;
    private String PhoneNo;
    private String SessionId;
    private Integer AgentId;
    private Long TotalPayRequest;
    private Integer TotalPayNum;
    private Long LastPayRequest;
    private Long LastPay;
    private Long LastPayFee;
    private Integer LastPayNum;
    private Long LastPayRequestMonth;
    private Long LastPayMonth;
    private Long LastPayFeeMonth;
    private Long LastPayNumMonth;
    private Integer YestodayPayNum;
    private BigDecimal ActualRemain;
    private int SubRatio;
    private int RemainDelayDay;
    private String domainName;
    private Long FrozenRemain;
    private String googleSecret;
    private String country;
}
