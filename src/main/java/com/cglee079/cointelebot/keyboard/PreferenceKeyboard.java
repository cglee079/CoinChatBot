package com.cglee079.cointelebot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.cointelebot.cmd.CMDER;

public class PreferenceKeyboard extends ReplyKeyboardMarkup{
	public PreferenceKeyboard(String lang) {
		super();
		
	    this.setSelective(true);
	    this.setResizeKeyboard(true);
	    this.setOneTimeKeyboard(false);

	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow keyboardFirstRow = new KeyboardRow();
	    keyboardFirstRow.add(CMDER.getPrefLang(lang));
	    
	    KeyboardRow keyboardSecondRow = new KeyboardRow();
	    keyboardFirstRow.add(CMDER.getPrefTimeAjdust(lang));
	    
	    KeyboardRow keyboardThirdRow = new KeyboardRow();
	    keyboardThirdRow.add(CMDER.getPrefOut(lang));
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    keyboard.add(keyboardThirdRow);
	    
	    this.setKeyboard(keyboard);
	}
	

}
