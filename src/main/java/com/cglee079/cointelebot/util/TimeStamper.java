package com.cglee079.cointelebot.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamper {
	public final static SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public synchronized static String getDateTime() {
		return DATETIME_FORMATTER.format(new Date());
	}
}
