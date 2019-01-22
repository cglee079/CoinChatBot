package com.cglee079.coinchatbot.coin;

import java.util.HashMap;

import com.cglee079.coinchatbot.config.id.Coin;

public class ApiPooler {
	protected HashMap<Coin, String> coinParam;

	public void setCoinParam(HashMap<Coin, String> coinParam) {
		this.coinParam = coinParam;
	}
	
}
