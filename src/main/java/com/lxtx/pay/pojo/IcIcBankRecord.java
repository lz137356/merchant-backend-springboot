package com.lxtx.pay.pojo;

import lombok.Data;

@Data
public class IcIcBankRecord {

    private Long id;

    private String creditAmount;

    private String debitAmount;

    private String valueDate;

    private String transactionDate;

    private String description;

    private String instrumentsId;

    private String accountBalance;

    private String account;

    private Integer syncStatus;

}
