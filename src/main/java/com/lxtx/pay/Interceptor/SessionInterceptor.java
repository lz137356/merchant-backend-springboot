package com.lxtx.pay.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.config.PlatformProperties;
import com.lxtx.pay.pojo.CpInfo;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 根据配置的 platform.version 提供对应前端的认证行为：
 * - v5 (velopay): 仅 Cookie Session，返回 code 444
 * - v6 (vvpay): Cookie + authorization 头，返回 code 40100
 */
public class SessionInterceptor implements HandlerInterceptor {

    private final PlatformProperties platformProperties;

    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/pay/cpinfo/login",
            "/pay/cpinfo/login_v3",
            "/pay/cpinfo/checkExistGoogle",
            "/pay/cpinfo/generateToken",
            "/pay/cpinfo/createGoogleSecretPublic"
    );

    public SessionInterceptor(PlatformProperties platformProperties) {
        this.platformProperties = platformProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String uri = request.getRequestURI();
        for (String path : PUBLIC_PATHS) {
            if (uri.equals(path) || uri.endsWith(path)) {
                return true;
            }
        }

        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");
        if (cpInfo == null) {
            writeLoginRequired(response);
            return false;
        }

        // v6 (vvpay) 会发送 authorization 头进行双因素校验
        if (platformProperties.isV6()) {
            String authHeader = request.getHeader("authorization");
            if (authHeader != null && !authHeader.isEmpty()) {
                String sessionId = cpInfo.getSessionId();
                if (sessionId != null && !sessionId.equals(authHeader)) {
                    writeLoginRequired(response);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 返回未登录错误，根据 platform.version 配置决定错误码
     */
    void writeLoginRequired(HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        // v5=velopay 使用 444，v6=vvpay 使用 40100
        int code = platformProperties.isV6() ? 40100 : 444;
        jsonObject.put("code", code);
        jsonObject.put("msg", "login error");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(jsonObject);
    }

    static void writeLoginRequired(HttpServletResponse response, int code) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", "login error");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(jsonObject);
    }
}
