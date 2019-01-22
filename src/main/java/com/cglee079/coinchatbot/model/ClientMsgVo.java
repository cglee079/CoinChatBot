package com.cglee079.coinchatbot.model;

import com.cglee079.coinchatbot.config.id.Coin;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientMsgVo {
	private int seq;
	private Coin coinId;
	private String userId;
	private String username;
	private String contents;
	private String date;

	public String log(Coin myCoin) {
		return "From Client\t:\t" + myCoin + "\t[id: " + userId + " : " + username + " ]  " + contents;
	}

}
