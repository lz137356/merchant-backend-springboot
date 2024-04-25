package com.lxtx.pay.vo;

import lombok.Data;

@Data
public class SettlementLogStatisticsVO {

    private Integer processedNum;

    private Long processedFiatAmount;

    private Integer totalAllNum;

    private Long totalFailAmount;

    private Long totalUAmount;

}
