package com.cglee079.cointelebot.telegram;

import java.util.List;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class TelegramClient {

	public TelegramClient(List<TelegramBot> telegramBots) throws TelegramApiRequestException {
		TelegramBotsApi api = new TelegramBotsApi();
		for(int i =0; i < telegramBots.size(); i++) {
			api.registerBot(telegramBots.get(i));			
		}
		
	}

}
