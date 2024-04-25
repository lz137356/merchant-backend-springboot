package com.lxtx.pay.dto;

import lombok.Data;

@Data
public class CpHomeStaticticsReqDTO {

    private String appId;

    private String Country;

    private String startTodayTime;

    private String endTodayTime;

    private String startYesterdayTime;

    private String endYesterdayTime;

}
