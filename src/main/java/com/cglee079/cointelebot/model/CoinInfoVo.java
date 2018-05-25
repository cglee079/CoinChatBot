package com.cglee079.cointelebot.model;

public class CoinInfoVo {
	private String coinId;
	private String symbol;
	private String usName;
	private String krName;
	private String chatAddr;
	private String enabeld;

	public String getCoinId() {
		return coinId;
	}

	public void setCoinId(String coinId) {
		this.coinId = coinId;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getUsName() {
		return usName;
	}

	public void setUsName(String usName) {
		this.usName = usName;
	}

	public String getKrName() {
		return krName;
	}

	public void setKrName(String krName) {
		this.krName = krName;
	}

	public String getChatAddr() {
		return chatAddr;
	}

	public void setChatAddr(String chatAddr) {
		this.chatAddr = chatAddr;
	}

	public String getEnabeld() {
		return enabeld;
	}

	public void setEnabeld(String enabeld) {
		this.enabeld = enabeld;
	}
}
