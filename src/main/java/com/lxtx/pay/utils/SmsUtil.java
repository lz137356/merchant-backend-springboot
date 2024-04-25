package com.lxtx.pay.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

public class SmsUtil {
	private static String url = "http://sms.253.com/msg/";// 应用地址
	private static String un = "N9490139";// 账号 
	private static String pw = "g2pcRAn6x";// 密码
	private static String rd = "1";// 是否需要状态报告，需要1，不需要0
	private static String ex = null;// 扩展码
	
	public static void main(String[] args) {

	}

	public static void msgErr(String msg) {
		msgErr("13811155779", msg);
	}
	
	public static void msgErr(String phone, String msg) {
		
		try {
			batchSend(phone, "【寄售宝】服务监控异常："+msg+"错误，请检查");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String batchSend(String phone, String msg)
			throws Exception {
		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager());
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(url, false);
			method.setURI(new URI(base, "send", false));
			method.setQueryString(new NameValuePair[] { new NameValuePair("un", un), new NameValuePair("pw", pw),
					new NameValuePair("phone", phone), new NameValuePair("rd", rd), new NameValuePair("msg", msg),
					new NameValuePair("ex", ex), });
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				return URLDecoder.decode(baos.toString(), "UTF-8");
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} finally {
			method.releaseConnection();
		}
	}
}
