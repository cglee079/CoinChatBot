package com.cglee079.coinchatbot.config.cmd;

import com.cglee079.coinchatbot.config.id.Lang;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MainCmd implements CmdEnum{
	NULL("", ""),
	CURRENT_PRICE("현재가", "Current Price"),
	MARKETS_PRICE("거래소별 현재가", "Each market price"),
	CHANGE_RATE("비트대비 변화량", "Change rate"),
	CALCULATE("손익금 계산", "Calculate"),
	SET_INVEST("투자금액 설정", "Set Investment amount"),
	SET_COINCNT("코인개수 설정", "Set The number of coins"),
	SET_TARGET("목표가 알림", "Set Target price"),
	SET_MARKET("거래소 설정", "Set Market"),
	SET_TIMELOOP("시간알림 설정", "Set Daily notifications"),
	SET_DAYLOOP("일일알림 설정", "Set Hourly notifications"),
	SEND_MESSAGE("문의/건의", "Suggest"),
	HAPPY_LINE("행복회로", "Happy Price"),
	SHOW_SETTING("설정정보", "Show Setting"),
	STOP_ALERTS("모든알림중지", "Stop Notifications"),
	COIN_LIST("타 코인 알리미", "Other COIN"),
	HELP("Help", "Help"),
	SPONSORING("후원하기", "Sponsoring"),
	PREFERENCE("Preference", "Preference");
	
	@Getter
	private final String kr;
	
	@Getter
	private final String us;
	public static MainCmd from(Lang lang, String str) {
		return CmdEnum.from(values(), lang, str, MainCmd.NULL);
	}
	
	public String getCmd(Lang lang) {
		return CmdEnum.getCmd(this, lang);
	}
}
