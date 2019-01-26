package com.cglee079.coinchatbot.config.id;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Market implements CodeEnum{
	COINONE("101"),
	BITHUMB("102"),
	UPBIT("103"),
	COINNEST("104"),
	KORBIT("105"),
	GOPAX("106"),
	CASHIEREST("107"),
	
	BITFINEX("201"),
	BITTREX("202"),
	POLONIEX("203"),
	BINANCE("204"),
	HUOBI("205"),
	HADAX("206"),
	OKEX("207");
	
	@Getter
	private final String code;

    
	public boolean isKRW() {
		return this.code.startsWith("10");
	}
	
	public boolean isUSD() {
		return this.code.startsWith("20");
	}
}
