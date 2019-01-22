package com.cglee079.cointelebot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.cointelebot.cmd.CMDER;

public class SetMarketKeyboard extends ReplyKeyboardMarkup {
	public SetMarketKeyboard(List<String> enabledMarkets, String lang) {
		super();

		this.setSelective(true);
		this.setResizeKeyboard(true);
		this.setOneTimeKeyboard(false);

		List<KeyboardRow> keyboard = new ArrayList<>();
		
		KeyboardRow keyboardRow = null;
		int marketCnt = 0;
		
		for(int i = 0; i < enabledMarkets.size(); i++) {
			if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow();}
			marketCnt ++;
			
			keyboardRow.add(CMDER.getSetMarket(enabledMarkets.get(i), lang));
			if(marketCnt%2 == 0) { keyboard.add(keyboardRow);}
		}


		if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
		marketCnt ++;
		keyboardRow.add(CMDER.getSetMarketOut(lang));
		keyboard.add(keyboardRow);
		
		this.setKeyboard(keyboard);
	}

}
