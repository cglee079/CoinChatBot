package com.cglee079.coinchatbot.model;

import lombok.Builder;
import lombok.Data;

@Data
public class CoinWalletVo {
	private String coinId;
	private String usName;
	private String krName;
	private String addr1;
	private String addr2;
	private String enabled;
}
