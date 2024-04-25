package com.lxtx.pay.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SettlementLogReqDTO {

    private Long id;

    private String appId;

    private String product;

    private String card;

    private String bankName;

    private String bankNetName;

    private String accountType;

    private String accountName;

    private String account;

    private String faitAmount;

    private Integer status;

    private String startCreateTime;

    private String endCreateTime;

    private String startFinishTime;

    private String endFinishTime;

    private Integer page;

    private Integer limit;

    private Integer rowIndex;

}
