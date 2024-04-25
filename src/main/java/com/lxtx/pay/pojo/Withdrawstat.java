package com.lxtx.pay.pojo;

public class Withdrawstat {

    private Integer Id;
    private int WithdrawTypeId;
    private int StatDay;
    private long WithdrawAmount;
    private long ThirdFee;

    public long getWithdrawAmount() {
        return WithdrawAmount;
    }

    public void setWithdrawAmount(long withdrawAmount) {
        WithdrawAmount = withdrawAmount;
    }

    public long getThirdFee() {
        return ThirdFee;
    }

    public void setThirdFee(long thirdFee) {
        ThirdFee = thirdFee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public int getWithdrawTypeId() {
        return WithdrawTypeId;
    }

    public void setWithdrawTypeId(int withdrawTypeId) {
        WithdrawTypeId = withdrawTypeId;
    }

    public int getStatDay() {
        return StatDay;
    }

    public void setStatDay(int statDay) {
        StatDay = statDay;
    }



    public void setThirdFee(Integer thirdFee) {
        ThirdFee = thirdFee;
    }
}
