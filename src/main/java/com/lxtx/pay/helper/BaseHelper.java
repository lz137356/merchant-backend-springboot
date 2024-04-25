package com.lxtx.pay.helper;

import com.lxtx.pay.utils.CommonUtil;
import org.springframework.beans.factory.InitializingBean;

public abstract class BaseHelper implements InitializingBean{

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}

	private void init() {
		realInit();
		
		if(sleepSeconds > 0) {
			new Thread() {
				public void run() {
					while(true) {
						try {
							CommonUtil.sleep(sleepSeconds);
							
							realInit();
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
			
		}
	}

	protected abstract void realInit() ;
	
	//ioc
	private int sleepSeconds = 3600*3;

	public void setSleepSeconds(int sleepSeconds) {
		this.sleepSeconds = sleepSeconds;
	}
}
