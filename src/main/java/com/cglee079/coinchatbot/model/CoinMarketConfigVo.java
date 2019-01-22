package com.cglee079.coinchatbot.model;

public class CoinMarketConfigVo {
	private String market;
	private String coinId;
	private boolean inBtc;
	private String param;
	private String enabled;

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getCoinId() {
		return coinId;
	}

	public void setCoinId(String coinId) {
		this.coinId = coinId;
	}

	public boolean isInBtc() {
		return inBtc;
	}

	public void setInBtc(boolean inBtc) {
		this.inBtc = inBtc;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

}