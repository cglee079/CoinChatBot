package com.cglee079.coinchatbot.log;

import com.cglee079.coinchatbot.util.TimeStamper;

public class Log {
	public static synchronized void i(String message) {
		String date = TimeStamper.getDateTime();
		System.out.println(date + "\t" + message);
	}
}
