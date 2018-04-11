package com.cglee079.cointelebot.constants;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cglee079.cointelebot.model.CoinConfigVo;
import com.cglee079.cointelebot.service.CoinConfigService;

@Component
public class SET {
	public final static String MY_COIN = ID.COIN_XRP;
	
	public final static Integer CLNT_MAX_ERRCNT = 10;
	
	public static boolean ISIN_BTCMARKET;
	public static boolean ENABLED_COINONE;
	public static boolean ENABLED_BITHUMB;	
	public static boolean ENABLED_UPBIT;
	public static boolean ENABLED_COINNEST;
	public static boolean ENABLED_KORBIT;
	
	public static boolean ENABLED_BITFINEX;
	public static boolean ENABLED_BITTREX;
	public static boolean ENABLED_POLONIEX;
	public static boolean ENABLED_BINANCE;
	
	public static String VERSION;
	public static String EX_PRICE;
	public static String EX_TARGET;
	public static String EX_NUMBER;
	public static String EX_RATE;
	
	@Autowired
	private CoinConfigService coinConfigService;

	@PostConstruct
	public void init() {
		CoinConfigVo config 	= coinConfigService.get(MY_COIN);
		
		SET.ISIN_BTCMARKET 		= config.isInBTCMarket();
		
		SET.ENABLED_COINONE 	= config.isEnabledCoinone();
		SET.ENABLED_BITHUMB 	= config.isEnabledBithumb();
		SET.ENABLED_UPBIT		= config.isEnabledUpbit();
		SET.ENABLED_COINNEST	= config.isEnabledCoinnest();
		SET.ENABLED_KORBIT 		= config.isEnabledKorbit();
		SET.ENABLED_BITFINEX	= config.isEnabledBitfinex();
		SET.ENABLED_BITTREX		= config.isEnabledBittrex();
		SET.ENABLED_POLONIEX	= config.isEnabledPoloniex();
		SET.ENABLED_BINANCE		= config.isEnabledBinance();
		
		SET.VERSION		= config.getVersion();
		SET.EX_PRICE 	= config.getPriceEx();
		SET.EX_NUMBER	= config.getNumberEx();
		SET.EX_TARGET	= config.getTargetEx();
		SET.EX_RATE		= "5%";
		
	}
}
