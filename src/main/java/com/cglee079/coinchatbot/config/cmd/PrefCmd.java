package com.cglee079.coinchatbot.config.cmd;

import com.cglee079.coinchatbot.config.id.Lang;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PrefCmd implements CmdEnum{
	NULL("", ""),
	SET_LANG("Language", "Language"),
	TIME_ADJUST("Time adjust(시차조절)", "Time adjust"),
	OUT("OUT", "OUT");
	
	@Getter
	private final String kr;
	
	@Getter
	private final String us;
	
	public static PrefCmd from(Lang lang, String str) {
		return CmdEnum.from(values(), lang, str, PrefCmd.NULL);
	}
	
	public String getCmd(Lang lang) {
		return CmdEnum.getCmd(this, lang);
	}
}
