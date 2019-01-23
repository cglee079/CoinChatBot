package com.cglee079.coinchatbot.config.cmd;

import com.cglee079.coinchatbot.config.id.Lang;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StopConfirmCmd implements CmdEnum{
	NULL("", ""),
	YES("예", "Yes"),
	NO("아니오", "No");
	
	@Getter
	private final String kr;
	
	@Getter
	private final String us;
	
	public static StopConfirmCmd from(Lang lang, String str) {
		return CmdEnum.from(values(), lang, str, StopConfirmCmd.NULL);
	}
	
	public String getCmd(Lang lang) {
		return CmdEnum.getCmd(this, lang);
	}
}
