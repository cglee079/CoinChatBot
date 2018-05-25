package com.cglee079.cointelebot.constants;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cglee079.cointelebot.model.CoinMarketConfigVo;
import com.cglee079.cointelebot.model.CoinConfigVo;
import com.cglee079.cointelebot.service.CoinMarketConfigService;
import com.cglee079.cointelebot.service.CoinConfigService;

@Component
public class SET {
	public final static String MY_COIN = ID.COIN_XVG;
	public final static Integer CLNT_MAX_ERRCNT = 10;
	
	public static HashMap<String, Boolean> inBtcs = new HashMap<>();
	
	public static boolean ENABLED_COINONE	= false;
	public static boolean ENABLED_BITHUMB	= false;	
	public static boolean ENABLED_UPBIT		= false;
	public static boolean ENABLED_COINNEST	= false;
	public static boolean ENABLED_KORBIT	= false;
	public static boolean ENABLED_BITFINEX	= false;
	public static boolean ENABLED_BITTREX	= false;
	public static boolean ENABLED_POLONIEX	= false;
	public static boolean ENABLED_BINANCE	= false;
	
	public static String VERSION;
	public static String EX_PRICE_KR;
	public static String EX_PRICE_US;
	public static String EX_TARGET_KR;
	public static String EX_TARGET_US;
	public static String EX_NUMBER;
	public static Integer DIGIT_KRW;
	public static Integer DIGIT_USD;
	public static Integer DIGIT_BTC;
	public static String EX_RATE;
	
	@Autowired
	private CoinConfigService coinConfigService;
	
	@Autowired
	private CoinMarketConfigService coinMarketConfigService;

	@PostConstruct
	public void init() {
		CoinConfigVo config = coinConfigService.get(MY_COIN);
		SET.VERSION			= config.getVersion();
		SET.EX_PRICE_KR 	= config.getPriceKREx();
		SET.EX_PRICE_US 	= config.getPriceUSEx();
		SET.EX_TARGET_KR	= config.getTargetKREx();
		SET.EX_TARGET_US	= config.getTargetUSEx();
		SET.EX_NUMBER		= config.getNumberEx();
		SET.DIGIT_KRW		= config.getDigitKRW();
		SET.DIGIT_USD		= config.getDigitUSD();
		SET.DIGIT_BTC		= config.getDigitBTC();
		SET.EX_RATE			= "5%";
		
		List<CoinMarketConfigVo> configMarkets = coinMarketConfigService.list(MY_COIN);
		CoinMarketConfigVo configMarket;
		
		for(int i = 0; i < configMarkets.size(); i++) {
			configMarket = configMarkets.get(i);
			inBtcs.put(configMarket.getMarket(), configMarket.isInBtc());
			
			switch(configMarket.getMarket()) {
			case ID.MARKET_COINONE 		: SET.ENABLED_COINONE 	= true; break;
			case ID.MARKET_BITHUMB 		: SET.ENABLED_BITHUMB 	= true; break;
			case ID.MARKET_UPBIT 		: SET.ENABLED_UPBIT 	= true; break;
			case ID.MARKET_COINNEST 	: SET.ENABLED_COINNEST 	= true; break;
			case ID.MARKET_KORBIT 		: SET.ENABLED_KORBIT 	= true; break;
			case ID.MARKET_BITFINNEX 	: SET.ENABLED_BITFINEX 	= true; break;
			case ID.MARKET_BITTREX 		: SET.ENABLED_BITTREX 	= true; break;
			case ID.MARKET_POLONIEX 	: SET.ENABLED_POLONIEX 	= true; break;
			case ID.MARKET_BINANCE 		: SET.ENABLED_BINANCE 	= true; break;
			}
		}
	}
	
	public synchronized static boolean isInBtcMarket(String market) {
		return SET.inBtcs.get(market);
	}
}
