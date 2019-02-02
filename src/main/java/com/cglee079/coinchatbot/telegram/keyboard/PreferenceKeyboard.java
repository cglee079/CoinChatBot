package com.cglee079.coinchatbot.telegram.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.coinchatbot.config.cmd.PrefCmd;
import com.cglee079.coinchatbot.config.id.Lang;

public class PreferenceKeyboard extends ReplyKeyboardMarkup{
	public PreferenceKeyboard(Lang lang) {
		super();
		
	    this.setSelective(true);
	    this.setResizeKeyboard(true);
	    this.setOneTimeKeyboard(false);

	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow keyboardFirstRow = new KeyboardRow();
	    keyboardFirstRow.add(PrefCmd.SET_LANG.getCmd(lang));
	    
	    KeyboardRow keyboardSecondRow = new KeyboardRow();
	    keyboardFirstRow.add(PrefCmd.TIME_ADJUST.getCmd(lang));
	    
	    KeyboardRow keyboardThirdRow = new KeyboardRow();
	    keyboardThirdRow.add(PrefCmd.OUT.getCmd(lang));
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    keyboard.add(keyboardThirdRow);
	    
	    this.setKeyboard(keyboard);
	}
	

}
