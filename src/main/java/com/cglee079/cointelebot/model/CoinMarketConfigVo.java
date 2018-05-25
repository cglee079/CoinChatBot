package com.cglee079.cointelebot.model;

public class CoinMarketConfigVo {
	private String coinId;
	private String market;
	private boolean inBtc;
	private String enabled;

	public String getCoinId() {
		return coinId;
	}

	public void setCoinId(String coinId) {
		this.coinId = coinId;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public boolean isInBtc() {
		return inBtc;
	}

	public void setInBtc(boolean inBtc) {
		this.inBtc = inBtc;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

}