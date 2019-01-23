package com.cglee079.coinchatbot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.coinchatbot.config.cmd.TimeloopCmd;
import com.cglee079.coinchatbot.config.id.Lang;

public class SetTimeloopKeyboard extends ReplyKeyboardMarkup{
	public SetTimeloopKeyboard(Lang lang) {
		super();
	    this.setSelective(true);
	    this.setResizeKeyboard(true);
	    this.setOneTimeKeyboard(false);

	    List<KeyboardRow> keyboard = new ArrayList<>();
	    KeyboardRow keyboardFirstRow = new KeyboardRow();
	    keyboardFirstRow.add(TimeloopCmd.H1.getCmd(lang));
	    keyboardFirstRow.add(TimeloopCmd.H2.getCmd(lang));
	    keyboardFirstRow.add(TimeloopCmd.H3.getCmd(lang));
	    keyboardFirstRow.add(TimeloopCmd.H4.getCmd(lang));
	    
	    KeyboardRow keyboardSecondRow = new KeyboardRow();
	    keyboardSecondRow.add(TimeloopCmd.H5.getCmd(lang));
	    keyboardSecondRow.add(TimeloopCmd.H6.getCmd(lang));
	    keyboardSecondRow.add(TimeloopCmd.H7.getCmd(lang));
	    keyboardSecondRow.add(TimeloopCmd.H8.getCmd(lang));
	    
	    KeyboardRow keyboardThirdRow = new KeyboardRow();
	    keyboardThirdRow.add(TimeloopCmd.H9.getCmd(lang));
	    keyboardThirdRow.add(TimeloopCmd.H10.getCmd(lang));
	    keyboardThirdRow.add(TimeloopCmd.H11.getCmd(lang));
	    keyboardThirdRow.add(TimeloopCmd.H12.getCmd(lang));
	    
	    KeyboardRow keyboardForthRow = new KeyboardRow();
	    keyboardForthRow.add(TimeloopCmd.OFF.getCmd(lang));
	    keyboardForthRow.add(TimeloopCmd.OUT.getCmd(lang));
	    
	    keyboard.add(keyboardFirstRow);
	    keyboard.add(keyboardSecondRow);
	    keyboard.add(keyboardThirdRow);
	    keyboard.add(keyboardForthRow);
	    
	    this.setKeyboard(keyboard);
	}
	

}
