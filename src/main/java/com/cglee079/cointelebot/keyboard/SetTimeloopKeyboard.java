package com.cglee079.cointelebot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.cointelebot.constants.CMD;

public class SetTimeloopKeyboard extends ReplyKeyboardMarkup{
	public SetTimeloopKeyboard() {
		super();
		
	    this.setSelective(true);
	    this.setResizeKeyboard(true);
	    this.setOneTimeKeyboard(false);

	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow keyboardFirstRow = new KeyboardRow();
	    keyboardFirstRow.add(CMD.SET_TIMELOOP_01);
	    keyboardFirstRow.add(CMD.SET_TIMELOOP_02);
	    keyboardFirstRow.add(CMD.SET_TIMELOOP_03);
	    keyboardFirstRow.add(CMD.SET_TIMELOOP_04);
	    
	    KeyboardRow keyboardSecondRow = new KeyboardRow();
	    keyboardSecondRow.add(CMD.SET_TIMELOOP_05);
	    keyboardSecondRow.add(CMD.SET_TIMELOOP_06);
	    keyboardSecondRow.add(CMD.SET_TIMELOOP_07);
	    keyboardSecondRow.add(CMD.SET_TIMELOOP_08);
	    
	    KeyboardRow keyboardThirdRow = new KeyboardRow();
	    keyboardThirdRow.add(CMD.SET_TIMELOOP_09);
	    keyboardThirdRow.add(CMD.SET_TIMELOOP_10);
	    keyboardThirdRow.add(CMD.SET_TIMELOOP_11);
	    keyboardThirdRow.add(CMD.SET_TIMELOOP_12);
	    
	    KeyboardRow keyboardForthRow = new KeyboardRow();
	    keyboardForthRow.add(CMD.SET_TIMELOOP_OFF);
	    keyboardForthRow.add(CMD.SET_TIMELOOP_OUT);
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    keyboard.add(keyboardThirdRow);
	    keyboard.add(keyboardForthRow);
	    
	    this.setKeyboard(keyboard);
	}
	

}
