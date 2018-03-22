package com.cglee079.cointelebot.scheduler;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.cointelebot.coin.CoinManager;
import com.cglee079.cointelebot.constants.C;
import com.cglee079.cointelebot.exception.ServerErrorException;
import com.cglee079.cointelebot.log.Log;
import com.cglee079.cointelebot.model.ClientVo;
import com.cglee079.cointelebot.service.ClientService;
import com.cglee079.cointelebot.telegram.TelegramBot;
import com.cglee079.cointelebot.util.TimeStamper;

public class TargetScheduler {

	@Autowired
	private TelegramBot telegramBot;

	@Autowired
	private ClientService clientService;

	@Autowired
	private CoinManager coinManager;

	@Scheduled(cron = "30 0/1 * * * *")
	public void loadTargetPrices(){
		if (C.ENABLED_COINONE) { loadTargetPrice(C.EXCHANGE_COINONE);}
		if (C.ENABLED_BITHUMB) { loadTargetPrice(C.EXCHANGE_BITHUMB);}
		if (C.ENABLED_UPBIT) { loadTargetPrice(C.EXCHANGE_UPBIT); }
	}
	
	public void loadTargetPrice(String exchange) {
		List<ClientVo> clients = null;
		JSONObject coinObj = null;
		
		try {
			coinObj = coinManager.getCoin(C.MY_COIN, exchange);
			clients = clientService.list(exchange, null, null, coinObj.getInt("last"));
			telegramBot.sendTargetPriceMessage(clients, coinObj);
		} catch (ServerErrorException e) {
			Log.i("Load TargetPrice  " + e.log());
			Log.i(e.getStackTrace());
		}
	}
}
