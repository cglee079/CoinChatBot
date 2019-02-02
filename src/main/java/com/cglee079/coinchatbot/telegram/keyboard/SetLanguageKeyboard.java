package com.cglee079.coinchatbot.telegram.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.coinchatbot.config.cmd.PrefLangCmd;
import com.cglee079.coinchatbot.config.id.Lang;

public class SetLanguageKeyboard extends ReplyKeyboardMarkup{
	public SetLanguageKeyboard(Lang lang) {
		super();
		
	    this.setSelective(true);
	    this.setResizeKeyboard(true);
	    this.setOneTimeKeyboard(false);

	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow keyboardFirstRow = new KeyboardRow();
	    keyboardFirstRow.add(PrefLangCmd.SET_KR.getCmd(lang));
	    keyboardFirstRow.add(PrefLangCmd.SET_US.getCmd(lang));
	    
	    KeyboardRow keyboardSecondRow = new KeyboardRow();
	    keyboardSecondRow.add(PrefLangCmd.OUT.getCmd(lang));
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    
	    this.setKeyboard(keyboard);
	}
	

}
