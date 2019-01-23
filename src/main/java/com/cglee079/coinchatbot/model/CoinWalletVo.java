package com.cglee079.coinchatbot.model;

import com.cglee079.coinchatbot.config.id.Coin;

import lombok.Data;

@Data
public class CoinWalletVo {
	private Coin coinId;
	private String addr1;
	private String addr2;
	private boolean enabled;
}
