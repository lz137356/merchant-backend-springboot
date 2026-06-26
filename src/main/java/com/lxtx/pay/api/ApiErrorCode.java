package com.lxtx.pay.api;

public enum ApiErrorCode {
    // 通用错误
    GENERAL_INTERNAL_ERROR(50000, "服务器内部错误"),
    GENERAL_BAD_REQUEST(40000, "请求参数错误"),
    GENERAL_NOT_FOUND(40400, "资源不存在"),

    // 认证错误 401xx
    AUTH_UNAUTHORIZED(40100, "未登录或登录已过期"),
    AUTH_PASSWORD_INVALID(40101, "密码错误"),
    AUTH_ACCOUNT_DISABLED(40102, "账号已被禁用"),
    AUTH_GOOGLE_CODE_REQUIRED(40103, "需要谷歌验证码"),
    AUTH_GOOGLE_CODE_INVALID(40104, "谷歌验证码错误"),
    AUTH_TOO_MANY_ATTEMPTS(40105, "登录尝试次数过多，请稍后重试"),

    // 权限错误 403xx
    FORBIDDEN(40300, "没有权限访问该资源"),

    // 业务错误
    BUSINESS_ERROR(40001, "业务处理错误");

    private final int code;
    private final String message;

    ApiErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
