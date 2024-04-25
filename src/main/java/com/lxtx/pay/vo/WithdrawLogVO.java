package com.lxtx.pay.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class WithdrawLogVO {

    private long id;
    private Integer appId;
    private String orderId;
    private Integer amount;
    private Integer platformFee;
    private Integer type;
    private String bankCardNo;
    private String bankName;
    private String bankAccountName;
    private String bankCode;
    private String branchCode;
    private String currency;
    private String email;
    private String mobile;
    private Integer status;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String withdrawTime;
    private String ip;
    private Integer syncCnt;
    private String notifyUrl;
    private String documentId;
    private String pixType;
    private String pixKey;
    private String utr;

}
