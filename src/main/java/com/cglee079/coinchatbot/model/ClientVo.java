package com.cglee079.coinchatbot.model;

import com.cglee079.coinchatbot.config.id.Coin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientVo {
	private Coin coinId;
	private String userId;
	private String username;
	private Long localtime;
	private String lang;
	private String state;
	private String marketId;
	private Integer timeloop;
	private Integer dayloop;
	private Double targetUp;
	private Double targetDown;
	private Double invest;
	private Double coinCnt;
	private String enabled;
	private Integer errCnt;
	private String openDate;
	private String reopenDate;
	private String closeDate;
	private String laMsgDate;
}
