package com.lxtx.pay.utils;

public class PageUtils {

    public static int getRowIndex(int pageNum, int pageSize){
        return (pageNum> 0) ? (pageNum- 1) * pageSize : 0;
    }
}
