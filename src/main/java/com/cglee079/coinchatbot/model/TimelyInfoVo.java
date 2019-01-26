package com.cglee079.coinchatbot.model;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.config.id.Market;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimelyInfoVo {
	private Coin coinId;
	private String date;
	private Market marketId;
	private Double high;
	private Double low;
	private Double last;
	private long volume;
	private String result;
	private String errorCode;
	private String errorMsg;
}
