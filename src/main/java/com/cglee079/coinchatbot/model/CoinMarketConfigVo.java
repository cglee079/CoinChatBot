package com.cglee079.coinchatbot.model;

import com.cglee079.coinchatbot.config.id.Coin;

import lombok.Data;

@Data
public class CoinMarketConfigVo {
	private Coin coinId;
	private String marketId;
	private boolean inBtc;
	private String param;
	private String enabled;
}