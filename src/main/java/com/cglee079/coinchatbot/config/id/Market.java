package com.cglee079.coinchatbot.config.id;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Market {
	KR("10"),
	COINONE("101"),
	BITHUMB("102"),
	UPBIT("103"),
	COINNEST("104"),
	KORBIT("105"),
	GOPAX("106"),
	
	US("20"),
	BITFINEX("201"),
	BITTREX("202"),
	POLONIEX("203"),
	BINANCE("204"),
	HUOBI("205"),
	HADAX("206"),
	OKEX("207");
	
	@Getter
	private final String code;
	
	public static Market from(String str) {
		for (Market b : Market.values()) {
			if (b.code.equalsIgnoreCase(str)) {
				return b;
			}
		}
		return null;
	}
	
	
	public static boolean isKR(Market marketId) {
		if(marketId.getCode().startsWith(Market.KR.getCode())) {
			return true;
		}
		return false;
	}
	
	public static boolean isUS(Market marketId) {
		if(marketId.getCode().startsWith(Market.US.getCode())) {
			return true;
		}
		return false;
	}
	
}
