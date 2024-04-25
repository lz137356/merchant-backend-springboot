package com.lxtx.pay.utils;

import java.io.UnsupportedEncodingException;

import com.qlzf.commons.org.AesCrypto;

public class EncryptUtils {

	private static final byte[] password = new byte[] { (byte) 0x67, (byte) 0xE7,
		(byte) 0x81, (byte) 0xAD, (byte) 0x4B, (byte) 0xEC, (byte) 0xE9,
		(byte) 0xFC, (byte) 0xD5, (byte) 0xC7, (byte) 0xDB, (byte) 0x92,
		(byte) 0xE2, (byte) 0x2F, (byte) 0x03, (byte) 0x7D };
	
	public static String decrypt(byte[] data){
		try {
			return new String(AesCrypto.decrypt(data,password),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] encrypt(String data){
		try {
			return AesCrypto.encrypt(data, password);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
