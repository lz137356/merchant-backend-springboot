package com.lxtx.pay.pojo;


import lombok.Data;

import java.util.Date;

@Data
public class SettlementLog {

    private long id;

    private String appId;

    private String product;

    private String card;

    private Integer accountType;

    private String bankName;

    private String bankNetName;

    private String accountName;

    private String account;

    private String faitAmount;

    private String uAmount;

    private String proof;

    private long status;

    private Date createTime;

    private Date finishTime;

    private String rate;

    private String comment;

    private String operator;

    private int accountCounter;

}
