package com.cglee079.cointelebot.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cglee079.cointelebot.coin.CoinManager;
import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.exception.ServerErrorException;
import com.cglee079.cointelebot.log.Log;
import com.cglee079.cointelebot.model.ClientVo;
import com.cglee079.cointelebot.model.CoinMarketConfigVo;
import com.cglee079.cointelebot.service.ClientService;
import com.cglee079.cointelebot.service.CoinMarketConfigService;
import com.cglee079.cointelebot.telegram.TelegramBot;

@Component
public class TargetScheduler {
	@Autowired
	private ClientService clientService;

	@Autowired
	private CoinManager coinManager;
	
	@Autowired
	private CoinMarketConfigService coinMarketConfigService;

	private String myCoin;
	private TelegramBot telegramBot;
	private HashMap<String, Boolean> inBtcs;
	private List<String> enabledMarkets;
	
	public TargetScheduler(String myCoin, TelegramBot telegramBot) {
		this.myCoin 		= myCoin;
		this.telegramBot	= telegramBot;
	}
	
	@PostConstruct
	public void init() {
		List<CoinMarketConfigVo> configMarkets = coinMarketConfigService.list(myCoin);
		CoinMarketConfigVo configMarket;
		
		inBtcs 			= new HashMap<>();
		enabledMarkets	= new ArrayList<String>();
		
		for(int i = 0; i < configMarkets.size(); i++) {
			configMarket = configMarkets.get(i);
			inBtcs.put(configMarket.getMarket(), configMarket.isInBtc());
			enabledMarkets.add(configMarket.getMarket());
		}
	}
	
	@Scheduled(cron = "30 0/1 * * * *")
	public void loadTargetPrices(){
		for(int i = 0; i <enabledMarkets.size(); i++) {
			loadTargetPrice(enabledMarkets.get(i));
		}
	}
	
	public void loadTargetPrice(String market) {
		List<ClientVo> clients = null;
		JSONObject coinObj = null;
		
		try {
			coinObj = coinManager.getCoin(myCoin, market);
			double coinValue = coinObj.getDouble("last");
			
			if(inBtcs.get(market)) {
				coinValue = coinValue * coinManager.getCoin(ID.COIN_BTC, market).getDouble("last");
			}
			
			clients = clientService.list(myCoin, market, coinValue, true); //TargetUp
			telegramBot.sendTargetPriceMessage(clients, market, coinObj, true);
			
			clients = clientService.list(myCoin, market, coinValue, false); //TagetDown
			telegramBot.sendTargetPriceMessage(clients, market, coinObj, false);
			
		} catch (ServerErrorException e) {
			Log.i("Load TargetPrice  " + e.log());
			e.printStackTrace();
		}
	}
}
