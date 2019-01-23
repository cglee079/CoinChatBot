package com.cglee079.coinchatbot.config.cmd;

import com.cglee079.coinchatbot.config.id.Lang;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PrefLangCmd implements CmdEnum{
	NULL(null, "", ""),
	SET_KR(Lang.KR, "Korea", "Korea"),
	SET_US(Lang.US, "English", "English"),
	OUT(null, "OUT", "OUT");
	
	@Getter
	private Lang id;
	
	@Getter
	private final String kr;
	
	@Getter
	private final String us;
	
	public static PrefLangCmd from(Lang lang, String str) {
		return CmdEnum.from(values(), lang, str, PrefLangCmd.NULL);
	}
	
	public String getCmd(Lang lang) {
		return CmdEnum.getCmd(this, lang);
	}
}
