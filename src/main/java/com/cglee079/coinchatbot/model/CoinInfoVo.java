package com.cglee079.coinchatbot.model;

import com.cglee079.coinchatbot.config.id.Coin;

import lombok.Data;

@Data
public class CoinInfoVo {
	private Coin coinId;
	private String usName;
	private String krName;
	private String chatAddr;
	private String enabled;
}
