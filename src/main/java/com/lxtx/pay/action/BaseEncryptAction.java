package com.lxtx.pay.action;

import com.lxtx.pay.utils.CommonUtil;
import com.lxtx.pay.utils.EncryptUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public abstract class BaseEncryptAction extends ActionSupport implements ServletRequestAware, ServletResponseAware{

	protected HttpServletResponse servletResponse;
	
	protected HttpServletRequest servletRequest;
	
	protected String params;
	
	protected String results;
	
	public String execute() {
		
		params = CommonUtil.parseDecryptRequest(servletRequest);
		
//		getLogger().info("params:"+params);
		
		results = deal();
		
//		getLogger().info("results:"+results);
		
		outputEncrypt(results);
		
		return NONE;
	}
	
	protected abstract Logger getLogger();
	
	protected abstract String deal();
	
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
	
	protected void outputEncrypt(JSONObject result) {
		try {
			byte[] bytes = EncryptUtils.encrypt(result.toString());
			
			servletResponse.setCharacterEncoding("UTF-8");
			servletResponse.getOutputStream().write(bytes);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void outputEncrypt(String result) {
		try {
			byte[] bytes = EncryptUtils.encrypt(result);
			
			servletResponse.setCharacterEncoding("UTF-8");
			servletResponse.getOutputStream().write(bytes);
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
