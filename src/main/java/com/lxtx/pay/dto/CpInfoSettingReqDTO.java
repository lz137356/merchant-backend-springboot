package com.lxtx.pay.dto;

import lombok.Data;

@Data
public class CpInfoSettingReqDTO {

    private String appId;

    private String passwordOri;

    private String passwordNew;

    private String googleSecret;
    private String publicKey;

    
}
