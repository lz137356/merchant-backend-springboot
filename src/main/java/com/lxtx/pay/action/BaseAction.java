package com.lxtx.pay.action;

import com.lxtx.pay.utils.CommonUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public abstract class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware{

	protected HttpServletResponse servletResponse;
	protected HttpServletRequest servletRequest;
	@Override
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}
	@Override
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}
	protected void output(JSONObject result) {
		try {
			servletResponse.setContentType("text/html;charset=utf-8");
			PrintWriter out = servletResponse.getWriter();
			String ret = result.toString();
    		out.print(ret);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void output(String result) {
		try {
			servletResponse.setContentType("text/html;charset=utf-8");
			PrintWriter out = servletResponse.getWriter();
			String ret = result;
    		out.print(ret);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
