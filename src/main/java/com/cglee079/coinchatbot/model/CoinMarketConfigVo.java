package com.cglee079.coinchatbot.model;

import lombok.Data;

@Data
public class CoinMarketConfigVo {
	private String coinId;
	private String marketId;
	private boolean inBtc;
	private String param;
	private String enabled;
}