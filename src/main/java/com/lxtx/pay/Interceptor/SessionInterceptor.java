package com.lxtx.pay.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.lxtx.pay.pojo.CpInfo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        // 判断请求路径是否为 /api/register/register
        if (requestURI.contains("/moneylog/export")) {
            // 放行
            return true;
        }

        if (requestURI.contains("/paylog/exportPayLog")) {
            // 放行
            return true;
        }

        if (requestURI.contains("/paylog/exportPayLogHistory")) {
            // 放行
            return true;
        }

        if (requestURI.contains("/withdrawlog/exportWithdrawLog")) {
            // 放行
            return true;
        }

        if (requestURI.contains("/cpinfo/createGoogleSecret")) {
            // 放行
            return true;
        }
        CpInfo cpInfo = (CpInfo) request.getSession().getAttribute("cpInfo");

        if (cpInfo == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 444);
            jsonObject.put("msg", "login error");

            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(jsonObject);
            return false;
        }


        String token = cpInfo.getSessionId();
        String authorization = request.getHeader("Authorization");

        if (!token.equals(authorization)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 444);
            jsonObject.put("msg", "login error");

            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(jsonObject);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
