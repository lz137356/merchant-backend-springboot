package com.lxtx.pay.pojo;

import lombok.Data;

import javax.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;


@Data
public class Paylog implements Serializable {

    private static final long serialVersionUID = 5992388024194947002L;
    @Nullable
    private long Id;
    private Integer AppId;
    private String OrderId;
    private Long Amount;
    private Integer Type;
    private String CallbackUrl;
    private String NotifyUrl;
    private Integer Status;
    private Integer Status1;
    private Date CreateTime;
    private Date PayTime;
    private String TransactionNum;
    private Date NextSyncTime;
    private Integer SyncCnt;
    private Integer PayTypeId;
    private Integer PlatformFee;
    private Integer ThirdFee;
    private Integer CreateDay;
    private Integer PayDay;
    private String NotifyData;
    private String Currency;
    private String Ip;

    private Integer Count;
    private Integer TotalAmount;
    private Integer TotalThirdFee;
    private String clabe;
    private String rastreo;
}
