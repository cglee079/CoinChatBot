package com.cglee079.coinchatbot.config.id;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MenuState implements CodeEnum {
    MAIN("000"), 
    SET_INVEST("010"),
    SET_COINCNT("020"),
    
    SET_TARGET("030"),
    ADD_TARGET("031"),
 	DEL_TARGET("032"),
 	
    SET_MARKET("040"),
    SET_DAYLOOP("050"),
    SET_TIMELOOP("060"),
    
    SEND_MSG("070"),
    CONFIRM_STOP("080"),
    HAPPY_LINE("090"),
    PREFERENCE("100"),
    PREF_LANGUAGE("101"),
    PREF_TIMEADJUST("102");
	
	@Getter
	private final String code;
	
}
