package com.cglee079.cointelebot.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyInfoVo {
	private String date;
	private String exchange;
	private int high;
	private int low;
	private int last;
	private long volume;
	private double usd;
	private int usd2krw;
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

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
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

	public int getUsd2krw() {
		return usd2krw;
	}

	public void setUsd2krw(int usd2krw) {
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
