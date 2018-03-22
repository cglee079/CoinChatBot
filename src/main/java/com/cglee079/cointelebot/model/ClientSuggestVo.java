package com.cglee079.cointelebot.model;

import com.cglee079.cointelebot.constants.C;

public class ClientSuggestVo {
	private int seq;
	private String userId;
	private String coin;
	private String username;
	private String msg;
	private String date;

	
	public ClientSuggestVo() {
		coin = C.MY_COIN_STR;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getCoin() {
		return coin;
	}

	public void setCoin(String coin) {
		this.coin = coin;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
