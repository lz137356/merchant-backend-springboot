package com.lxtx.pay.vo;

import lombok.Data;

@Data
public class RechargeStatisticsVO {

    private Integer processedNum;

    private Long processedFiatAmount;

    private Integer totalAllNum;

    private Long totalFailAmount;

    private Long totalUAmount;

}
