package com.lxtx.pay.vo;

import lombok.Data;

@Data
public class RechargeVO {

    private Long id;
    private Long appId;
    private String uAmount;
    private Long status;
    private Long faitAmount;
    private Long payLogId;
    private String createTime;
    private String finishTime;
    private String rate;
    private String proof;
}
