package com.cglee079.coinchatbot.config.cmd;

import com.cglee079.coinchatbot.config.id.Lang;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DayloopCmd implements CmdEnum{
	NULL(-1, "", ""),
	D1(1, "1일", "1 Day"),
	D2(2, "2일", "2 Day"),
	D3(3, "3일", "3 Day"),
	D4(4, "4일", "4 Day"),
	D5(5, "5일", "5 Day"),
	D6(6, "6일", "6 Day"),
	D7(7, "7일", "7 Day"),
	OFF(0, "끄기", "Stop"),
	OUT(-1, "나가기", "Out");
	
	@Getter
	private final int value;
	
	@Getter
	private final String kr;
	
	@Getter
	private final String us;
	
	public static DayloopCmd from(Lang lang, String str) {
		return CmdEnum.from(values(), lang, str, DayloopCmd.NULL);
	}
	
	public String getCmd(Lang lang) {
		return CmdEnum.getCmd(this, lang);
		
	}
}
