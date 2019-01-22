package com.cglee079.coinchatbot.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cglee079.coinchatbot.constants.ID;
import com.cglee079.coinchatbot.constants.SET;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientVo {
	private String coinId;
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
