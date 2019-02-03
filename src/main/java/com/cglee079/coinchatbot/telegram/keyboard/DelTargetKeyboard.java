package com.cglee079.coinchatbot.telegram.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.coinchatbot.config.cmd.TargetDelCmd;
import com.cglee079.coinchatbot.config.id.Lang;

public class DelTargetKeyboard extends ReplyKeyboardMarkup {

	public DelTargetKeyboard(List<String> targetsCmd, Lang lang) {
		super();

		this.setSelective(true);
		this.setResizeKeyboard(true);
		this.setOneTimeKeyboard(false);

		List<KeyboardRow> keyboard = new ArrayList<>();

		KeyboardRow keyboardRow = new KeyboardRow();

		for (int i = 0; i < targetsCmd.size(); i++) {
			keyboardRow = new KeyboardRow();
			keyboardRow.add(targetsCmd.get(i));
			keyboard.add(keyboardRow);
		}

		keyboardRow = new KeyboardRow();
		keyboardRow.add(TargetDelCmd.OUT.getCmd(lang));
		keyboard.add(keyboardRow);

		this.setKeyboard(keyboard);

	}

}
