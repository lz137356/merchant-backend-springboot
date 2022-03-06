package com.lxtx.pay.action;

import com.lxtx.pay.handler.AgentHandler;
import com.lxtx.pay.handler.CpInfoHandler;
import com.lxtx.pay.handler.UserHandler;
import com.opensymphony.xwork2.ActionContext;

import javax.servlet.ServletException;
import java.io.IOException;

public class LoginAction extends BaseAction{
    private UserHandler userHandler;
    private CpInfoHandler cpInfoHandler;
    private AgentHandler agentHandler;


    /**
     * 退出
     * @throws ServletException
     * @throws IOException
     */
    public void logout() throws IOException {
        //清理session
        ActionContext.getContext().getSession().remove("cpInfo");
        this.servletResponse.sendRedirect("../login.jsp");
    }

    public UserHandler getUserHandler() {
        return userHandler;
    }

    public void setUserHandler(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    public CpInfoHandler getCpInfoHandler() {
        return cpInfoHandler;
    }

    public void setCpInfoHandler(CpInfoHandler cpInfoHandler) {
        this.cpInfoHandler = cpInfoHandler;
    }

    public AgentHandler getAgentHandler() {
        return agentHandler;
    }

    public void setAgentHandler(AgentHandler agentHandler) {
        this.agentHandler = agentHandler;
    }
}
