package com.lxtx.pay.dto;

import lombok.Data;

@Data
public class MoneyLogReqDTO {

    private Long id;

    private String appId;

    private String orderId;

    private String type;

    private String sceneInfo;

    private String money;

    private String startCreateTime;

    private String endCreateTime;

    private int page;

    private int limit;

    private int rowIndex;
}
