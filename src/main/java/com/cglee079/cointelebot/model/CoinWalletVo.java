package com.cglee079.cointelebot.model;

public class CoinWalletVo {
	private String coinId;
	private String symbol;
	private String usName;
	private String krName;
	private String addr1;
	private String addr2;
	private String enabled;

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

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

}
