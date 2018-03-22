package com.cglee079.cointelebot.log;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cglee079.cointelebot.service.LogService;
import com.cglee079.cointelebot.util.TimeStamper;

@Component
public class Log {

	private static LogService slogService;
	@Autowired
	private LogService logService;

	@PostConstruct
	public void init() {
		Log.slogService = logService;
	}

	public static synchronized void i(String message) {
		String date = TimeStamper.getDateTime();
		System.out.println(date + "\t" + message);
		slogService.insert(date, message);
	}

	public static synchronized void i(StackTraceElement[] stackTrace) {
		String message = "";
		message += "ERROR :  ";
		for (int i = 0; i < stackTrace.length; i++) {
			message += stackTrace[i].toString() + "   ";
		}

		Log.i(message);
	}
}
