package com.cglee079.coinchatbot.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cglee079.coinchatbot.coin.CoinManager;
import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.exception.ServerErrorException;
import com.cglee079.coinchatbot.log.Log;
import com.cglee079.coinchatbot.model.ClientVo;
import com.cglee079.coinchatbot.model.CoinMarketConfigVo;
import com.cglee079.coinchatbot.service.ClientService;
import com.cglee079.coinchatbot.service.CoinMarketConfigService;
import com.cglee079.coinchatbot.telegram.TelegramBot;

@Component
public class TargetScheduler {
	@Autowired
	private ClientService clientService;

	@Autowired
	private CoinManager coinManager;
	
	@Autowired
	private CoinMarketConfigService coinMarketConfigService;

	private Coin myCoin;
	private TelegramBot telegramBot;
	private HashMap<String, Boolean> inBtcs;
	private List<String> enabledMarkets;
	
	public TargetScheduler(Coin myCoin, TelegramBot telegramBot) {
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
			inBtcs.put(configMarket.getMarketId(), configMarket.isInBtc());
			enabledMarkets.add(configMarket.getMarketId());
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
				coinValue = coinValue * coinManager.getCoin(Coin.BTC, market).getDouble("last");
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
