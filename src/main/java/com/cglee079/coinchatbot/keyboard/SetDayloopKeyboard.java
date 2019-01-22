package com.cglee079.coinchatbot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.coinchatbot.cmd.CMDER;

public class SetDayloopKeyboard extends ReplyKeyboardMarkup{
	public SetDayloopKeyboard(String lang) {
		super();
		
	    this.setSelective(true);
	    this.setResizeKeyboard(true);
	    this.setOneTimeKeyboard(false);

	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow keyboardFirstRow = new KeyboardRow();
	    keyboardFirstRow.add(CMDER.getSetDayloop01(lang));
	    keyboardFirstRow.add(CMDER.getSetDayloop02(lang));
	    keyboardFirstRow.add(CMDER.getSetDayloop03(lang));
	    
	    KeyboardRow keyboardSecondRow = new KeyboardRow();
	    keyboardSecondRow.add(CMDER.getSetDayloop04(lang));
	    keyboardSecondRow.add(CMDER.getSetDayloop05(lang));
	    keyboardSecondRow.add(CMDER.getSetDayloop06(lang));
	    
	    KeyboardRow keyboardThirdRow = new KeyboardRow();
	    keyboardThirdRow.add(CMDER.getSetDayloop07(lang));
	    keyboardThirdRow.add(CMDER.getSetDayloopOff(lang));
	    keyboardThirdRow.add(CMDER.getSetDayloopOut(lang));
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    keyboard.add(keyboardThirdRow);
	    
	    this.setKeyboard(keyboard);
	}
	

}
