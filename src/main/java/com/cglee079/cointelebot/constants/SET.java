package com.cglee079.cointelebot.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cglee079.cointelebot.model.CoinMarketConfigVo;
import com.cglee079.cointelebot.service.CoinMarketConfigService;

@Component
public class SET {
	public final static String MY_COIN = ID.COIN_BTC;
	public final static Integer CLNT_MAX_ERRCNT = 10;
	
	private static HashMap<String, Boolean> inBtcs = new HashMap<>();
	private static List<String> enabledMarkets = new ArrayList<String>();
	
	@Autowired
	private CoinMarketConfigService coinMarketConfigService;

	@PostConstruct
	public void init() {
		List<CoinMarketConfigVo> configMarkets = coinMarketConfigService.list(MY_COIN);
		CoinMarketConfigVo configMarket;
		
		for(int i = 0; i < configMarkets.size(); i++) {
			configMarket = configMarkets.get(i);
			inBtcs.put(configMarket.getMarket(), configMarket.isInBtc());
			enabledMarkets.add(configMarket.getMarket());
		}
	}
	
	public synchronized static boolean isInBtcMarket(String market) {
		return inBtcs.get(market);
	}
	
	public synchronized static List<String> getEnabledMarkets(){
		return enabledMarkets;
	}
	
}
