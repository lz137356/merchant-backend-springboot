package com.lxtx.pay.vo;

import lombok.Data;

@Data
public class StaticticsVO {


    private long id;

    private String appid;

    private String orderId;

    private Long orderAmount;

    private Long notifyAmount;


}
