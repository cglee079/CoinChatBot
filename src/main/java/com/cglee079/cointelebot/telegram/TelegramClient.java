package com.cglee079.cointelebot.telegram;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class TelegramClient {

	public TelegramClient(TelegramBot telegramBot) throws TelegramApiRequestException {
		TelegramBotsApi api = new TelegramBotsApi();
		api.registerBot(telegramBot);
	}

}
