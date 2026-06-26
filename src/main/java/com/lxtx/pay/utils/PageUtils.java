package com.lxtx.pay.utils;

public class PageUtils {

    public static int getRowIndex(int pageNum, int pageSize){
        return (pageNum> 0) ? (pageNum- 1) * pageSize : 0;
    }

    /**
     * 验证分页参数是否安全（防止 SQL 注入）
     * @param rowIndex 起始行
     * @param limit 每页大小
     * @return 验证后的安全值
     */
    public static int[] validatePaginationParams(Integer rowIndex, Integer limit) {
        // 默认值
        int safeRowIndex = 0;
        int safeLimit = 10;

        if (rowIndex != null && rowIndex >= 0) {
            safeRowIndex = rowIndex;
        }
        if (limit != null && limit > 0 && limit <= 1000) {
            safeLimit = limit;
        }

        // 限制最大偏移量，防止数据库性能问题
        if (safeRowIndex > 100000) {
            safeRowIndex = 100000;
        }

        return new int[]{safeRowIndex, safeLimit};
    }
}
