package com.cglee079.cointelebot.model;

public class CoinInfoVo {
	private String coinId;
	private String coinname;
	private String priceEx;
	private String targetEx;
	private String numberEx;
	private String chatAddr;
	private String version;

	public String getCoinId() {
		return coinId;
	}

	public void setCoinId(String coinId) {
		this.coinId = coinId;
	}

	public String getCoinname() {
		return coinname;
	}

	public void setCoinname(String coinname) {
		this.coinname = coinname;
	}

	public String getPriceEx() {
		return priceEx;
	}

	public void setPriceEx(String priceEx) {
		this.priceEx = priceEx;
	}

	public String getTargetEx() {
		return targetEx;
	}

	public void setTargetEx(String targetEx) {
		this.targetEx = targetEx;
	}

	public String getNumberEx() {
		return numberEx;
	}

	public void setNumberEx(String numberEx) {
		this.numberEx = numberEx;
	}

	public String getChatAddr() {
		return chatAddr;
	}

	public void setChatAddr(String chatAddr) {
		this.chatAddr = chatAddr;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
