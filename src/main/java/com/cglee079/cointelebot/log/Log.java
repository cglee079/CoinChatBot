package com.cglee079.cointelebot.log;

import com.cglee079.cointelebot.util.TimeStamper;

public class Log {
	public static synchronized void i(String message) {
		String date = TimeStamper.getDateTime();
		System.out.println(date + "\t" + message);
	}

	public static synchronized void i(StackTraceElement[] stackTrace) {
		String message = "";
		message += "ERROR :\t";
		for (int i = 0; i < stackTrace.length; i++) {
			message += stackTrace[i].toString() + "\t";
		}

		Log.i(message);
	}
}
