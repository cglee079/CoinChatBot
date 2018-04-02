package com.cglee079.cointelebot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.cointelebot.constants.SET;
import com.cglee079.cointelebot.constants.CMD;

public class SetExchangeKeyboard extends ReplyKeyboardMarkup {
	public SetExchangeKeyboard() {
		super();

		this.setSelective(true);
		this.setResizeKeyboard(true);
		this.setOneTimeKeyboard(false);

		List<KeyboardRow> keyboard = new ArrayList<>();
		
		if (SET.ENABLED_COINONE) {
			KeyboardRow keyboardFirstRow = new KeyboardRow();
			keyboardFirstRow.add(CMD.SET_EXCHANGE_COINONE);
			keyboard.add(keyboardFirstRow);
		}

		if (SET.ENABLED_BITHUMB) {
			KeyboardRow keyboardSecondRow = new KeyboardRow();
			keyboardSecondRow.add(CMD.SET_EXCHANGE_BITHUMB);
			keyboard.add(keyboardSecondRow);
		}

		if (SET.ENABLED_UPBIT) {
			KeyboardRow keyboardThirdRow = new KeyboardRow();
			keyboardThirdRow.add(CMD.SET_EXCHANGE_UPBIT);
			keyboard.add(keyboardThirdRow);
		}
		
		if (SET.ENABLED_COINNEST) {
			KeyboardRow keyboardThirdRow = new KeyboardRow();
			keyboardThirdRow.add(CMD.SET_EXCHANGE_COINNEST);
			keyboard.add(keyboardThirdRow);
		}

		KeyboardRow keyboardForthRow = new KeyboardRow();
		keyboardForthRow.add(CMD.SET_EXCHANGE_OUT);
		keyboard.add(keyboardForthRow);
		
		this.setKeyboard(keyboard);
	}

}
