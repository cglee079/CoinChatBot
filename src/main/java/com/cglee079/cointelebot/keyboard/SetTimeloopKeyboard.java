package com.cglee079.cointelebot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.cointelebot.cmd.CMDER;

public class SetTimeloopKeyboard extends ReplyKeyboardMarkup{
	public SetTimeloopKeyboard(String lang) {
		super();
	    this.setSelective(true);
	    this.setResizeKeyboard(true);
	    this.setOneTimeKeyboard(false);

	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow keyboardFirstRow = new KeyboardRow();
	    keyboardFirstRow.add(CMDER.getSetTimeloop01(lang));
	    keyboardFirstRow.add(CMDER.getSetTimeloop02(lang));
	    keyboardFirstRow.add(CMDER.getSetTimeloop03(lang));
	    keyboardFirstRow.add(CMDER.getSetTimeloop04(lang));
	    
	    KeyboardRow keyboardSecondRow = new KeyboardRow();
	    keyboardSecondRow.add(CMDER.getSetTimeloop05(lang));
	    keyboardSecondRow.add(CMDER.getSetTimeloop06(lang));
	    keyboardSecondRow.add(CMDER.getSetTimeloop07(lang));
	    keyboardSecondRow.add(CMDER.getSetTimeloop08(lang));
	    
	    KeyboardRow keyboardThirdRow = new KeyboardRow();
	    keyboardThirdRow.add(CMDER.getSetTimeloop09(lang));
	    keyboardThirdRow.add(CMDER.getSetTimeloop10(lang));
	    keyboardThirdRow.add(CMDER.getSetTimeloop11(lang));
	    keyboardThirdRow.add(CMDER.getSetTimeloop12(lang));
	    
	    KeyboardRow keyboardForthRow = new KeyboardRow();
	    keyboardForthRow.add(CMDER.getSetTimeloopOff(lang));
	    keyboardForthRow.add(CMDER.getSetTimeloopOut(lang));
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    keyboard.add(keyboardThirdRow);
	    keyboard.add(keyboardForthRow);
	    
	    this.setKeyboard(keyboard);
	}
	

}
