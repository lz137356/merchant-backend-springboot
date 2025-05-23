package com.lxtx.pay.pojo;

import java.time.LocalDate;

public class FailInfo {
    private int count;
    private LocalDate lastFailDate;

    public FailInfo(int count, LocalDate lastFailDate) {
        this.count = count;
        this.lastFailDate = lastFailDate;
    }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public LocalDate getLastFailDate() { return lastFailDate; }
    public void setLastFailDate(LocalDate lastFailDate) { this.lastFailDate = lastFailDate; }
}
