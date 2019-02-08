package com.cglee079.coinchatbot.config.log;

import org.slf4j.LoggerFactory;

public class MyLog {
	public static <T> void e(Class<T> c, String message) {
		LoggerFactory.getLogger(c).error(message);
	}
	
	public static <T> void i(Class<T> c, String message) {
		LoggerFactory.getLogger(c).info(message);
	}
}
