package com.lxtx.pay.pojo;

import org.apache.xpath.operations.Bool;

public class CpBank {
    private int Id;
    private Integer AppId;
    private int TxType;
    private Integer BankCardType;
    private String BankCode;

    public void setId(int id) {
        Id = id;
    }

    public void setAppId(Integer appId) {
        AppId = appId;
    }

    private String BankCardNo;
    private String BankCardName;
    private String Mobile;
    private String CreditValid;
    private String CreditCvv;
    private String Email;
    private String Upi;



    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getAppId() {
        return AppId;
    }

    public void setAppId(int appId) {
        AppId = appId;
    }

    public int getTxType() {
        return TxType;
    }

    public void setTxType(int txType) {
        TxType = txType;
    }

    public Integer getBankCardType() {
        return BankCardType;
    }

    public void setBankCardType(Integer bankCardType) {
        BankCardType = bankCardType;
    }

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }

    public String getBankCardNo() {
        return BankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        BankCardNo = bankCardNo;
    }

    public String getBankCardName() {
        return BankCardName;
    }

    public void setBankCardName(String bankCardName) {
        BankCardName = bankCardName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCreditValid() {
        return CreditValid;
    }

    public void setCreditValid(String creditValid) {
        CreditValid = creditValid;
    }

    public String getCreditCvv() {
        return CreditCvv;
    }

    public void setCreditCvv(String creditCvv) {
        CreditCvv = creditCvv;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    @Override
    public String toString() {
        return "CpBank{" +
                "Id=" + Id +
                ", AppId=" + AppId +
                ", TxType=" + TxType +
                ", BankCardType=" + BankCardType +
                ", BankCode='" + BankCode + '\'' +
                ", BankCardNo='" + BankCardNo + '\'' +
                ", BankCardName='" + BankCardName + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", CreditValid='" + CreditValid + '\'' +
                ", CreditCvv='" + CreditCvv + '\'' +
                ", Email='" + Email + '\'' +
                ", Upi='" + Upi + '\'' +
                '}';
    }

    public String getUpi() {
        return Upi;
    }

    public void setUpi(String upi) {
        Upi = upi;
    }
}

