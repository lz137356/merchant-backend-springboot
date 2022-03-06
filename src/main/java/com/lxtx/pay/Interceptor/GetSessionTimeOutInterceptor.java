package com.lxtx.pay.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author lz
 * @version 1.0
 * @date 2021/8/9 0009 18:48
 */
public class GetSessionTimeOutInterceptor implements HandlerInterceptor {
    private static final String LOGIN_URL="/jsp/sessionrun.jsp";
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session=request.getSession(true);
        //session中获取用户名信息
        Object obj = session.getAttribute("cpInfo");
        if (obj==null||"".equals(obj.toString())) {
            response.sendRedirect("/login.jsp");
            return false;
        }
        return true;
    }
}
