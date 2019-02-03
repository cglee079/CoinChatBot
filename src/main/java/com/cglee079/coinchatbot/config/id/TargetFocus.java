package com.cglee079.coinchatbot.config.id;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TargetFocus{
	UP("이상", "MORE"), 
	DOWN("이하", "LESS");
	
	@Getter
	private String kr;
	
	@Getter
	private String us;
	
	public String getStr(Lang lang) {
		switch(lang) {
		case KR : return kr;
		case US : return us;
		}
		
		return "";
	}
	
}
