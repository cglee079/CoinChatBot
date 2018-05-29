package com.cglee079.cointelebot.scheduler;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.cointelebot.coin.CoinManager;
import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.constants.SET;
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
		List<String> enabledMarkets = SET.getEnabledMarkets();
		for(int i = 0; i <enabledMarkets.size(); i++) {
			loadTargetPrice(enabledMarkets.get(i));
		}
	}
	
	public void loadTargetPrice(String market) {
		List<ClientVo> clients = null;
		JSONObject coinObj = null;
		
		try {
			coinObj = coinManager.getCoin(SET.MY_COIN, market);
			double coinValue = coinObj.getDouble("last");
			
			if(SET.isInBtcMarket(market)) {
				coinValue = coinValue * coinManager.getCoin(ID.COIN_BTC, market).getDouble("last");
			}
			
			clients = clientService.list(market, coinValue, true); //TargetUp
			telegramBot.sendTargetPriceMessage(clients, market, coinObj, true);
			
			clients = clientService.list(market, coinValue, false); //TagetDown
			telegramBot.sendTargetPriceMessage(clients, market, coinObj, false);
			
		} catch (ServerErrorException e) {
			Log.i("Load TargetPrice  " + e.log());
			e.printStackTrace();
		}
	}
}
