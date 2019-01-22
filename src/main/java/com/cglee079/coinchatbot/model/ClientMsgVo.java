package com.cglee079.coinchatbot.model;

public class ClientMsgVo {
	private int seq;
	private String coinId;
	private String userId;
	private String username;
	private String msg;
	private String date;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	public String getCoinId() {
		return coinId;
	}

	public void setCoinId(String coinId) {
		this.coinId = coinId;
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

	public void setUsername(String username) {
		this.username = username;
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

	public String log(String myCoin) {
		return "From Client\t:\t" + myCoin + "\t[id: " + userId + " : " + username + " ]  " + msg;
	}

}
