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
		
		KeyboardRow keyboardRow = null;
		int exchangeCnt = 0;
		
		if (SET.ENABLED_COINONE) {
			if(exchangeCnt%2 == 0) { keyboardRow = new KeyboardRow();}
			exchangeCnt ++;
			
			keyboardRow.add(CMD.SET_EXCHANGE_COINONE);
			if(exchangeCnt%2 == 0) { keyboard.add(keyboardRow);}
			
		}

		if (SET.ENABLED_BITHUMB) {
			if(exchangeCnt%2 == 0) { keyboardRow = new KeyboardRow();}
			exchangeCnt ++;
			
			keyboardRow.add(CMD.SET_EXCHANGE_BITHUMB);
			if(exchangeCnt%2 == 0) { keyboard.add(keyboardRow);}
		}

		if (SET.ENABLED_UPBIT) {
			if(exchangeCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
			exchangeCnt ++;
			
			keyboardRow.add(CMD.SET_EXCHANGE_UPBIT);
			if(exchangeCnt%2 == 0) { keyboard.add(keyboardRow);}
		}
		
		if (SET.ENABLED_COINNEST) {
			if(exchangeCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
			exchangeCnt ++;
			
			keyboardRow.add(CMD.SET_EXCHANGE_COINNEST);
			if(exchangeCnt%2 == 0) { keyboard.add(keyboardRow);}
		}
		
		if (SET.ENABLED_KORBIT) {
			if(exchangeCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
			exchangeCnt ++;
			
			keyboardRow.add(CMD.SET_EXCHANGE_KORTBIT);
			if(exchangeCnt%2 == 0) { keyboard.add(keyboardRow);}
		}

		if(exchangeCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
		exchangeCnt ++;
		keyboardRow.add(CMD.SET_EXCHANGE_OUT);
		keyboard.add(keyboardRow);
		
		this.setKeyboard(keyboard);
	}

}
