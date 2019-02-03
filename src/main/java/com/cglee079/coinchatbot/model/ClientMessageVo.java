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
public class ClientMessageVo {
	private int row;
	private int seq;
	private Coin coinId;
	private String userId;
	private String username;
	private String contents;
	private String date;

	public String log(Coin myCoinId) {
		return "From Client\t:\t" + myCoinId + "\t[id: " + userId + " : " + username + " ]  " + contents;
	}

}
