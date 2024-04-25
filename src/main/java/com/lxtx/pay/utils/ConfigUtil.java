package com.lxtx.pay.utils;

public class ConfigUtil {
	
	public static long convert(long configId) {
		if(configId >= 10) {
			return 10;
		}else {
			return configId;
		}
	}

}
