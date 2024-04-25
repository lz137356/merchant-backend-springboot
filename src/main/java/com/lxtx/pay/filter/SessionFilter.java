package com.lxtx.pay.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getContextPath();
        HttpSession session = request.getSession();
        if (!request.getServletPath().endsWith("login.jsp") && null == session.getAttribute("cpInfo")) {
            response.sendRedirect(url + "/login.jsp");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
