package com.cglee079.cointelebot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.cointelebot.constants.CMD;
import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.constants.SET;

public class MainKeyboard extends ReplyKeyboardMarkup{
	public MainKeyboard() {
		super();
		
	    this.setSelective(true);
	    this.setResizeKeyboard(true);
	    this.setOneTimeKeyboard(false);

	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow keyboardFirstRow = new KeyboardRow();
	    keyboardFirstRow.add(CMD.MAIN_CURRENT_PRICE);
	    keyboardFirstRow.add(CMD.MAIN_KOREA_PREMIUM);
	    keyboardFirstRow.add(CMD.MAIN_BTC);
	    keyboardFirstRow.add(CMD.MAIN_CALCULATE);
	  
	    
	    KeyboardRow keyboardSecondRow = new KeyboardRow();
	    keyboardSecondRow.add(CMD.MAIN_SET_TARGET);
	    keyboardSecondRow.add(CMD.MAIN_SET_PRICE);
	    keyboardSecondRow.add(CMD.MAIN_SET_NUMBER);
	    keyboardSecondRow.add(CMD.MAIN_SET_EXCHANGE);
	    keyboardSecondRow.add(CMD.MAIN_HAPPY_LINE);
	    
	    KeyboardRow keyboardThirdRow = new KeyboardRow();
	    keyboardThirdRow.add(CMD.MAIN_SET_DAYLOOP);
	    keyboardThirdRow.add(CMD.MAIN_SET_TIMELOOP);
	    keyboardThirdRow.add(CMD.MAIN_INFO);
	    keyboardThirdRow.add(CMD.MAIN_STOP);
	    
	    KeyboardRow keyboardForthRow = new KeyboardRow();
	    keyboardForthRow.add(CMD.MAIN_COIN_LIST);
	    keyboardForthRow.add(CMD.MAIN_HELP);
	    keyboardForthRow.add(CMD.MAIN_SEND_MSG);
	    keyboardForthRow.add(CMD.MAIN_SUPPORT);
	    
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    keyboard.add(keyboardThirdRow);
	    keyboard.add(keyboardForthRow);
	    this.setKeyboard(keyboard);
	}
	

}
