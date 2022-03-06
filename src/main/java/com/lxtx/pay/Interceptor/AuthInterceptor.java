package com.lxtx.pay.Interceptor;

import com.lxtx.pay.handler.CpInfoHandler;
import com.lxtx.pay.pojo.CpInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


public class AuthInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = -4116070391170567489L;


    private CpInfoHandler cpInfoHandler;


    public CpInfoHandler getCpInfoHandler() {
        return cpInfoHandler;
    }

    public void setCpInfoHandler(CpInfoHandler cpInfoHandler) {
        this.cpInfoHandler = cpInfoHandler;
    }

    public AuthInterceptor() {


        super();
    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        CpInfo cpInfo = (CpInfo) ActionContext.getContext().getSession().get("cpInfo");
        String method = actionInvocation.getProxy().getMethod();
        if("login".equals(method)){
            return actionInvocation.invoke();
        }
        if(cpInfo!=null){
            CpInfo select = cpInfoHandler.select(cpInfo.getAppId());
            if(cpInfo.getSessionId().equals(select.getSessionId())){
                return actionInvocation.invoke();
            }else{
                ActionContext.getContext().getSession().clear();
                return "login";
            }
        }
        return "login";
    }

    @Override
    public void init() {
        super.init();


    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
