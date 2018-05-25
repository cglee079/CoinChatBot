package com.cglee079.cointelebot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.cointelebot.cmd.CMDER;

public class SetLanguageKeyboard extends ReplyKeyboardMarkup{
	public SetLanguageKeyboard(String lang) {
		super();
		
	    this.setSelective(true);
	    this.setResizeKeyboard(true);
	    this.setOneTimeKeyboard(false);

	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow keyboardFirstRow = new KeyboardRow();
	    keyboardFirstRow.add(CMDER.getSetLanguageKR(lang));
	    keyboardFirstRow.add(CMDER.getSetLanguageUS(lang));
	    
	    KeyboardRow keyboardSecondRow = new KeyboardRow();
	    keyboardSecondRow.add(CMDER.getSetLanguageOut(lang));
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    
	    this.setKeyboard(keyboard);
	}
	

}
