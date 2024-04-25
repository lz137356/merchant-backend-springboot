package com.lxtx.pay.utils;


import org.apache.log4j.Logger;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CommonUtil {
	private static Logger logger = Logger.getLogger(CommonUtil.class);
	private static final String TOKENKEY = "topchaingame";
	private static final String SECRETKEY = "topchaingamesecretkey";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static void main(String[] args) {

//		long userId = 10000002l;
//		int gameId = 1001;
//
//		String openId = getOpenId(userId, gameId);
//
//		System.out.println("openId:"+openId);
//
//		OpenIdParse result = parseOpenId(openId);
//
//		System.out.println("parse userId : "+result.getUserId());
//		System.out.println("parse gameId : "+result.getGameId());
//
//		System.out.println("accessToken : "+getToken(openId));

//		System.out.println(md5Passwd("111111"));
//		System.out.println(md5UserToken("10000002"));

//		System.out.println(filterIdentityNo("421127198203040071"));

		System.out.println(getDouble(1.2));

//		System.out.println(getUsTime());

//		System.out.println(getRootDomain("baidu-ip.com"));
	}

	public static void llog(HttpServletRequest request) {
		String ip = getRemortIP(request);
		String referer = request.getHeader("Referer");

		logger.info("llog : "+referer+" "+ip);
	}

	public static double getDouble(double d) {
		return getDouble(d, 2);
	}

	public static double getDouble(double d, int p) {
		BigDecimal b = new BigDecimal(d);
		double df = b.setScale(p, BigDecimal.ROUND_HALF_UP).doubleValue();
		return df;
	}

	public static String getDomainByOrderUrl(String orderUrl) {
		logger.info("getDomainByOrderUrl:"+orderUrl);
		if(orderUrl != null && orderUrl.length() > 0) {

			String domain = orderUrl.replaceAll("https://", "").replaceAll("http://", "");

			int pos = domain.indexOf("/");

			if(pos > 0) {
				return domain.substring(0,pos);
			}else {
				return domain;
			}
		}

		return null;
	}

	public static String getDomainByOrderId(String orderId) {

		if(orderId != null && orderId.length() > 0) {
			int pos = orderId.lastIndexOf("-");

			if(pos > 0) {
				String prefix = orderId.substring(0, pos);
				prefix = prefix.replace("/", ".");
				prefix += ".com";

				return prefix;
			}
		}

		return null;
	}

	public static String getRootDomain(String domain) {

		if(domain != null && domain.length() > 0) {

			int pos0 = domain.indexOf(".");
			int pos1 = domain.lastIndexOf(".");

			if(pos0 == pos1) {
				return domain;
			}else {
				return domain.substring(pos0+1);
			}
		}

		return "";
	}

	public static Date getTimeOff(Date date, int dayOff) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, dayOff);
		return cal.getTime();
	}

	public static Date getUsTime() {
		return getUsTime(new Date());
	}

	public static Date getUsTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
//		cal.add(Calendar.HOUR_OF_DAY, -13);
//		cal.add(Calendar.MINUTE, -35);
		cal.add(Calendar.HOUR_OF_DAY, -15);
		cal.add(Calendar.MINUTE, -1);

		return cal.getTime();
	}


	public static Date getUtcTime() {
		return getUtcTime(new Date());
	}

	public static Date getUtcTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
