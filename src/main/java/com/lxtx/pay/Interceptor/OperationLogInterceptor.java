package com.lxtx.pay.Interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.handler.OperationLogHandler;
import com.lxtx.pay.pojo.CpInfo;
import com.lxtx.pay.pojo.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@Component
public class OperationLogInterceptor implements HandlerInterceptor {

    @Autowired
    private OperationLogHandler operationLogHandler;

    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private static final ThreadLocal<OperationLog> operationLogThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        startTime.set(System.currentTimeMillis());

        // 获取用户信息
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        if (cpInfo == null) {
            return true;
        }

        // 创建操作日志
        OperationLog operationLog = new OperationLog();
        operationLog.setAppId(cpInfo.getAppId());
        operationLog.setUserName(cpInfo.getUserName());
        operationLog.setRequestUrl(request.getRequestURI());
        operationLog.setRequestMethod(request.getMethod());
        operationLog.setOperationIp(getClientIp(request));
        operationLog.setUserAgent(request.getHeader("User-Agent"));
        operationLog.setSessionId(request.getSession().getId());
        operationLog.setCreateTime(new Date());

        // 解析模块和操作
        String uri = request.getRequestURI();
        parseModuleAndOperation(uri, operationLog);

        // 记录请求参数（脱敏）
        String params = getRequestParams(request);
        operationLog.setRequestParams(params);

        operationLogThreadLocal.set(operationLog);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        OperationLog operationLog = operationLogThreadLocal.get();
        if (operationLog == null) {
            return;
        }

        try {
            // 计算执行时间
            long executeTime = System.currentTimeMillis() - startTime.get();
            operationLog.setExecuteTime(executeTime);

            // 设置状态
            if (ex != null) {
                operationLog.setStatus(1); // 失败
                operationLog.setMessage(ex.getMessage());
            } else {
                operationLog.setStatus(0); // 成功
                operationLog.setMessage("操作成功");
            }

            // 异步保存日志
            saveOperationLog(operationLog);
        } finally {
            startTime.remove();
            operationLogThreadLocal.remove();
        }
    }

    private void parseModuleAndOperation(String uri, OperationLog operationLog) {
        if (uri.contains("/cpinfo")) {
            operationLog.setModule("商户管理");
            if (uri.contains("login")) operationLog.setOperation("登录");
            else if (uri.contains("logout")) operationLog.setOperation("退出");
            else operationLog.setOperation("商户操作");
        } else if (uri.contains("/paylog")) {
            operationLog.setModule("充值订单");
            operationLog.setOperation("查询");
        } else if (uri.contains("/withdrawlog")) {
            operationLog.setModule("提款订单");
            operationLog.setOperation("查询");
        } else if (uri.contains("/moneylog")) {
            operationLog.setModule("资金流水");
            operationLog.setOperation("查询");
        } else if (uri.contains("/recharge")) {
            operationLog.setModule("充值管理");
            operationLog.setOperation("充值");
        } else {
            operationLog.setModule("其他");
            operationLog.setOperation("操作");
        }
    }

    private String getRequestParams(HttpServletRequest request) {
        try {
            JSONObject params = new JSONObject();
            request.getParameterMap().forEach((k, v) -> {
                // 敏感字段脱敏
                if (isSensitiveField(k)) {
                    params.put(k, "***");
                } else {
                    params.put(k, v.length > 0 ? v[0] : "");
                }
            });
            return params.toJSONString();
        } catch (Exception e) {
            return "";
        }
    }

    private boolean isSensitiveField(String field) {
        String[] sensitiveFields = {"password", "googleCode", "token", "key", "secret", "pwd"};
        String lowerField = field.toLowerCase();
        for (String s : sensitiveFields) {
            if (lowerField.contains(s)) {
                return true;
            }
        }
        return false;
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多个IP取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

    private void saveOperationLog(OperationLog operationLog) {
        try {
            operationLogHandler.add(operationLog);
        } catch (Exception e) {
            log.error("保存操作日志失败", e);
        }
    }
}
