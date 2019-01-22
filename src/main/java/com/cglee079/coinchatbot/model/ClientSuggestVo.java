package com.cglee079.coinchatbot.model;

import com.cglee079.coinchatbot.config.id.Coin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientSuggestVo {
	private int seq;
	private String userId;
	private Coin coinId;
	private String username;
	private String contents;
	private String date;
}
