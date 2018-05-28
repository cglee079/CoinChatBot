package com.cglee079.cointelebot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.cointelebot.cmd.CMDER;

public class MainKeyboard_KR extends ReplyKeyboardMarkup{
	public MainKeyboard_KR(String lang) {
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
	    keyboardSecondRow.add(CMDER.getMainSetTarget(lang));
	    keyboardSecondRow.add(CMDER.getMainSetPrice(lang));
	    keyboardSecondRow.add(CMDER.getMainSetNumber(lang));
	    keyboardSecondRow.add(CMDER.getMainSetMarket(lang));
	    keyboardSecondRow.add(CMDER.getMainHappyLine(lang));
	    
	    KeyboardRow keyboardThirdRow = new KeyboardRow();
	    keyboardThirdRow.add(CMDER.getMainSetDayloop(lang));
	    keyboardThirdRow.add(CMDER.getMainSetTimeloop(lang));
	    keyboardThirdRow.add(CMDER.getMainInfo(lang));
	    keyboardThirdRow.add(CMDER.getMainStop(lang));
	    
	    KeyboardRow keyboardForthRow = new KeyboardRow();
	    keyboardForthRow.add(CMDER.getMainCoinList(lang));
	    keyboardForthRow.add(CMDER.getMainSendMsg(lang));
	    keyboardForthRow.add(CMDER.getMainSupport(lang));
	    keyboardForthRow.add(CMDER.getMainHelp(lang));
	    keyboardForthRow.add(CMDER.getMainPref(lang));
	    
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    keyboard.add(keyboardThirdRow);
	    keyboard.add(keyboardForthRow);
	    this.setKeyboard(keyboard);
	}
	

}
