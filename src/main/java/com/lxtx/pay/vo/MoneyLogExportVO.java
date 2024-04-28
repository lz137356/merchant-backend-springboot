package com.lxtx.pay.vo;

import com.lxtx.pay.annotations.ExcelHeader;
import lombok.Data;

import java.util.Date;

@Data
public class MoneyLogExportVO {

    @ExcelHeader(value = "Id")
    private Integer id;

    @ExcelHeader(value = "商户id")
    private Integer appId;

    @ExcelHeader(value = "入金/出金")
    private String type;

    @ExcelHeader(value = "交易类型")
    private String sceneInfo;

    @ExcelHeader(value = "交易前金额")
    private Long frontMoney;

    @ExcelHeader(value = "交易金额")
    private Long money;

    @ExcelHeader(value = "交易后金额")
    private Long queenMoney;

    @ExcelHeader(value = "关联单号")
    private String orderId;
    @ExcelHeader(value = "记账时间")
    private String createTime;
}
