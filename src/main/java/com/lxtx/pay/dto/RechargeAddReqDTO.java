package com.lxtx.pay.dto;

import lombok.Data;

@Data
public class RechargeAddReqDTO {

    private String uAmount;
    private String faitAmount;
    private String rate;
    private String proof;

}
