package com.cglee079.coinchatbot.model;

import lombok.Data;

@Data
public class CoinConfigVo {
	private String coinId;
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