package com.cglee079.coinchatbot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.coinchatbot.config.cmd.StopConfirmCmd;
import com.cglee079.coinchatbot.config.id.Lang;

public class ConfirmStopKeyboard extends ReplyKeyboardMarkup {
	public ConfirmStopKeyboard(Lang lang) {
		super();

		this.setSelective(true);
		this.setResizeKeyboard(true);
		this.setOneTimeKeyboard(false);

		List<KeyboardRow> keyboard = new ArrayList<>();
		
		KeyboardRow keyboardFirstRow = new KeyboardRow();
		keyboardFirstRow.add(StopConfirmCmd.YES.getCmd(lang));
		keyboardFirstRow.add(StopConfirmCmd.NO.getCmd(lang));
		keyboard.add(keyboardFirstRow);

		this.setKeyboard(keyboard);
	}

}
