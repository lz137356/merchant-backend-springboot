package com.lxtx.pay.dto;

import lombok.Data;

@Data
public class RechargeReqDTO {

    private Integer appId;

    private Integer status;

    private String startCreateTime;

    private String endCreateTime;

    private String startFinishTime;

    private String endFinishTime;

    private Integer page;

    private Integer limit;

    private Integer rowIndex;
}
