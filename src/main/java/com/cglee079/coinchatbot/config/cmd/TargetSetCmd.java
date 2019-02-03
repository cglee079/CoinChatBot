package com.cglee079.coinchatbot.config.cmd;

import com.cglee079.coinchatbot.config.id.Lang;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TargetSetCmd implements CmdEnum{
	NULL(-1, "", ""),
	ADD(1, "추가", "Add"),
	DEL(0, "삭제", "Delete"),
	OUT(-1, "나가기", "Out");
	
	@Getter
	private final int value;
	
	@Getter
	private final String kr;
	
	@Getter
	private final String us;
	
	public static TargetSetCmd from(Lang lang, String str) {
		return CmdEnum.from(values(), lang, str, TargetSetCmd.NULL);
	}
	
	public String getCmd(Lang lang) {
		return CmdEnum.getCmd(this, lang);
		
	}
}
