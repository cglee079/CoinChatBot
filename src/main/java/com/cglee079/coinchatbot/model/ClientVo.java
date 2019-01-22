package com.cglee079.coinchatbot.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cglee079.coinchatbot.constants.ID;
import com.cglee079.coinchatbot.constants.SET;

public class ClientVo {
	private String coinId			= null;
	private String userId 			= null;
	private String username 		= null;
	private Long localTime 			= null;
	private String lang 			= "kr";
	private String state 			= null;
	private String market 			= null;
	private Integer timeLoop 		= null;
	private Integer dayLoop 		= null;
	private Double targetUpPrice 	= null;
	private Double targetDownPrice 	= null;
	private Double price 			= null;
	private Double coinCount 		= null;
	private String enabled 			= "Y";
	private Integer errCnt 			= 0;
	private String openDate;
	private String reopenDate;
	private String closeDate;
	private String msgDate;

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

	
	public Long getLocalTime() {
		return localTime;
	}

	public void setLocalTime(Long localTime) {
		this.localTime = localTime;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public Integer getTimeLoop() {
		return timeLoop;
	}

	public void setTimeLoop(Integer timeLoop) {
		this.timeLoop = timeLoop;
	}

	public Integer getDayLoop() {
		return dayLoop;
	}

	public void setDayLoop(Integer dayLoop) {
		this.dayLoop = dayLoop;
	}

	public Double getTargetUpPrice() {
		return targetUpPrice;
	}

	public void setTargetUpPrice(Double targetPrice) {
		this.targetUpPrice = targetPrice;
	}

	public Double getTargetDownPrice() {
		return targetDownPrice;
	}

	public void setTargetDownPrice(Double targetPrice) {
		this.targetDownPrice = targetPrice;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCoinCount() {
		return coinCount;
	}

	public void setCoinCount(Double coinCount) {
		this.coinCount = coinCount;
	}

	public String getMakeDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public void setOpenDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.openDate = format.format(date);
	}

	public String getOpenDate() {
		return openDate;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	public Integer getErrCnt() {
		return errCnt;
	}


	public void setErrCnt(Integer errCnt) {
		this.errCnt = errCnt;
	}

	public String getReopenDate() {
		return reopenDate;
	}

	public void setReopenDate(String reopenDate) {
		this.reopenDate = reopenDate;
	}

	public void setReopenDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.reopenDate = format.format(date);
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public void setCloseDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.closeDate = format.format(date);
	}

	public String getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}

}
