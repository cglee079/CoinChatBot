package com.cglee079.coinchatbot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.coinchatbot.config.cmd.MainCmd;
import com.cglee079.coinchatbot.config.id.Lang;

public class MainKeyboard_US extends ReplyKeyboardMarkup{
	public MainKeyboard_US(Lang lang) {
		super();
		
	    this.setSelective(true);
	    this.setResizeKeyboard(true);
	    this.setOneTimeKeyboard(false);
	    
	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow keyboardFirstRow = new KeyboardRow();
	    keyboardFirstRow.add(MainCmd.CURRENT_PRICE.getCmd(lang));
	    keyboardFirstRow.add(MainCmd.MARKETS_PRICE.getCmd(lang));
	    keyboardFirstRow.add(MainCmd.CHANGE_RATE.getCmd(lang));
	    keyboardFirstRow.add(MainCmd.CALCULATE.getCmd(lang));
	    
	    KeyboardRow keyboardSecondRow = new KeyboardRow();
	    keyboardSecondRow.add(MainCmd.SET_INVEST.getCmd(lang));
	    keyboardSecondRow.add(MainCmd.SET_COINCNT.getCmd(lang));
	    keyboardSecondRow.add(MainCmd.SET_MARKET.getCmd(lang));
	    
	    KeyboardRow keyboardThirdRow = new KeyboardRow();
	    keyboardSecondRow.add(MainCmd.SET_TARGET.getCmd(lang));
	    keyboardThirdRow.add(MainCmd.SET_DAYLOOP.getCmd(lang));
	    keyboardThirdRow.add(MainCmd.SET_TIMELOOP.getCmd(lang));
	    
	    KeyboardRow keyboardForthRow = new KeyboardRow();
	    keyboardThirdRow.add(MainCmd.HAPPY_LINE.getCmd(lang));
	    keyboardThirdRow.add(MainCmd.SHOW_SETTING.getCmd(lang));
	    keyboardThirdRow.add(MainCmd.STOP_ALERTS.getCmd(lang));
	    
	    KeyboardRow keyboardFifthRow = new KeyboardRow();
	    keyboardForthRow.add(MainCmd.COIN_LIST.getCmd(lang));
	    keyboardForthRow.add(MainCmd.SEND_MESSAGE.getCmd(lang));
	    keyboardForthRow.add(MainCmd.SPONSORING.getCmd(lang));
	    keyboardForthRow.add(MainCmd.HELP.getCmd(lang));
	    keyboardForthRow.add(MainCmd.PREFERENCE.getCmd(lang));
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    keyboard.add(keyboardThirdRow);
	    keyboard.add(keyboardForthRow);
	    keyboard.add(keyboardFifthRow);
	    this.setKeyboard(keyboard);
	}
	

}
