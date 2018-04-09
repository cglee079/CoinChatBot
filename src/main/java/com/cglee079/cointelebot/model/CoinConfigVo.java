package com.cglee079.cointelebot.model;

public class CoinConfigVo {
	private String coinId;
	private String priceEx;
	private String targetEx;
	private String numberEx;
	private String version;
	private boolean inBTCMarket;
	private boolean enabledCoinone;
	private boolean enabledBithumb;
	private boolean enabledUpbit;
	private boolean enabledCoinnest;
	private boolean enabledKorbit;
	private boolean enabledBitfinex;
	private boolean enabledBittrex;
	private boolean enabledPoloniex;
	private boolean enabledBinance;

	
	@Override
	public String toString() {
		return "CoinConfigVo [coinId=" + coinId + ", priceEx=" + priceEx + ", targetEx=" + targetEx + ", numberEx="
				+ numberEx + ", version=" + version + ", enabledCoinone=" + enabledCoinone + ", enabledBithumb="
				+ enabledBithumb + ", enabledUpbit=" + enabledUpbit + ", enabledCoinnest=" + enabledCoinnest
				+ ", enabledKorbit=" + enabledKorbit + ", enabledBitfinex=" + enabledBitfinex + ", enabledBittrex="
				+ enabledBittrex + ", enabledPoloniex=" + enabledPoloniex + ", enabledBinance=" + enabledBinance + "]";
	}

	public String getCoinId() {
		return coinId;
	}

	public void setCoinId(String coinId) {
		this.coinId = coinId;
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

	public String getVersion() {
		return version;
	}
	
	public boolean isInBTCMarket() {
		return inBTCMarket;
	}

	public void setInBTCMarket(boolean inBTCMarket) {
		this.inBTCMarket = inBTCMarket;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isEnabledCoinone() {
		return enabledCoinone;
	}

	public void setEnabledCoinone(boolean enabledCoinone) {
		this.enabledCoinone = enabledCoinone;
	}

	public boolean isEnabledBithumb() {
		return enabledBithumb;
	}

	public void setEnabledBithumb(boolean enabledBithumb) {
		this.enabledBithumb = enabledBithumb;
	}

	public boolean isEnabledUpbit() {
		return enabledUpbit;
	}

	public void setEnabledUpbit(boolean enabledUpbit) {
		this.enabledUpbit = enabledUpbit;
	}

	public boolean isEnabledCoinnest() {
		return enabledCoinnest;
	}

	public void setEnabledCoinnest(boolean enabledCoinnest) {
		this.enabledCoinnest = enabledCoinnest;
	}

	public boolean isEnabledKorbit() {
		return enabledKorbit;
	}

	public void setEnabledKorbit(boolean enabledKorbit) {
		this.enabledKorbit = enabledKorbit;
	}

	public boolean isEnabledBitfinex() {
		return enabledBitfinex;
	}

	public void setEnabledBitfinex(boolean enabledBitfinex) {
		this.enabledBitfinex = enabledBitfinex;
	}

	public boolean isEnabledBittrex() {
		return enabledBittrex;
	}

	public void setEnabledBittrex(boolean enabledBittrex) {
		this.enabledBittrex = enabledBittrex;
	}

	public boolean isEnabledPoloniex() {
		return enabledPoloniex;
	}

	public void setEnabledPoloniex(boolean enabledPoloniex) {
		this.enabledPoloniex = enabledPoloniex;
	}

	public boolean isEnabledBinance() {
		return enabledBinance;
	}

	public void setEnabledBinance(boolean enabledBinance) {
		this.enabledBinance = enabledBinance;
	}
	
	

}