package com.cglee079.coinchatbot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.coinchatbot.config.cmd.MarketCmd;
import com.cglee079.coinchatbot.config.id.Lang;

public class SetMarketKeyboard extends ReplyKeyboardMarkup {
	public SetMarketKeyboard(List<MarketCmd> enabledMarketIds, Lang lang) {
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
			
			keyboardRow.add(enabledMarketIds.get(i).getCmd(lang));
			if(marketCnt%2 == 0) { keyboard.add(keyboardRow);}
		}


		if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
		marketCnt ++;
		keyboardRow.add(MarketCmd.OUT.getCmd(lang));
		keyboard.add(keyboardRow);
		
		this.setKeyboard(keyboard);
	}

}
