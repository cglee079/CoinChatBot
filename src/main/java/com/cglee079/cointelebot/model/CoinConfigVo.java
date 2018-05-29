package com.cglee079.cointelebot.model;

public class CoinConfigVo {
	private String coinId;
	private String priceKREx;
	private String priceUSEx;
	private String targetKREx;
	private String targetUSEx;
	private String targetRateEx;
	private String numberEx;
	private Integer digitKRW;
	private Integer digitUSD;
	private Integer digitBTC;
	private String version;

	public String getCoinId() {
		return coinId;
	}

	public void setCoinId(String coinId) {
		this.coinId = coinId;
	}

	public String getPriceKREx() {
		return priceKREx;
	}

	public void setPriceKREx(String priceKREx) {
		this.priceKREx = priceKREx;
	}

	public String getPriceUSEx() {
		return priceUSEx;
	}

	public void setPriceUSEx(String priceUSEx) {
		this.priceUSEx = priceUSEx;
	}

	public String getTargetKREx() {
		return targetKREx;
	}

	public void setTargetKREx(String targetKREx) {
		this.targetKREx = targetKREx;
	}

	public String getTargetUSEx() {
		return targetUSEx;
	}

	public void setTargetUSEx(String targetUSEx) {
		this.targetUSEx = targetUSEx;
	}

	public String getTargetRateEx() {
		return targetRateEx;
	}

	public void setTargetRateEx(String targetRateEx) {
		this.targetRateEx = targetRateEx;
	}

	public String getNumberEx() {
		return numberEx;
	}

	public void setNumberEx(String numberEx) {
		this.numberEx = numberEx;
	}

	public Integer getDigitKRW() {
		return digitKRW;
	}

	public void setDigitKRW(Integer digitKRW) {
		this.digitKRW = digitKRW;
	}

	public Integer getDigitUSD() {
		return digitUSD;
	}

	public void setDigitUSD(Integer digitUSD) {
		this.digitUSD = digitUSD;
	}

	public Integer getDigitBTC() {
		return digitBTC;
	}

	public void setDigitBTC(Integer digitBTC) {
		this.digitBTC = digitBTC;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}