//		cal.add(Calendar.HOUR_OF_DAY, -13);
//		cal.add(Calendar.MINUTE, -35);
		cal.add(Calendar.HOUR_OF_DAY, -8);
		cal.add(Calendar.MINUTE, -1);

		return cal.getTime();
	}

	public static String getOpenId(long userId, int gameId) {

		String prefix = Integer.toHexString(gameId);

		if(prefix.length() < 4) {
			while(prefix.length() < 4) {
				prefix = "0"+prefix;
			}
			prefix = "1"+prefix;
		}else {
			prefix = "2"+prefix;
		}

		String postfix = Long.toHexString(userId);
		while(postfix.length() < 7) {
			postfix = "0"+postfix;
		}

		return prefix + postfix;
	}

	public static String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if(isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){

			int index = ip.indexOf(",");
		 	if(index != -1){
		 		return ip.substring(0,index);
		 	}else{
		 		return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if(isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
			return ip;
		}

		return request.getRemoteAddr();
	}

	private static boolean isNotEmpty(String s){
		return s != null && s.length() > 0;
	}

	public static int getDay(){
		Calendar cal = Calendar.getInstance();

		return getDay(cal);
	}

	public static Date getDayOff(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(Calendar.DAY_OF_MONTH, -day);

		return cal.getTime();
	}

	public static String formatDate(Date date) {
		if(date != null) {
			return sdf.format(date);
		}

		return "";
	}

	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return getDay(cal);
	}

	public static int getDay(Calendar cal){
		return cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH)+1) * 100 + cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int[] getStar(BigDecimal bd){
		int num = bd.intValue();
		int halfNum = (bd.doubleValue()-num) == 0 ? 0 : 1;
		int noneNum = 5 - num;

		return new int[]{num, halfNum, noneNum};
	}

	public static String getTimeOff(Date date){
		long now = System.currentTimeMillis();
		long time = date.getTime();

		int offMinutes = (int)((now - time)/1000/60);//minutes

		if(offMinutes < 60){
			return offMinutes+" minutes ago";
		}else{
			int offHour = offMinutes/60;

			if(offHour < 24){
				return offHour+" hours ago";
			}else{
				int offDay = offHour/24;

				return offDay+" days ago";
			}
		}
	}

	public static String parseDecryptRequest(HttpServletRequest request) {
		try {
			ServletInputStream in = request.getInputStream();

			byte[] body = com.lxtx.pay.utils.InputStreamUtils.toBytes(in);

			String params = com.lxtx.pay.utils.EncryptUtils.decrypt(body);

			return params;
		}catch(Exception e) {
			return null;
		}
	}

	public static String parseRequst(HttpServletRequest request){
        String body = "";
        try {
            ServletInputStream inputStream = request.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while(true){
                String info = br.readLine();
                if(info == null){
                    break;
                }
                if(body == null || "".equals(body)){
                    body = info;
                }else{
                    body += info;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

	public static String defaultString(String s, String defaultVal){
		if(s == null){
			s = defaultVal;
		}
		return s;
	}

	public static String getPhoneCode() {
		StringBuffer sb = new StringBuffer();

		for(int i = 0; i < 5 ; i ++) {
			sb.append(new Random().nextInt(10));
		}

		return sb.toString();
	}

	public static void sleep(int sleepSeconds) {
		try {
			Thread.sleep(1000 * sleepSeconds);
		}catch (Exception e) {

		}
	}

	public static String filterIdentityNo(String identifyNo) {
		if(identifyNo != null && identifyNo.length() > 0) {
			return identifyNo.substring(0, 4) + "**********" + identifyNo.substring(identifyNo.length() - 4, identifyNo.length());
		}
		return "";
	}

	public static String filterBankCard(String bankCard) {
		if(bankCard != null && bankCard.length() > 0) {
			return "**** **** **** " + bankCard.substring(bankCard.length() - 4, bankCard.length());
		}

		return "";
	}

	public static String getServerPath(HttpServletRequest servletRequest) {

		String serverUrl = servletRequest.getScheme()+"://"+servletRequest.getServerName();

		int port = servletRequest.getServerPort();
		if(port != 80 && port != 443) {
			serverUrl += ":"+port;
		}

		serverUrl += servletRequest.getContextPath()+servletRequest.getServletPath();

		int pos = serverUrl.lastIndexOf("/");

		serverUrl = serverUrl.substring(0,pos+1);

		if(serverUrl.indexOf(".com") > 0) {
			serverUrl = serverUrl.replace("http:", "https:");
		}

		return serverUrl;
	}
	public static Date getDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.getTime();
	}

	public static Date getTodayDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.getTime();
	}

	public static Date getYesterdayDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(5, -1);
		return cal.getTime();
	}
}

