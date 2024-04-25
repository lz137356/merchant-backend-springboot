package com.lxtx.pay.vo;

import lombok.Data;

@Data
public class CpInfoSettingVO {

    private String appId;

    private String key;

    private String googleSecret;

    private String userName;

    private String country;

    private String payFeeRate;

    private String payFeeFix;

    private String withdrawFeeRate;

    private String withdrawFeeFix;
}
