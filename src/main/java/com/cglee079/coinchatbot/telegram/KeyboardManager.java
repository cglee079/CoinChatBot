package com.cglee079.coinchatbot.telegram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import com.cglee079.coinchatbot.config.cmd.MarketCmd;
import com.cglee079.coinchatbot.config.id.Lang;
import com.cglee079.coinchatbot.config.id.Market;
import com.cglee079.coinchatbot.telegram.keyboard.ConfirmStopKeyboard;
import com.cglee079.coinchatbot.telegram.keyboard.MainKeyboard_KR;
import com.cglee079.coinchatbot.telegram.keyboard.MainKeyboard_US;
import com.cglee079.coinchatbot.telegram.keyboard.PreferenceKeyboard;
import com.cglee079.coinchatbot.telegram.keyboard.SetDayloopKeyboard;
import com.cglee079.coinchatbot.telegram.keyboard.SetLanguageKeyboard;
import com.cglee079.coinchatbot.telegram.keyboard.SetMarketKeyboard;
import com.cglee079.coinchatbot.telegram.keyboard.SetTimeloopKeyboard;

public class KeyboardManager {

	private ReplyKeyboardRemove defaultKeyboard;
	private HashMap<Lang, ReplyKeyboardMarkup> mainKeyboards;
	private HashMap<Lang, SetDayloopKeyboard> setDayloopKeyboards;
	private HashMap<Lang, SetTimeloopKeyboard> setTimeloopKeyboards;
	private HashMap<Lang, SetMarketKeyboard> setMarketKeyboards;
	private HashMap<Lang, SetLanguageKeyboard> setLanguageKeyboards;
	private HashMap<Lang, ConfirmStopKeyboard> confirmStopKeyboards;
	private HashMap<Lang, PreferenceKeyboard> preferenceKeyboards;

	public KeyboardManager(List<Market> enabledMarketIds) {
		
		//enabledMarketId --> enabledMarketCmd
		List<MarketCmd> enabledMarketCmds = new ArrayList<>();
		for(int i = 0; i < enabledMarketIds.size(); i++) {
			enabledMarketCmds.add(MarketCmd.from(enabledMarketIds.get(i)));
		}
		
		defaultKeyboard 	= new ReplyKeyboardRemove();
		mainKeyboards 		= new HashMap<>();
		setDayloopKeyboards = new HashMap<>();
		setTimeloopKeyboards= new HashMap<>();
		setMarketKeyboards	= new HashMap<>();
		setLanguageKeyboards= new HashMap<>();
		confirmStopKeyboards= new HashMap<>();
		preferenceKeyboards = new HashMap<>();
		
		mainKeyboards.put(Lang.KR, new MainKeyboard_KR(Lang.KR));
		mainKeyboards.put(Lang.US, new MainKeyboard_US(Lang.US));
		
		setDayloopKeyboards.put(Lang.KR, new SetDayloopKeyboard(Lang.KR));
		setDayloopKeyboards.put(Lang.US, new SetDayloopKeyboard(Lang.US));
		
		setTimeloopKeyboards.put(Lang.KR, new SetTimeloopKeyboard(Lang.KR));
		setTimeloopKeyboards.put(Lang.US, new SetTimeloopKeyboard(Lang.US));
		
		setMarketKeyboards.put(Lang.KR, new SetMarketKeyboard(enabledMarketCmds, Lang.KR));
		setMarketKeyboards.put(Lang.US, new SetMarketKeyboard(enabledMarketCmds, Lang.US));
		
		setLanguageKeyboards.put(Lang.KR, new SetLanguageKeyboard(Lang.KR));
		setLanguageKeyboards.put(Lang.US, new SetLanguageKeyboard(Lang.US));
		
		confirmStopKeyboards.put(Lang.KR, new ConfirmStopKeyboard(Lang.KR));
		confirmStopKeyboards.put(Lang.US, new ConfirmStopKeyboard(Lang.US));
		
		preferenceKeyboards.put(Lang.KR, new PreferenceKeyboard(Lang.KR));
		preferenceKeyboards.put(Lang.US, new PreferenceKeyboard(Lang.US));
	}

	public ReplyKeyboardRemove getDefaultKeyboard() {
		return defaultKeyboard;
	}
	
	public ReplyKeyboardRemove getDefaultKeyboard(Lang lang) {
		return defaultKeyboard;
	}

	public ReplyKeyboardMarkup getMainKeyboard(Lang lang) {
		return mainKeyboards.get(lang);
	}

	public SetDayloopKeyboard getSetDayloopKeyboard(Lang lang) {
		return setDayloopKeyboards.get(lang);
	}

	public SetTimeloopKeyboard getSetTimeloopKeyboard(Lang lang) {
		return setTimeloopKeyboards.get(lang);
	}

	public SetMarketKeyboard getSetMarketKeyboard(Lang lang) {
		return setMarketKeyboards.get(lang);
	}
	
	public SetLanguageKeyboard getSetLanguageKeyboard(Lang lang) {
		return setLanguageKeyboards.get(lang);
	}

	public ConfirmStopKeyboard getConfirmStopKeyboard(Lang lang) {
		return confirmStopKeyboards.get(lang);
	}

	public PreferenceKeyboard getPreferenceKeyboard(Lang lang) {
		return preferenceKeyboards.get(lang);
	}
}
