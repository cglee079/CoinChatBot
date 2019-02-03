package com.cglee079.coinchatbot.config.cmd;

import com.cglee079.coinchatbot.config.id.Lang;
import com.cglee079.coinchatbot.config.id.TargetFocus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TargetDelCmd implements CmdEnum{
	NULL(null, "", ""),
	OUT(null, "나가기", "Out");
	
	@Getter
	private final TargetFocus value;
	
	@Getter
	private final String kr;
	
	@Getter
	private final String us;
	
	public static TargetDelCmd from(Lang lang, String str) {
		return CmdEnum.from(values(), lang, str, TargetDelCmd.NULL);
	}
	
	public String getCmd(Lang lang) {
		return CmdEnum.getCmd(this, lang);
		
	}
}
