package com.lxtx.pay.action;

import com.lxtx.pay.handler.CpInfoHandler;
import com.lxtx.pay.handler.PaylogHandler;
import com.lxtx.pay.helper.PaylogHelper;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PaylogAction extends BaseAction{

    private PaylogHandler paylogHandler;
    private PaylogHelper paylogHelper;

    private CpInfoHandler cpInfoHandler;

    public CpInfoHandler getCpInfoHandler() {
        return cpInfoHandler;
    }

    public void setCpInfoHandler(CpInfoHandler cpInfoHandler) {
        this.cpInfoHandler = cpInfoHandler;
    }

    public PaylogHelper getPaylogHelper() {
        return paylogHelper;
    }

    public void setPaylogHelper(PaylogHelper paylogHelper) {
        this.paylogHelper = paylogHelper;
    }

    public  void querypaylog() throws IOException {

    }


    public String execute() throws IOException {
        HttpServletResponse response= ServletActionContext.getResponse();
        response.setContentType("text/html");
        return "paylog";
    }
    public PaylogHandler getPaylogHandler() {
        return paylogHandler;
    }
    public void setPaylogHandler(PaylogHandler paylogHandler) {
        this.paylogHandler = paylogHandler;
    }
}
