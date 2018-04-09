package com.cglee079.cointelebot.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimelyInfoVo {
	private String date;
	private String exchange;
	private Double high;
	private Double low;
	private Double last;
	private Double lastBTC;
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

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getLast() {
		return last;
	}

	public void setLast(Double last) {
		this.last = last;
	}

	public Double getLastBTC() {
		return lastBTC;
	}

	public void setLastBTC(Double lastBTC) {
		this.lastBTC = lastBTC;
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
