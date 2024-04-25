package com.lxtx.pay.vo;

import lombok.Data;

@Data
public class PayLogVo {
    private long id;
    private Integer appId;
    private String orderId;
    private Long amount;
    private Integer type;
    private String callbackUrl;
    private String notifyUrl;
    private Integer status;
    private String createTime;
    private String payTime;
    private Integer payTypeId;
    private Integer syncCnt;
    private Integer platformFee;
    private Integer createDay;
    private Integer payDay;
    private String currency;
    private String ip;
    private String validDay;
    private Integer validStatus;
    private String clabe;
    private String rastreo;
}
