package com.cglee079.cointelebot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.cointelebot.constants.SET;
import com.cglee079.cointelebot.constants.CMD;

public class ConfirmStopKeyboard extends ReplyKeyboardMarkup {
	public ConfirmStopKeyboard() {
		super();

		this.setSelective(true);
		this.setResizeKeyboard(true);
		this.setOneTimeKeyboard(false);

		List<KeyboardRow> keyboard = new ArrayList<>();
		
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		keyboardFirstRow.add(CMD.CONFIRM_STOP_YES);
		keyboardFirstRow.add(CMD.CONFIRM_STOP_NO);
		keyboard.add(keyboardFirstRow);

		this.setKeyboard(keyboard);
	}

}
