package com.lxtx.pay.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SettlementLogVO {

    private long id;

    private String appId;

    private Long faitAmount;

    private String uAmount;

    private String account;

//    private String comment;

//    private String proof;

    private long status;

    private String createTime;

    private String finishTime;

    private String rate;

}
