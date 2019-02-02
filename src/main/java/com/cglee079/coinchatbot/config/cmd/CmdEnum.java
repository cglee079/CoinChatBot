package com.cglee079.coinchatbot.config.cmd;

import com.cglee079.coinchatbot.config.id.Lang;

public interface CmdEnum {
	String getKr();
	String getUs();

	public static <E extends Enum<E> & CmdEnum> E from(E[] values, Lang lang, String str, E dfalt) {
		E rs = null;
		
		if(lang == null) {
			return null;
		}
		
		switch(lang) {
		case KR : 
			for (E b : values) {
				if (b.getKr().equalsIgnoreCase(str)) {
					return b;
				}
			}
			break;
		case US :
			for (E b : values) {
				if (b.getUs().equalsIgnoreCase(str)) {
					return b;
				}
			}
			break;
		}
		
		if(rs == null) {
			rs = dfalt;
		}
		
		return rs;
	}
	
	public static <E extends Enum<E> & CmdEnum> String getCmd(E e, Lang lang) {
		String cmd = "";
		switch(lang) {
		case KR : cmd = e.getKr(); break;
		case US : cmd = e.getUs(); break;
		}
		
		return cmd;
	}

}
