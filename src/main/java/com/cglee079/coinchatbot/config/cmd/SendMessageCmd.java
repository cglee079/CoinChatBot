package com.cglee079.coinchatbot.config.cmd;

import com.cglee079.coinchatbot.config.id.Lang;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SendMessageCmd implements CmdEnum{
	NULL("", ""),
	OUT("0", "0");
	
	@Getter
	private final String kr;
	
	@Getter
	private final String us;
	
	public static SendMessageCmd from(Lang lang, String str) {
		return CmdEnum.from(values(), lang, str, SendMessageCmd.NULL);
	}
	
	public String getCmd(Lang lang) {
		return CmdEnum.getCmd(this, lang);
	}
}
