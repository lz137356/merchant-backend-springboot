package com.lxtx.pay.dto;

import lombok.Data;

@Data
public class PayLogReqDTO {

    private Long id;

    private String appId;

    private String orderId;

    private String type;

    private Long amount;

    private String status;

    private String syncCnt;

    private String description;

    private String payDay;

    private String validDay;

    private String clabe;

    private String rastreo;

    private String pix_key;

    private String document_id;

    private String startCreateTime;

    private String endCreateTime;

    private String startPayTime;

    private String endPayTime;

    private int page;

    private int limit;

    private int rowIndex;

}
