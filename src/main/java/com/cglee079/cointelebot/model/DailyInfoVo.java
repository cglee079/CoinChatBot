package com.cglee079.cointelebot.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyInfoVo {
	private String date;
	private String exchange;
	private double high;
	private double low;
	private double last;
	private long volume;
	private double usd;
	private double usd2krw;
	private double kimp;
	private int exchangeRate;
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

	public void setHigh(double d) {
		this.high = d;
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

	public double getUsd() {
		return usd;
	}

	public void setUsd(double usd) {
		this.usd = usd;
	}

	public double getUsd2krw() {
		return usd2krw;
	}

	public void setUsd2krw(double usd2krw) {
		this.usd2krw = usd2krw;
	}

	public double getKimp() {
		return kimp;
	}

	public void setKimp(double kimp) {
		this.kimp = kimp;
	}

	public int getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(int exchangeRate) {
		this.exchangeRate = exchangeRate;
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
