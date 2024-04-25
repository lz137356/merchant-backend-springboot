package com.lxtx.pay.pojo;

public class PageHelper {

    private int rowIndex;
    private int limit;


    public PageHelper() {
    }

    public PageHelper(int rowIndex, int limit) {
        this.rowIndex = rowIndex;
        this.limit = limit;
    }


    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
