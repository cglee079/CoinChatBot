package com.cglee079.cointelebot.log;

import com.cglee079.cointelebot.util.TimeStamper;

public class Log {
	public static synchronized void i(String message) {
		String date = TimeStamper.getDateTime();
		System.out.println(date + "\t" + message);
	}
}
