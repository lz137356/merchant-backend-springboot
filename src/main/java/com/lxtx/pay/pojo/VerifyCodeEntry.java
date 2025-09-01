package com.lxtx.pay.pojo;

public class VerifyCodeEntry {

    private final String code;
    private final long expireAt; // 过期时间戳

    public VerifyCodeEntry(String code, long expireAt) {
        this.code = code;
        this.expireAt = expireAt;
    }

    public String getCode() { return code; }
    public long getExpireAt() { return expireAt; }
}
