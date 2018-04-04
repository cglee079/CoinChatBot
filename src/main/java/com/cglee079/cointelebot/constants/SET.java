package com.cglee079.cointelebot.constants;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cglee079.cointelebot.model.CoinInfoVo;
import com.cglee079.cointelebot.service.CoinInfoService;

@Component
public class SET {
	public final static String MY_COIN = ID.COIN_TRX;
	
	public final static Integer CLNT_MAX_ERRCNT = 10;
	
	public static boolean ENABLED_COINONE;
	public static boolean ENABLED_BITHUMB;	
	public static boolean ENABLED_UPBIT;
	public static boolean ENABLED_COINNEST;
	public static boolean ENABLED_KORBIT;
	
	public static boolean ENABLED_BITFINEX;
	public static boolean ENABLED_BITTREX;
	public static boolean ENABLED_POLONIEX;
	
	public static String COIN_NAME;
	public static String VERSION;
	public static String EX_PRICE;
	public static String EX_TARGET;
	public static String EX_NUMBER;
	public static String EX_RATE;
	
	@Autowired
	private CoinInfoService coinInfoService;

	@PostConstruct
	public void init() {
		CoinInfoVo coinInfo 	= coinInfoService.get(MY_COIN);
		SET.ENABLED_COINONE 	= coinInfo.isEnabledCoinone();
		SET.ENABLED_BITHUMB 	= coinInfo.isEnabledBithumb();
		SET.ENABLED_UPBIT		= coinInfo.isEnabledUpbit();
		SET.ENABLED_COINNEST	= coinInfo.isEnabledCoinnest();
		SET.ENABLED_KORBIT 		= coinInfo.isEnabledKorbit();
		SET.ENABLED_BITFINEX	= coinInfo.isEnabledBitfinex();
		SET.ENABLED_BITTREX		= coinInfo.isEnabledBittrex();
		SET.ENABLED_POLONIEX	= coinInfo.isEnabledPoloniex();
		
		SET.COIN_NAME	= coinInfo.getCoinname();
		SET.VERSION		= coinInfo.getVersion();
		SET.EX_PRICE 	= coinInfo.getPriceEx();
		SET.EX_NUMBER	= coinInfo.getNumberEx();
		SET.EX_TARGET	= coinInfo.getTargetEx();
		SET.EX_RATE		= "5%";
	}
}
