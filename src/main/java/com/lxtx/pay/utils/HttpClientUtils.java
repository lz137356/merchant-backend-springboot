package com.lxtx.pay.utils;

import java.net.URL;
import java.security.Security;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;  
import org.apache.commons.httpclient.HttpMethod;  
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;  
import org.apache.commons.httpclient.methods.PostMethod;  
import org.apache.commons.httpclient.protocol.Protocol;  
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;  
import org.apache.commons.lang.StringUtils;  
import org.apache.log4j.Logger;

public class HttpClientUtils {  
	private static Logger logger = Logger.getLogger(HttpClientUtils.class);
    private static final Logger log = Logger.getLogger(HttpClientUtils.class);  
  
	private String url;
	private String method;
	private Cookie[] cookies;
	private String param;
	private NameValuePair[] nameValuePairs;
	private Header[] headers;
	
	private Cookie[] returnCookies;
	
	private AuthScope authScope = null;
	private UsernamePasswordCredentials credentials = null;
	
	public Cookie[] getReturnCookies(){
		return this.returnCookies;
	}
	
	public HttpClientUtils(String url,String method,Header[] headers,Cookie[] cookies,String param,NameValuePair[] nameValuePairs){
		this.url = url;
		this.method = method;
		this.headers = headers;
		this.cookies = cookies;
		this.param = param;
		this.nameValuePairs = nameValuePairs;
	}
	
	public void setProxy(AuthScope authScope, UsernamePasswordCredentials credentials) {
		this.authScope = authScope;
		this.credentials = credentials;
	}
	
    public String send() { 
    	logger.info("send:"+url);
    	
        String body = "";  
        
        HttpClient client = new HttpClient();
        
        if(this.authScope != null) {
    		client.getState().setProxyCredentials(authScope, credentials);
        }
        
        HttpMethod httpMethod = null;  
        if (method.equalsIgnoreCase("post")) {  
            httpMethod = new PostMethod(url); // 输入网址  
        } else {
        	httpMethod = new GetMethod(url); // 输入网址  
        }  
        
        try {  
            if (url.startsWith("https:")) {  
                supportSSL(url, client);  
            }  
            client.getParams().setContentCharset("UTF-8"); // 处理中午字符串  
            
            
            client.getParams().setParameter("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 1.7; .NET CLR 1.1.4322; CIBA; .NET CLR 2.0.50727)");
            
            client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY); 
            client.getParams().setParameter("http.protocol.single-cookie-header", true); 
          
	        if(cookies != null && cookies.length > 0){
	            client.getState().addCookies(cookies);
	        }
        
	        if(param != null && param.length() > 0){
	        	if(httpMethod instanceof GetMethod){
	        		((GetMethod)httpMethod).setQueryString(param);
	        	}else{
	        		((PostMethod)httpMethod).setRequestContentLength(param.length());
	        		((PostMethod)httpMethod).setRequestBody(param);
	        	}
	        }else if(nameValuePairs != null && nameValuePairs.length > 0){
	        	((PostMethod)httpMethod).setRequestBody(nameValuePairs);
	        }
	        
	        if(headers != null && headers.length > 0){
	        	for(Header header : headers){
	        		httpMethod.setRequestHeader(header);
	        	}
	        }
	        
            int code = client.executeMethod(httpMethod);  
           
            Header locationHeader = httpMethod.getResponseHeader("location");
            
            returnCookies = client.getState().getCookies();
            
            if(locationHeader != null){
            	this.url = locationHeader.getValue();
            	
            	this.method = "get";
            	this.cookies = returnCookies;
            	this.param = null;
            	this.nameValuePairs = null;
            	
            	return this.send();
            }else{
            	body = httpMethod.getResponseBodyAsString();  
            }
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return body;  
    }  
    
      
    private static void supportSSL(String url, HttpClient client) {  
        if(StringUtils.isBlank(url)) {  
            return;  
        }  
        String siteUrl = StringUtils.lowerCase(url);  
        if (!(siteUrl.startsWith("https"))) {  
            return;  
        }  
         
        try {  
           setSSLProtocol(siteUrl, client);  
        } catch (Exception e) {  
            log.error("setProtocol error ", e);  
        }  
        Security.setProperty( "ssl.SocketFactory.provider","com.tool.util.DummySSLSocketFactory");  
    }  
      
    private static void setSSLProtocol(String strUrl, HttpClient client) throws Exception {          
        URL url = new URL(strUrl);  
        String host = url.getHost();  
        int port = url.getPort();  
  
        if (port <= 0) {  
            port = 443;  
        }  
        ProtocolSocketFactory factory = new DummySSLSocketFactory();  
        Protocol authhttps = new Protocol("https", factory, port);  
        Protocol.registerProtocol("https", authhttps);  
        client.getHostConfiguration().setHost(host, port, authhttps);  
    }  
}  