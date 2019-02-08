package com.cglee079.coinchatbot.config.log;

import org.apache.commons.logging.LogFactory;

public class MyLog {
	public static <T> void e(Class<T> c, String message) {
		LogFactory.getLog(c).error(message);
	}
	
	public static <T> void i(Class<T> c, String message) {
		LogFactory.getLog(c).info(message);
	}
}
