package com.cglee079.coinchatbot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimelyInfoVo {
	private String coinId;
	private String date;
	private String marketId;
	private Double high;
	private Double low;
	private Double last;
	private long volume;
	private String result;
	private String errorCode;
	private String errorMsg;
}
