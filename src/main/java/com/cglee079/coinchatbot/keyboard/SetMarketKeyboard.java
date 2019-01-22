package com.cglee079.coinchatbot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.coinchatbot.cmd.CMDER;
import com.cglee079.coinchatbot.config.id.Market;

public class SetMarketKeyboard extends ReplyKeyboardMarkup {
	public SetMarketKeyboard(List<Market> enabledMarketIds, String lang) {
		super();

		this.setSelective(true);
		this.setResizeKeyboard(true);
		this.setOneTimeKeyboard(false);

		List<KeyboardRow> keyboard = new ArrayList<>();
		
		KeyboardRow keyboardRow = null;
		int marketCnt = 0;
		
		for(int i = 0; i < enabledMarketIds.size(); i++) {
			if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow();}
			marketCnt ++;
			
			keyboardRow.add(CMDER.getSetMarket(enabledMarketIds.get(i), lang));
			if(marketCnt%2 == 0) { keyboard.add(keyboardRow);}
		}


		if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
		marketCnt ++;
		keyboardRow.add(CMDER.getSetMarketOut(lang));
		keyboard.add(keyboardRow);
		
		this.setKeyboard(keyboard);
	}

}
