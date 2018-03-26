package com.cglee079.cointelebot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.cointelebot.constants.CMD;

public class SetDayloopKeyboard extends ReplyKeyboardMarkup{
	public SetDayloopKeyboard() {
		super();
		
	    this.setSelective(true);
	    this.setResizeKeyboard(true);
	    this.setOneTimeKeyboard(false);

	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow keyboardFirstRow = new KeyboardRow();
	    keyboardFirstRow.add(CMD.SET_DAYLOOP_01);
	    keyboardFirstRow.add(CMD.SET_DAYLOOP_02);
	    keyboardFirstRow.add(CMD.SET_DAYLOOP_03);
	    
	    KeyboardRow keyboardSecondRow = new KeyboardRow();
	    keyboardSecondRow.add(CMD.SET_DAYLOOP_04);
	    keyboardSecondRow.add(CMD.SET_DAYLOOP_05);
	    keyboardSecondRow.add(CMD.SET_DAYLOOP_06);
	    
	    KeyboardRow keyboardThirdRow = new KeyboardRow();
	    keyboardThirdRow.add(CMD.SET_DAYLOOP_07);
	    keyboardThirdRow.add(CMD.SET_DAYLOOP_OFF);
	    keyboardThirdRow.add(CMD.SET_DAYLOOP_OUT);
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    keyboard.add(keyboardThirdRow);
	    
	    this.setKeyboard(keyboard);
	}
	

}
