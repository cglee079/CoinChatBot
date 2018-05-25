package com.cglee079.cointelebot.keyboard;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import com.cglee079.cointelebot.cmd.CMDER;
import com.cglee079.cointelebot.constants.SET;

public class SetMarketKeyboard extends ReplyKeyboardMarkup {
	public SetMarketKeyboard(String lang) {
		super();

		this.setSelective(true);
		this.setResizeKeyboard(true);
		this.setOneTimeKeyboard(false);

		List<KeyboardRow> keyboard = new ArrayList<>();
		
		KeyboardRow keyboardRow = null;
		int marketCnt = 0;
		
		if (SET.ENABLED_COINONE) {
			if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow();}
			marketCnt ++;
			
			keyboardRow.add(CMDER.getSetMarketCoinone(lang));
			if(marketCnt%2 == 0) { keyboard.add(keyboardRow);}
			
		}

		if (SET.ENABLED_BITHUMB) {
			if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow();}
			marketCnt ++;
			
			keyboardRow.add(CMDER.getSetMarketBithumb(lang));
			if(marketCnt%2 == 0) { keyboard.add(keyboardRow);}
		}

		if (SET.ENABLED_UPBIT) {
			if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
			marketCnt ++;
			
			keyboardRow.add(CMDER.getSetMarketUpbit(lang));
			if(marketCnt%2 == 0) { keyboard.add(keyboardRow);}
		}
		
		if (SET.ENABLED_COINNEST) {
			if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
			marketCnt ++;
			
			keyboardRow.add(CMDER.getSetMarketCoinnest(lang));
			if(marketCnt%2 == 0) { keyboard.add(keyboardRow);}
		}
		
		if (SET.ENABLED_KORBIT) {
			if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
			marketCnt ++;
			
			keyboardRow.add(CMDER.getSetMarketKorbit(lang));
			if(marketCnt%2 == 0) { keyboard.add(keyboardRow);}
		}
		
		if (SET.ENABLED_BITFINEX) {
			if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
			marketCnt ++;
			
			keyboardRow.add(CMDER.getSetMarketBitfinex(lang));
			if(marketCnt%2 == 0) { keyboard.add(keyboardRow);}
		}
		
		if (SET.ENABLED_BITTREX) {
			if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
			marketCnt ++;
			
			keyboardRow.add(CMDER.getSetMarketBittrex(lang));
			if(marketCnt%2 == 0) { keyboard.add(keyboardRow);}
		}
		
		if (SET.ENABLED_POLONIEX) {
			if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
			marketCnt ++;
			
			keyboardRow.add(CMDER.getSetMarketPoloniex(lang));
			if(marketCnt%2 == 0) { keyboard.add(keyboardRow);}
		}
		
		if (SET.ENABLED_BINANCE) {
			if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
			marketCnt ++;
			
			keyboardRow.add(CMDER.getSetMarketBinance(lang));
			if(marketCnt%2 == 0) { keyboard.add(keyboardRow);}
		}

		if(marketCnt%2 == 0) { keyboardRow = new KeyboardRow(); }
		marketCnt ++;
		keyboardRow.add(CMDER.getSetMarketOut(lang));
		keyboard.add(keyboardRow);
		
		this.setKeyboard(keyboard);
	}

}
