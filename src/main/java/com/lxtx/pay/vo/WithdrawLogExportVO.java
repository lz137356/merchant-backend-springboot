package com.lxtx.pay.vo;

import com.lxtx.pay.annotations.ExcelHeader;
import lombok.Data;

@Data
public class WithdrawLogExportVO {

    @ExcelHeader(value = "平台订单号")
    private long id;
    @ExcelHeader(value = "商户id")
    private Integer appId;
    @ExcelHeader(value = "商户订单号")
    private String orderId;
    @ExcelHeader(value = "金额")
    private String amount;
    @ExcelHeader(value = "平台手续费")
    private String platformFee;
    @ExcelHeader(value = "收款账号")
    private String bankCardNo;
//    @ExcelHeader(value = "银行编号")
//    private String bankCode;
    @ExcelHeader(value = "IFSC")
    private String branchCode;
//    @ExcelHeader(value = "姓名")
//    private String bankAccountName;
//    @ExcelHeader(value = "邮箱")
//    private String email;
//    @ExcelHeader(value = "手机号")
//    private String mobile;
//    @ExcelHeader(value = "证件号")
//    private String documentId;
//    @ExcelHeader(value = "pix")
//    private String pixKey;
    @ExcelHeader(value = "状态(2成功 -2失败 其他处理中)")
    private String status;
    @ExcelHeader(value = "创建时间")
    private String createTime;
    @ExcelHeader(value = "提现时间")
    private String withdrawTime;
    @ExcelHeader(value = "UTR")
    private String utr;
}
