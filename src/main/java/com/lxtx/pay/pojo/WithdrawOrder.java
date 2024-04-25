package com.lxtx.pay.pojo;

import java.util.Date;

public class WithdrawOrder {

    private long Id;
    private Integer AppId;
    private String OrderId;
    private Integer Amount;
    private Integer Type;
    private String BankCardNo;
    private String BankCode;
    private String BankAccountName;
    private String BranchCode;
    private String Email;
    private String Mobile;
    private Integer Status;
    private Date CreateTime;
    private Integer ConfirmStatus;
    private String TransactionNum;
    private Date WithdrawTime;
    private Date ConfirmTime;
    private Integer ConfirmUserId;
    private Integer WithdrawTypeId;
    private Integer PlatformFee;
    private Integer ThirdFee;
    private Integer CreateDay;
    private Integer ConfirmDay;
    private Integer WithdrawDay;
    private String NotifyData;
    private String Ip;
    private Integer SyncCnt;
    private Date NextSyncTime;
    private String NotifyUrl;
    private String Currency;

    private String Name;

    public WithdrawOrder(long id, Integer appId, String orderId, Integer amount, Integer type, String bankCardNo, String bankCode, String bankAccountName, String branchCode, String email, String mobile, Integer status, Date createTime, Integer confirmStatus, String transactionNum, Date withdrawTime, Date confirmTime, Integer confirmUserId, Integer withdrawTypeId, Integer platformFee, Integer thirdFee, Integer createDay, Integer confirmDay, Integer withdrawDay, String notifyData, String ip, Integer syncCnt, Date nextSyncTime, String notifyUrl, String currency, String name, Integer count, String username) {
        Id = id;
        AppId = appId;
        OrderId = orderId;
        Amount = amount;
        Type = type;
        BankCardNo = bankCardNo;
        BankCode = bankCode;
        BankAccountName = bankAccountName;
        BranchCode = branchCode;
        Email = email;
        Mobile = mobile;
        Status = status;
        CreateTime = createTime;
        ConfirmStatus = confirmStatus;
        TransactionNum = transactionNum;
        WithdrawTime = withdrawTime;
        ConfirmTime = confirmTime;
        ConfirmUserId = confirmUserId;
        WithdrawTypeId = withdrawTypeId;
        PlatformFee = platformFee;
        ThirdFee = thirdFee;
        CreateDay = createDay;
        ConfirmDay = confirmDay;
        WithdrawDay = withdrawDay;
        NotifyData = notifyData;
        Ip = ip;
        SyncCnt = syncCnt;
        NextSyncTime = nextSyncTime;
        NotifyUrl = notifyUrl;
        Currency = currency;
        Name = name;
        Count = count;
        this.username = username;
    }

    private Integer Count;

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private  String username;

    public WithdrawOrder() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public Integer getAppId() {
        return AppId;
    }

    public void setAppId(Integer appId) {
        AppId = appId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public String getBankCardNo() {
        return BankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        BankCardNo = bankCardNo;
    }

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }

    public String getBankAccountName() {
        return BankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        BankAccountName = bankAccountName;
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }



    public Integer getConfirmStatus() {
        return ConfirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        ConfirmStatus = confirmStatus;
    }

    public String getTransactionNum() {
        return TransactionNum;
    }

    public void setTransactionNum(String transactionNum) {
        TransactionNum = transactionNum;
    }


    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public Date getWithdrawTime() {
        return WithdrawTime;
    }

    public void setWithdrawTime(Date withdrawTime) {
        WithdrawTime = withdrawTime;
    }

    public Date getConfirmTime() {
        return ConfirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        ConfirmTime = confirmTime;
    }

    public Integer getConfirmUserId() {
        return ConfirmUserId;
    }

    public void setConfirmUserId(Integer confirmUserId) {
        ConfirmUserId = confirmUserId;
    }

    public Integer getWithdrawTypeId() {
        return WithdrawTypeId;
    }

    public void setWithdrawTypeId(Integer withdrawTypeId) {
        WithdrawTypeId = withdrawTypeId;
    }

    public Integer getPlatformFee() {
        return PlatformFee;
    }

    public void setPlatformFee(Integer platformFee) {
        PlatformFee = platformFee;
    }

    public Integer getThirdFee() {
        return ThirdFee;
    }

    public void setThirdFee(Integer thirdFee) {
        ThirdFee = thirdFee;
    }

    public Integer getCreateDay() {
        return CreateDay;
    }

    public void setCreateDay(Integer createDay) {
        CreateDay = createDay;
    }

    public Integer getConfirmDay() {
        return ConfirmDay;
    }

    public void setConfirmDay(Integer confirmDay) {
        ConfirmDay = confirmDay;
    }

    public Integer getWithdrawDay() {
        return WithdrawDay;
    }

    public void setWithdrawDay(Integer withdrawDay) {
        WithdrawDay = withdrawDay;
    }

    public String getNotifyData() {
        return NotifyData;
    }

    public void setNotifyData(String notifyData) {
        NotifyData = notifyData;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public Integer getSyncCnt() {
        return SyncCnt;
    }

    public void setSyncCnt(Integer syncCnt) {
        SyncCnt = syncCnt;
    }

    public Date getNextSyncTime() {
        return NextSyncTime;
    }

    public void setNextSyncTime(Date nextSyncTime) {
        NextSyncTime = nextSyncTime;
    }

    public String getNotifyUrl() {
        return NotifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        NotifyUrl = notifyUrl;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }




}
