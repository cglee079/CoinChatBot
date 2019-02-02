package com.cglee079.coinchatbot.telegram.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.coinchatbot.config.cmd.DayloopCmd;
import com.cglee079.coinchatbot.config.id.Lang;

public class SetDayloopKeyboard extends ReplyKeyboardMarkup{
	public SetDayloopKeyboard(Lang lang) {
		super();
		
	    this.setSelective(true);
	    this.setResizeKeyboard(true);
	    this.setOneTimeKeyboard(false);

	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow keyboardFirstRow = new KeyboardRow();
	    keyboardFirstRow.add(DayloopCmd.D1.getCmd(lang));
	    keyboardFirstRow.add(DayloopCmd.D2.getCmd(lang));
	    keyboardFirstRow.add(DayloopCmd.D3.getCmd(lang));
	    
	    KeyboardRow keyboardSecondRow = new KeyboardRow();
	    keyboardSecondRow.add(DayloopCmd.D4.getCmd(lang));
	    keyboardSecondRow.add(DayloopCmd.D5.getCmd(lang));
	    keyboardSecondRow.add(DayloopCmd.D6.getCmd(lang));
	    
	    KeyboardRow keyboardThirdRow = new KeyboardRow();
	    keyboardThirdRow.add(DayloopCmd.D7.getCmd(lang));
	    keyboardThirdRow.add(DayloopCmd.OFF.getCmd(lang));
	    keyboardThirdRow.add(DayloopCmd.OUT.getCmd(lang));
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    keyboard.add(keyboardThirdRow);
	    
	    this.setKeyboard(keyboard);
	}
	

}
