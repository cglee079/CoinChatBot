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
import com.cglee079.coinchatbot.config.id.Market;
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

	private Coin myCoinId;
	private TelegramBot telegramBot;
	private HashMap<Market, Boolean> inBtcs;
	private List<Market> enabledMarkets;
	
	public TargetScheduler(Coin myCoinId, TelegramBot telegramBot) {
		this.myCoinId 		= myCoinId;
		this.telegramBot	= telegramBot;
	}
	
	@PostConstruct
	public void init() {
		List<CoinMarketConfigVo> configMarkets = coinMarketConfigService.list(myCoinId);
		CoinMarketConfigVo configMarket;
		
		inBtcs 			= new HashMap<>();
		enabledMarkets	= new ArrayList<Market>();
		
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
	
	public void loadTargetPrice(Market marketId) {
		List<ClientVo> clients = null;
		JSONObject coinObj = null;
		
		try {
			coinObj = coinManager.getCoin(myCoinId, marketId);
			double coinValue = coinObj.getDouble("last");
			
			if(inBtcs.get(marketId)) {
				coinValue = coinValue * coinManager.getCoin(Coin.BTC, marketId).getDouble("last");
			}
			
			clients = clientService.list(myCoinId, marketId, coinValue, true); //TargetUp
			telegramBot.sendTargetPriceMessage(clients, marketId, coinObj, true);
			
			clients = clientService.list(myCoinId, marketId, coinValue, false); //TagetDown
			telegramBot.sendTargetPriceMessage(clients, marketId, coinObj, false);
			
		} catch (ServerErrorException e) {
			Log.i("Load TargetPrice  " + e.log());
			e.printStackTrace();
		}
	}
}
