package com.cglee079.cointelebot.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimelyInfoVo {
	private String date;
	private String exchange;
	private double high;
	private double low;
	private double last;
	private long volume;
	private String result;
	private String errorCode;
	private String errorMsg;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setDate(Date date) {
		this.date = new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getLast() {
		return last;
	}

	public void setLast(double last) {
		this.last = last;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long d) {
		this.volume = d;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
