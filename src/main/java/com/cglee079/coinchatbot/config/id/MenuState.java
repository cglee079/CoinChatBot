package com.cglee079.coinchatbot.config.id;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MenuState implements CodeEnum {
    MAIN("000"), 
    SET_INVEST("012"),
    SET_COINCNT("013"),
    SET_TARGET("014"),
    SET_MARKET("015"),
    SET_DAYLOOP("016"),
    SET_TIMELOOP("017"),
    SEND_MSG("018"),
    CONFIRM_STOP("019"),
    HAPPY_LINE("020"),
    PREFERENCE("021"),
    PREF_LANGUAGE("022"),
    PREF_TIMEADJUST("023");
	
	@Getter
	private final String code;
	
}
