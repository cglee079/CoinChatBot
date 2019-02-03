package com.cglee079.coinchatbot.telegram.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.coinchatbot.config.cmd.TargetSetCmd;
import com.cglee079.coinchatbot.config.id.Lang;

public class SetTargetKeyboard extends ReplyKeyboardMarkup{
	public SetTargetKeyboard(Lang lang) {
		super();
		
	    this.setSelective(true);
	    this.setResizeKeyboard(true);
	    this.setOneTimeKeyboard(false);

	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow keyboardFirstRow = new KeyboardRow();
	    keyboardFirstRow.add(TargetSetCmd.ADD.getCmd(lang));
	    keyboardFirstRow.add(TargetSetCmd.DEL.getCmd(lang));
	    
	    KeyboardRow keyboardSecondRow = new KeyboardRow();
	    keyboardSecondRow.add(TargetSetCmd.OUT.getCmd(lang));
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    
	    this.setKeyboard(keyboard);
	}
	

}
