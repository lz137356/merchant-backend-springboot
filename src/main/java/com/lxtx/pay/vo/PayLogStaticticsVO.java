package com.lxtx.pay.vo;

import lombok.Data;

@Data
public class PayLogStaticticsVO {

    private Long needPayAmount;

    private Long payAmount;

    private Long payFee;

    private int payNum;

    private int allPayNum;
}
