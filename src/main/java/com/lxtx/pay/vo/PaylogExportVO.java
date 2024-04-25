package com.lxtx.pay.vo;

import com.lxtx.pay.annotations.ExcelHeader;
import lombok.Data;

@Data
public class PaylogExportVO {

    @ExcelHeader(value = "平台单号")
    private long id;
    @ExcelHeader(value = "商户id")
    private Integer appId;
    @ExcelHeader(value = "商户订单号")
    private String orderId;
    @ExcelHeader(value = "金额")
    private String amount;
    @ExcelHeader(value = "手续费")
    private String platformFee;
    @ExcelHeader(value = "状态(1成功 0处理中)")
    private String status;
    @ExcelHeader(value = "创建时间")
    private String createTime;
    @ExcelHeader(value = "支付时间")
    private String payTime;
    @ExcelHeader(value = "UTR")
    private String utr;
}
