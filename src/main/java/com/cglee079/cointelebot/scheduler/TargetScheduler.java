package com.cglee079.cointelebot.scheduler;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.cointelebot.coin.CoinManager;
import com.cglee079.cointelebot.constants.SET;
import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.exception.ServerErrorException;
import com.cglee079.cointelebot.log.Log;
import com.cglee079.cointelebot.model.ClientVo;
import com.cglee079.cointelebot.service.ClientService;
import com.cglee079.cointelebot.telegram.TelegramBot;

public class TargetScheduler {

	@Autowired
	private TelegramBot telegramBot;

	@Autowired
	private ClientService clientService;

	@Autowired
	private CoinManager coinManager;

	@Scheduled(cron = "30 0/1 * * * *")
	public void loadTargetPrices(){
		if (SET.ENABLED_COINONE) { loadTargetPrice(ID.EXCHANGE_COINONE);}
		if (SET.ENABLED_BITHUMB) { loadTargetPrice(ID.EXCHANGE_BITHUMB);}
		if (SET.ENABLED_UPBIT) { loadTargetPrice(ID.EXCHANGE_UPBIT); }
		if (SET.ENABLED_COINNEST) { loadTargetPrice(ID.EXCHANGE_COINNEST); }
		if (SET.ENABLED_KORBIT) { loadTargetPrice(ID.EXCHANGE_KORBIT); }
	}
	
	public void loadTargetPrice(String exchange) {
		List<ClientVo> clients = null;
		JSONObject coinObj = null;
		
		try {
			coinObj = coinManager.getCoin(SET.MY_COIN, exchange);
			clients = clientService.list(exchange, null, null, coinObj.getDouble("last"));
			telegramBot.sendTargetPriceMessage(clients, coinObj);
		} catch (ServerErrorException e) {
			Log.i("Load TargetPrice  " + e.log());
			Log.i(e.getStackTrace());
		}
	}
}
