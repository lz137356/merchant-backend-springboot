package com.lxtx.pay.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class InputStreamUtils {

	public static InputStream toInputStream(String str){
		return new ByteArrayInputStream(str.getBytes());
	}
	
	public static InputStream toInputStream(String str,String charset) throws UnsupportedEncodingException{
		return new ByteArrayInputStream(str.getBytes(charset));
	}
	
	public static byte[] toBytes(InputStream is){
		
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	BufferedInputStream bis = null;
    	
    	try {
    		bis = new BufferedInputStream(is);
    		
    		byte[] buf = new byte[1024];
            int readSize = -1;
            
            while ((readSize = bis.read(buf)) != -1) {
            	baos.write(buf, 0, readSize);
            }
			
			return baos.toByteArray();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(baos != null){
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    	
		return null;
	}
	
	public static String toString(InputStream is){
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		StringBuilder sb = new StringBuilder();
		
		String line = null;
		
		try {
			while((line = br.readLine()) != null){
				sb.append(line).append("/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(is != null){
				try{
					is.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		
		return sb.toString();
	}
}
