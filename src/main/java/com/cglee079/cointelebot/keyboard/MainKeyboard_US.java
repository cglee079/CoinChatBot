package com.cglee079.cointelebot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.cointelebot.cmd.CMDER;

public class MainKeyboard_US extends ReplyKeyboardMarkup{
	public MainKeyboard_US(String lang) {
		super();
		
	    this.setSelective(true);
	    this.setResizeKeyboard(true);
	    this.setOneTimeKeyboard(false);

	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow keyboardFirstRow = new KeyboardRow();
	    keyboardFirstRow.add(CMDER.getMainCurrentPrice(lang));
	    keyboardFirstRow.add(CMDER.getMainKoreaPremium(lang));
	    keyboardFirstRow.add(CMDER.getMainBtc(lang));
	    keyboardFirstRow.add(CMDER.getMainCalculate(lang));
	  
	    
	    KeyboardRow keyboardSecondRow = new KeyboardRow();
	    keyboardSecondRow.add(CMDER.getMainSetPrice(lang));
	    keyboardSecondRow.add(CMDER.getMainSetNumber(lang));
	    keyboardSecondRow.add(CMDER.getMainSetMarket(lang));
	    
	    
	    KeyboardRow keyboardThirdRow = new KeyboardRow();
	    keyboardThirdRow.add(CMDER.getMainSetTarget(lang));
	    keyboardThirdRow.add(CMDER.getMainSetDayloop(lang));
	    keyboardThirdRow.add(CMDER.getMainSetTimeloop(lang));
	    
	    
	    KeyboardRow keyboardForthRow = new KeyboardRow();
	    keyboardForthRow.add(CMDER.getMainHappyLine(lang));
	    keyboardForthRow.add(CMDER.getMainInfo(lang));
	    keyboardForthRow.add(CMDER.getMainStop(lang));
	    
	    KeyboardRow keyboardFifthRow = new KeyboardRow();
	    keyboardFifthRow.add(CMDER.getMainCoinList(lang));
	    keyboardFifthRow.add(CMDER.getMainSendMsg(lang));
	    keyboardFifthRow.add(CMDER.getMainSupport(lang));
	    keyboardFifthRow.add(CMDER.getMainHelp(lang));
	    keyboardFifthRow.add(CMDER.getMainPref(lang));
	    
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    keyboard.add(keyboardThirdRow);
	    keyboard.add(keyboardForthRow);
	    keyboard.add(keyboardFifthRow);
	    this.setKeyboard(keyboard);
	}
	

}
