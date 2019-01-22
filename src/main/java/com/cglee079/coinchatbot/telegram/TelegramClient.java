package com.cglee079.coinchatbot.telegram;

import java.util.List;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class TelegramClient {

	public TelegramClient(List<TelegramBot> telegramBots) throws TelegramApiRequestException {
		TelegramBotsApi api = new TelegramBotsApi();
		for(int i =0; i < telegramBots.size(); i++) {
			api.registerBot(telegramBots.get(i));			
		}
	}

}
