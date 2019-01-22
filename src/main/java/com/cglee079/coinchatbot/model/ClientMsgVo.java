package com.cglee079.coinchatbot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientMsgVo {
	private int seq;
	private String coinId;
	private String userId;
	private String username;
	private String contents;
	private String date;

	public String log(String myCoin) {
		return "From Client\t:\t" + myCoin + "\t[id: " + userId + " : " + username + " ]  " + contents;
	}

}
