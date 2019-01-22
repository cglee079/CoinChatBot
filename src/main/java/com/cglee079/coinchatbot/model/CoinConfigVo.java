package com.cglee079.coinchatbot.model;

import com.cglee079.coinchatbot.config.id.Coin;

import lombok.Data;

@Data
public class CoinConfigVo {
	private Coin coinId;
	private String exInvestKRW;
	private String exInvestUSD;
	private String exTargetKRW;
	private String exTargetUSD;
	private String exTargetRate;
	private String exCoinCnt;
	private Integer digitKRW;
	private Integer digitUSD;
	private Integer digitBTC;
	private String version;
}