package com.cglee079.coinchatbot.telegram;

import java.util.HashMap;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import com.cglee079.coinchatbot.constants.ID;
import com.cglee079.coinchatbot.keyboard.ConfirmStopKeyboard;
import com.cglee079.coinchatbot.keyboard.MainKeyboard_KR;
import com.cglee079.coinchatbot.keyboard.MainKeyboard_US;
import com.cglee079.coinchatbot.keyboard.PreferenceKeyboard;
import com.cglee079.coinchatbot.keyboard.SetDayloopKeyboard;
import com.cglee079.coinchatbot.keyboard.SetLanguageKeyboard;
import com.cglee079.coinchatbot.keyboard.SetMarketKeyboard;
import com.cglee079.coinchatbot.keyboard.SetTimeloopKeyboard;

public class KeyboardManager {

	private ReplyKeyboardRemove defaultKeyboard;
	private HashMap<String, ReplyKeyboardMarkup> mainKeyboards;
	private HashMap<String, SetDayloopKeyboard> setDayloopKeyboards;
	private HashMap<String, SetTimeloopKeyboard> setTimeloopKeyboards;
	private HashMap<String, SetMarketKeyboard> setMarketKeyboards;
	private HashMap<String, SetLanguageKeyboard> setLanguageKeyboards;
	private HashMap<String, ConfirmStopKeyboard> confirmStopKeyboards;
	private HashMap<String, PreferenceKeyboard> preferenceKeyboards;

	public KeyboardManager(List<String> enabledMarkets) {
		defaultKeyboard 	= new ReplyKeyboardRemove();
		mainKeyboards 		= new HashMap<>();
		setDayloopKeyboards = new HashMap<>();
		setTimeloopKeyboards= new HashMap<>();
		setMarketKeyboards	= new HashMap<>();
		setLanguageKeyboards= new HashMap<>();
		confirmStopKeyboards= new HashMap<>();
		preferenceKeyboards = new HashMap<>();
		
		mainKeyboards.put(ID.LANG_KR, new MainKeyboard_KR(ID.LANG_KR));
		mainKeyboards.put(ID.LANG_US, new MainKeyboard_US(ID.LANG_US));
		
		setDayloopKeyboards.put(ID.LANG_KR, new SetDayloopKeyboard(ID.LANG_KR));
		setDayloopKeyboards.put(ID.LANG_US, new SetDayloopKeyboard(ID.LANG_US));
		
		setTimeloopKeyboards.put(ID.LANG_KR, new SetTimeloopKeyboard(ID.LANG_KR));
		setTimeloopKeyboards.put(ID.LANG_US, new SetTimeloopKeyboard(ID.LANG_US));
		
		setMarketKeyboards.put(ID.LANG_KR, new SetMarketKeyboard(enabledMarkets, ID.LANG_KR));
		setMarketKeyboards.put(ID.LANG_US, new SetMarketKeyboard(enabledMarkets, ID.LANG_US));
		
		setLanguageKeyboards.put(ID.LANG_KR, new SetLanguageKeyboard(ID.LANG_KR));
		setLanguageKeyboards.put(ID.LANG_US, new SetLanguageKeyboard(ID.LANG_US));
		
		confirmStopKeyboards.put(ID.LANG_KR, new ConfirmStopKeyboard(ID.LANG_KR));
		confirmStopKeyboards.put(ID.LANG_US, new ConfirmStopKeyboard(ID.LANG_US));
		
		preferenceKeyboards.put(ID.LANG_KR, new PreferenceKeyboard(ID.LANG_KR));
		preferenceKeyboards.put(ID.LANG_US, new PreferenceKeyboard(ID.LANG_US));
	}

	public ReplyKeyboardRemove getDefaultKeyboard() {
		return defaultKeyboard;
	}
	
	public ReplyKeyboardRemove getDefaultKeyboard(String lang) {
		return defaultKeyboard;
	}

	public ReplyKeyboardMarkup getMainKeyboard(String lang) {
		return mainKeyboards.get(lang);
	}

	public SetDayloopKeyboard getSetDayloopKeyboard(String lang) {
		return setDayloopKeyboards.get(lang);
	}

	public SetTimeloopKeyboard getSetTimeloopKeyboard(String lang) {
		return setTimeloopKeyboards.get(lang);
	}

	public SetMarketKeyboard getSetMarketKeyboard(String lang) {
		return setMarketKeyboards.get(lang);
	}
	
	public SetLanguageKeyboard getSetLanguageKeyboard(String lang) {
		return setLanguageKeyboards.get(lang);
	}

	public ConfirmStopKeyboard getConfirmStopKeyboard(String lang) {
		return confirmStopKeyboards.get(lang);
	}

	public PreferenceKeyboard getPreferenceKeyboard(String lang) {
		return preferenceKeyboards.get(lang);
	}
}
