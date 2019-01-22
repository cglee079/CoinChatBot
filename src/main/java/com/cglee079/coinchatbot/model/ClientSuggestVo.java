package com.cglee079.coinchatbot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientSuggestVo {
	private int seq;
	private String userId;
	private String coinId;
	private String username;
	private String contents;
	private String date;
}
