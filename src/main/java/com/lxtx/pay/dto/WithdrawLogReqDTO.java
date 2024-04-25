package com.lxtx.pay.dto;

import lombok.Data;

@Data
public class WithdrawLogReqDTO {

    private Long id;

    private String appId;

    private String orderId;

    private String status;

    private String bankCardNo;

    private String cpf;

    private String pix_key;

    private String type;

    private Long amount;

    private String syncCnt;

    private String startCreateTime;

    private String endCreateTime;

    private String startWithdrawTime;

    private String endWithdrawTime;

    private int page;

    private int limit;

    private int rowIndex;

}
