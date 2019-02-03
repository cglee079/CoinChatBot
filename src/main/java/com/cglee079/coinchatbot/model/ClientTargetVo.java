package com.cglee079.coinchatbot.model;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.config.id.Market;
import com.cglee079.coinchatbot.config.id.TargetFocus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientTargetVo {
	private Coin coinId;
	private String userId;
	private Double price;
	private TargetFocus focus;
	private String createDate;
}
