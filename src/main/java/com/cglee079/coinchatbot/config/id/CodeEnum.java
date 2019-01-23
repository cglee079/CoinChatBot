package com.cglee079.coinchatbot.config.id;

public interface CodeEnum {
	public String getCode();
	
	public static <E extends Enum<E> & CodeEnum> E from(E[] es, String str) {
		E rs = null;
		
		for (E b : es) {
			if (b.getCode().equalsIgnoreCase(str)) {
				return b;
			}
		}
		
		return rs;
	}
}
