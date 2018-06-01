package com.cglee079.cointelebot.cmd;

import com.cglee079.cointelebot.constants.ID;

public class CMDER {
	public static String getMainCurrentPrice(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_CURRENT_PRICE ; break;
		case ID.LANG_US : str = CMD_US.MAIN_CURRENT_PRICE ; break;
		}
		return str;
	}

	public static String getMainKoreaPremium(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_KOREA_PREMIUM ; break;
		case ID.LANG_US : str = CMD_US.MAIN_KOREA_PREMIUM ; break;
		}
		return str;
	}

	public static String getMainBtc(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_BTC ; break;
		case ID.LANG_US : str = CMD_US.MAIN_BTC ; break;
		}
		return str;
	}

	public static String getMainCalculate(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_CALCULATE ; break;
		case ID.LANG_US : str = CMD_US.MAIN_CALCULATE ; break;
		}
		return str;
	}

	public static String getMainHelp(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_HELP ; break;
		case ID.LANG_US : str = CMD_US.MAIN_HELP ; break;
		}
		return str;
	}

	public static String getMainSupport(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_SUPPORT ; break;
		case ID.LANG_US : str = CMD_US.MAIN_SUPPORT ; break;
		}
		return str;
	}

	public static String getMainSetPrice(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_SET_PRICE ; break;
		case ID.LANG_US : str = CMD_US.MAIN_SET_PRICE ; break;
		}
		return str;
	}

	public static String getMainSetNumber(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_SET_NUMBER ; break;
		case ID.LANG_US : str = CMD_US.MAIN_SET_NUMBER ; break;
		}
		return str;
	}

	public static String getMainSetTarget(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_SET_TARGET ; break;
		case ID.LANG_US : str = CMD_US.MAIN_SET_TARGET ; break;
		}
		return str;
	}

	public static String getMainSetMarket(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_SET_MARKET ; break;
		case ID.LANG_US : str = CMD_US.MAIN_SET_MARKET ; break;
		}
		return str;
	}

	public static String getMainSetTimeloop(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_SET_TIMELOOP ; break;
		case ID.LANG_US : str = CMD_US.MAIN_SET_TIMELOOP ; break;
		}
		return str;
	}

	public static String getMainSetDayloop(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_SET_DAYLOOP ; break;
		case ID.LANG_US : str = CMD_US.MAIN_SET_DAYLOOP ; break;
		}
		return str;
	}

	public static String getMainSendMsg(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_SEND_MSG ; break;
		case ID.LANG_US : str = CMD_US.MAIN_SEND_MSG ; break;
		}
		return str;
	}

	public static String getMainHappyLine(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_HAPPY_LINE ; break;
		case ID.LANG_US : str = CMD_US.MAIN_HAPPY_LINE ; break;
		}
		return str;
	}

	public static String getMainInfo(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_INFO ; break;
		case ID.LANG_US : str = CMD_US.MAIN_INFO ; break;
		}
		return str;
	}

	public static String getMainStop(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_STOP ; break;
		case ID.LANG_US : str = CMD_US.MAIN_STOP ; break;
		}
		return str;
	}

	
	public static String getMainPref(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_PREFERENCE ; break;
		case ID.LANG_US : str = CMD_US.MAIN_PREFERENCE ; break;
		}
		return str;
	}
	
	public static String getMainCoinList(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.MAIN_COIN_LIST ; break;
		case ID.LANG_US : str = CMD_US.MAIN_COIN_LIST ; break;
		}
		return str;
	}
	
	public static String getSetMarket(String market, String lang) {
		String str = "";
		switch(market) {
		case ID.MARKET_COINONE 	: str = getSetMarketCoinone(lang);break;
		case ID.MARKET_BITHUMB 	: str = getSetMarketBithumb(lang);break;
		case ID.MARKET_UPBIT 	: str = getSetMarketUpbit(lang);break;
		case ID.MARKET_COINNEST : str = getSetMarketCoinnest(lang);break;
		case ID.MARKET_KORBIT 	: str = getSetMarketKorbit(lang);break;
		case ID.MARKET_BITFINEX : str = getSetMarketBitfinex(lang);break;
		case ID.MARKET_BITTREX 	: str = getSetMarketBittrex(lang);break;
		case ID.MARKET_POLONIEX : str = getSetMarketPoloniex(lang);break;
		case ID.MARKET_BINANCE 	: str = getSetMarketBinance(lang);break;
		case ID.MARKET_HUOBI 	: str = getSetMarketHuobi(lang);break;
		case ID.MARKET_HADAX 	: str = getSetMarketHadax(lang);break;
		case ID.MARKET_OKEX 	: str = getSetMarketOkex(lang);break;
		}
		return str;
	}
	
	public static String getSetMarketCoinone(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_MARKET_COINONE; break;
		case ID.LANG_US : str = CMD_US.SET_MARKET_COINONE; break;
		}
		return str;
	}

	public static String getSetMarketBithumb(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_MARKET_BITHUMB ; break;
		case ID.LANG_US : str = CMD_US.SET_MARKET_BITHUMB ; break;
		}
		return str;
	}

	public static String getSetMarketUpbit(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_MARKET_UPBIT ; break;
		case ID.LANG_US : str = CMD_US.SET_MARKET_UPBIT ; break;
		}
		return str;
	}

	public static String getSetMarketCoinnest(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_MARKET_COINNEST; break;
		case ID.LANG_US : str = CMD_US.SET_MARKET_COINNEST; break;
		}
		return str;
	}

	public static String getSetMarketKorbit(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_MARKET_KORBIT; break;
		case ID.LANG_US : str = CMD_US.SET_MARKET_KORBIT; break;
		}
		return str;
	}
	
	public static String getSetMarketBitfinex(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_MARKET_BITFINEX; break;
		case ID.LANG_US : str = CMD_US.SET_MARKET_BITFINEX; break;
		}
		return str;
	}
	
	public static String getSetMarketBittrex(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_MARKET_BITTREX; break;
		case ID.LANG_US : str = CMD_US.SET_MARKET_BITTREX; break;
		}
		return str;
	}
	
	public static String getSetMarketPoloniex(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_MARKET_POLONIEX; break;
		case ID.LANG_US : str = CMD_US.SET_MARKET_POLONIEX; break;
		}
		return str;
	}

	public static String getSetMarketBinance(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_MARKET_BINANCE; break;
		case ID.LANG_US : str = CMD_US.SET_MARKET_BINANCE; break;
		}
		return str;
	}
	
	public static String getSetMarketHuobi(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_MARKET_HUOBI; break;
		case ID.LANG_US : str = CMD_US.SET_MARKET_HUOBI; break;
		}
		return str;
	}
	
	public static String getSetMarketHadax(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_MARKET_HADAX; break;
		case ID.LANG_US : str = CMD_US.SET_MARKET_HADAX; break;
		}
		return str;
	}
	
	public static String getSetMarketOkex(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_MARKET_OKEX; break;
		case ID.LANG_US : str = CMD_US.SET_MARKET_OKEX; break;
		}
		return str;
	}
	
	public static String getSetMarketOut(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_MARKET_OUT; break;
		case ID.LANG_US : str = CMD_US.SET_MARKET_OUT; break;
		}
		return str;
	}

	public static String getSetDayloopOff(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_DAYLOOP_OFF; break;
		case ID.LANG_US : str = CMD_US.SET_DAYLOOP_OFF; break;
		}
		return str;
	}

	public static String getSetDayloop01(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_DAYLOOP_01; break;
		case ID.LANG_US : str = CMD_US.SET_DAYLOOP_01; break;
		}
		return str;
	}

	public static String getSetDayloop02(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_DAYLOOP_02; break;
		case ID.LANG_US : str = CMD_US.SET_DAYLOOP_02; break;
		}
		return str;
	}

	public static String getSetDayloop03(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_DAYLOOP_03; break;
		case ID.LANG_US : str = CMD_US.SET_DAYLOOP_03; break;
		}
		return str;
	}

	public static String getSetDayloop04(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_DAYLOOP_04; break;
		case ID.LANG_US : str = CMD_US.SET_DAYLOOP_04; break;
		}
		return str;
	}

	public static String getSetDayloop05(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_DAYLOOP_05; break;
		case ID.LANG_US : str = CMD_US.SET_DAYLOOP_05; break;
		}
		return str;
	}

	public static String getSetDayloop06(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_DAYLOOP_06; break;
		case ID.LANG_US : str = CMD_US.SET_DAYLOOP_06; break;
		}
		return str;
	}

	public static String getSetDayloop07(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_DAYLOOP_07; break;
		case ID.LANG_US : str = CMD_US.SET_DAYLOOP_07; break;
		}
		return str;
	}

	public static String getSetDayloopOut(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_DAYLOOP_OUT; break;
		case ID.LANG_US : str = CMD_US.SET_DAYLOOP_OUT; break;
		}
		return str;
	}

	public static String getSetTimeloopOff(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_TIMELOOP_OFF; break;
		case ID.LANG_US : str = CMD_US.SET_TIMELOOP_OFF; break;
		}
		return str;
	}

	public static String getSetTimeloop01(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_TIMELOOP_01; break;
		case ID.LANG_US : str = CMD_US.SET_TIMELOOP_01; break;
		}
		return str;
	}

	public static String getSetTimeloop02(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_TIMELOOP_02; break;
		case ID.LANG_US : str = CMD_US.SET_TIMELOOP_02 ; break;
		}
		return str;
	}

	public static String getSetTimeloop03(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_TIMELOOP_03; break;
		case ID.LANG_US : str = CMD_US.SET_TIMELOOP_03; break;
		}
		return str;
	}

	public static String getSetTimeloop04(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_TIMELOOP_04; break;
		case ID.LANG_US : str = CMD_US.SET_TIMELOOP_04; break;
		}
		return str;
	}

	public static String getSetTimeloop05(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_TIMELOOP_05; break;
		case ID.LANG_US : str = CMD_US.SET_TIMELOOP_05; break;
		}
		return str;
	}

	public static String getSetTimeloop06(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_TIMELOOP_06; break;
		case ID.LANG_US : str = CMD_US.SET_TIMELOOP_06; break;
		}
		return str;
	}

	public static String getSetTimeloop07(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_TIMELOOP_07; break;
		case ID.LANG_US : str = CMD_US.SET_TIMELOOP_07; break;
		}
		return str;
	}

	public static String getSetTimeloop08(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_TIMELOOP_08; break;
		case ID.LANG_US : str = CMD_US.SET_TIMELOOP_08; break;
		}
		return str;
	}

	public static String getSetTimeloop09(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_TIMELOOP_09; break;
		case ID.LANG_US : str = CMD_US.SET_TIMELOOP_09; break;
		}
		return str;
	}

	public static String getSetTimeloop10(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_TIMELOOP_10; break;
		case ID.LANG_US : str = CMD_US.SET_TIMELOOP_10 ; break;
		}
		return str;
	}

	public static String getSetTimeloop11(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_TIMELOOP_11; break;
		case ID.LANG_US : str = CMD_US.SET_TIMELOOP_11; break;
		}
		return str;
	}

	public static String getSetTimeloop12(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_TIMELOOP_12; break;
		case ID.LANG_US : str = CMD_US.SET_TIMELOOP_12; break;
		}
		return str;
	}

	public static String getSetTimeloopOut(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_TIMELOOP_OUT; break;
		case ID.LANG_US : str = CMD_US.SET_TIMELOOP_OUT ; break;
		}
		return str;
	}

	public static String getSendMsgOut(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SEND_MSG_OUT; break;
		case ID.LANG_US : str = CMD_US.SEND_MSG_OUT ; break;
		}
		return str;
	}

	public static String getConfirmStopYes(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.CONFIRM_STOP_YES ; break;
		case ID.LANG_US : str = CMD_US.CONFIRM_STOP_YES ; break;
		}
		return str;
	}

	public static String getConfirmStopNo(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.CONFIRM_STOP_NO; break;
		case ID.LANG_US : str = CMD_US.CONFIRM_STOP_NO ; break;
		}
		return str;
	}

	public static String getOut(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.OUT; break;
		case ID.LANG_US : str = CMD_US.OUT; break;
		}
		return str;
	}
	
	
	public static String getPrefLang(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.PREF_LANG ; break;
		case ID.LANG_US : str = CMD_US.PREF_LANG; break;
		}
		return str;
	}

	public static String getPrefTimeAjdust(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.PREF_TIME_ADJUST; break;
		case ID.LANG_US : str = CMD_US.PREF_TIME_ADJUST; break;
		}
		return str;
	}
	
	public static String getPrefOut(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.PREF_OUT; break;
		case ID.LANG_US : str = CMD_US.PREF_OUT; break;
		}
		return str;
	}
	
	public static String getSetLanguageKR(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_LANG_KOREAN; break;
		case ID.LANG_US : str = CMD_US.SET_LANG_KOREAN; break;
		}
		return str;
	}

	public static String getSetLanguageUS(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_LANG_ENGLISH; break;
		case ID.LANG_US : str = CMD_US.SET_LANG_ENGLISH; break;
		}
		return str;
	}

	public static String getSetLanguageOut(String lang) {
		String str = "";
		switch(lang){
		case ID.LANG_KR : str = CMD_KR.SET_LANG_OUT; break;
		case ID.LANG_US : str = CMD_US.SET_LANG_OUT; break;
		}
		return str;
	}
}
