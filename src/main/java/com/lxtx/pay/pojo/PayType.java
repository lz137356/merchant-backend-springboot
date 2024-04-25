package com.lxtx.pay.pojo;

import java.math.BigInteger;
import java.util.Date;

public class PayType {
    private int id;
    private String name;
    private String params;
    private int status;
    private int feefix;
    private double feerate;
    private long totalfee;
    private int sort;
    private String types;
    private String worktime;
    private  String range;
    private Date createtime;
    private String currency;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFeefix() {
        return feefix;
    }

    public void setFeefix(int feefix) {
        this.feefix = feefix;
    }

    public double getFeerate() {
        return feerate;
    }

    public void setFeerate(double feerate) {
        this.feerate = feerate;
    }

    public long getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(long totalfee) {
        this.totalfee = totalfee;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
