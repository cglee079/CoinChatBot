package com.cglee079.cointelebot.telegram;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;

import com.cglee079.cointelebot.cmd.CMDER;
import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.constants.SET;
import com.cglee079.cointelebot.model.ClientVo;
import com.cglee079.cointelebot.model.CoinConfigVo;
import com.cglee079.cointelebot.model.CoinInfoVo;
import com.cglee079.cointelebot.model.CoinWalletVo;
import com.cglee079.cointelebot.model.TimelyInfoVo;
import com.cglee079.cointelebot.util.TimeStamper;

public class MessageMaker {
	private String myCoin;
	private String version;
	private String priceKREx;
	private String priceUSEx;
	private String coinCntEx;
	private String targetKREx;
	private String targetUSEx;
	private String targetRateEx;
	private int digitKRW;
	private int digitUSD;
	private int digitBTC;
	private HashMap<String, Boolean> inBtcs;
	
		
	public MessageMaker(String myCoin, CoinConfigVo config, HashMap<String, Boolean> inBtcs) {
		this.myCoin		= myCoin;
		this.inBtcs		= inBtcs;
		version			= config.getVersion();
		priceKREx 		= config.getPriceKREx();
		priceUSEx 		= config.getPriceUSEx();
		coinCntEx 		= config.getNumberEx();
		targetKREx 		= config.getTargetKREx();
		targetUSEx 		= config.getTargetUSEx();
		targetRateEx 	= config.getTargetRateEx();
		digitKRW 		= config.getDigitKRW();
		digitUSD 		= config.getDigitUSD();
		digitBTC 		= config.getDigitBTC();
	}

	/*******************/
	/*** Formatter *****/
	/*******************/
	private String toExchangeRateKRWStr(double i) {
		DecimalFormat df = new DecimalFormat("#,###"); 
		df.setMinimumFractionDigits(0);
		df.setMaximumFractionDigits(0);
		df.setPositiveSuffix("원");
		df.setNegativeSuffix("원");
		return df.format(i);
	}
	
	private String toBTCStr(double i) {
		DecimalFormat df = new DecimalFormat("#.#");
		df.setMinimumFractionDigits(digitBTC);
		df.setMaximumFractionDigits(digitBTC);
		df.setPositiveSuffix(" BTC");
		df.setNegativeSuffix(" BTC");
		return df.format(i);
	}
	
	private String toOnlyBTCMoneyStr(double i, String market) {
		String result = "";
		
		if(market.startsWith(ID.MARKET_KR)) {
			DecimalFormat df = new DecimalFormat("#,###"); 
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositiveSuffix("원");
			result = df.format(i);
		} else if(market.startsWith(ID.MARKET_US)) {
			DecimalFormat df = new DecimalFormat("#,###");
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositivePrefix("$");
			result = df.format(i);
		}
		return result;
	}
	
	private String toInvestAmountStr(double i, String market) {
		String result = "";
		
		if(market.startsWith(ID.MARKET_KR)) {
			DecimalFormat df = new DecimalFormat("#,###"); 
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositiveSuffix("원");
			result = df.format(i);
		} else if(market.startsWith(ID.MARKET_US)) {
			DecimalFormat df = new DecimalFormat("#,###");
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositivePrefix("$");
			result = df.format(i);
		}
		return result;
	}
	
	private String toSignInvestAmountStr(double i, String market) {
		String result = "";
		
		if(market.startsWith(ID.MARKET_KR)) {
			DecimalFormat df = new DecimalFormat("#,###"); 
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositivePrefix("+");
			df.setNegativePrefix("-");
			df.setPositiveSuffix("원");
			df.setNegativeSuffix("원");
			result = df.format(i);
		} else if(market.startsWith(ID.MARKET_US)) {
			DecimalFormat df = new DecimalFormat("#,###");
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositivePrefix("+$");
			df.setNegativePrefix("-$");
			result = df.format(i);
		}
		return result;
	}
	
	private String toMoneyStr(double i, String market){
		String result = "";
		if(market.startsWith(ID.MARKET_KR)) { result = toKRWStr(i);}
		if(market.startsWith(ID.MARKET_US)) { result = toUSDStr(i);}
		return result;
	}
	
	private String toSignMoneyStr(double i, String market) {
		String result = "";
		if(market.startsWith(ID.MARKET_KR)) { result = toSignKRWStr(i);}
		if(market.startsWith(ID.MARKET_US)) { result = toSignUSDStr(i);}
		return result;
	}
	
	private String toKRWStr(double i){
		DecimalFormat df = new DecimalFormat("#,###.#"); 
		df.setMinimumFractionDigits(digitKRW);
		df.setMaximumFractionDigits(digitKRW);
		df.setPositiveSuffix("원");
		df.setNegativeSuffix("원");
		return df.format(i);
	}
	
	private String toSignKRWStr(double i) {
		DecimalFormat df = new DecimalFormat("#,###.#");
		df.setMinimumFractionDigits(digitKRW);
		df.setMaximumFractionDigits(digitKRW);
		df.setPositivePrefix("+");
		df.setNegativePrefix("-");
		df.setPositiveSuffix("원");
		df.setNegativeSuffix("원");
		return df.format(i);
	}
	
	private String toUSDStr(double d){
		DecimalFormat df = new DecimalFormat("#.#");
		df.setMinimumFractionDigits(digitUSD);
		df.setMaximumFractionDigits(digitUSD);
		df.setPositivePrefix("$");
		return df.format(d);
	}
	
	private String toSignUSDStr(double d){
		DecimalFormat df = new DecimalFormat("#.#");
		df.setMinimumFractionDigits(digitUSD);
		df.setMaximumFractionDigits(digitUSD);
		df.setPositivePrefix("+$");
		df.setNegativePrefix("-$");
		return df.format(d);
	}
	
	private String toVolumeStr(long i) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(i);
	}
	
	private String toSignVolumeStr(long i) {
		DecimalFormat df = new DecimalFormat("#,###");
		df.setPositivePrefix("+");
		df.setNegativePrefix("-");
		return df.format(i);
	}
	
	private String toCoinCntStr(double i, String lang) {
		DecimalFormat df = new DecimalFormat("#.##");
		if(lang.equals(ID.LANG_KR)) {
			df.setPositiveSuffix("개");
			df.setNegativeSuffix("개");
		} else if(lang.equals(ID.LANG_US)) {
			df.setPositiveSuffix(" COIN");
			df.setNegativeSuffix(" COIN");
		}
		
		return df.format(i);
	}
	
	
	public String toSignKimpStr(double d){
		DecimalFormat df = new DecimalFormat("#.##");
		df.setPositivePrefix("+");
		df.setNegativePrefix("-");
		return df.format(d);
	}
	
	public String toSignPercent(double c, double b){
		String prefix = "";
		double gap = c - b;
		double percent = (gap / b) * 100;
		if (percent > 0) {
			prefix = "+";
		}
		DecimalFormat df = new DecimalFormat("#.##");
		return prefix + df.format(percent) + "%";
	}

	private String toMarketStr(String marketID, String lang) {
		String market = "";
		if(lang.equals(ID.LANG_KR)) {
			switch(marketID) {
			case ID.MARKET_COINONE 		: market = "코인원"; break;
			case ID.MARKET_BITHUMB 		: market = "빗썸"; break;
			case ID.MARKET_UPBIT 		: market = "업비트"; break;
			case ID.MARKET_COINNEST 	: market = "코인네스트"; break;
			case ID.MARKET_KORBIT 		: market = "코빗"; break;
			case ID.MARKET_GOPAX 		: market = "고팍스"; break;
			case ID.MARKET_BITFINEX 	: market = "비트파이넥스"; break;
			case ID.MARKET_BITTREX 		: market = "비트렉스"; break;
			case ID.MARKET_POLONIEX 	: market = "폴로닉스"; break;
			case ID.MARKET_BINANCE 		: market = "바이낸스"; break;
			case ID.MARKET_HUOBI 		: market = "후오비"; break;
			case ID.MARKET_HADAX 		: market = "하닥스"; break;
			case ID.MARKET_OKEX 		: market = "오케이엑스"; break;
			}
		} else if(lang.equals(ID.LANG_US)) {
			switch(marketID) {
			case ID.MARKET_COINONE 		: market = "Coinone"; break;
			case ID.MARKET_BITHUMB 		: market = "Bithumb"; break;
			case ID.MARKET_UPBIT 		: market = "Upbit"; break;
			case ID.MARKET_COINNEST 	: market = "Coinnest"; break;
			case ID.MARKET_KORBIT 		: market = "Korbit"; break;
			case ID.MARKET_GOPAX 		: market = "Gopax"; break;
			case ID.MARKET_BITFINEX 	: market = "Bitfinex"; break;
			case ID.MARKET_BITTREX 		: market = "Bittrex"; break;
			case ID.MARKET_POLONIEX 	: market = "Poloniex"; break;
			case ID.MARKET_BINANCE 		: market = "Binance"; break;
			case ID.MARKET_HUOBI 		: market = "Huobi"; break;
			case ID.MARKET_HADAX 		: market = "Hadax"; break;
			case ID.MARKET_OKEX 		: market = "OKEx"; break;
			}
		}
		return market;
	}
	
	/********************/
	/** Common Message **/
	/********************/
	public String warningNeedToStart(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "알림을 먼저 시작해주세요. \n 명령어 /start  <<< 클릭!.\n"; break;
		case ID.LANG_US : msg = "Please start this service first.\n /start <<< click here.\n"; break; 
		}
		return msg;
	}
	
	public String warningWaitSecond(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "잠시 후 다시 보내주세요.\n"; break;
		case ID.LANG_US : msg = "Please send message again after a while.\n"; break; 
		}
		return msg;
	}
	
	
	public String msgToMain(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "\n# 메인화면으로 이동합니다.\n"; break;
		case ID.LANG_US : msg = "\n# Changed to main menu.\n"; break; 
		}
		return msg;
	}
	
	public String msgPleaseSetInvestmentAmount(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "먼저 투자금액을 설정해주세요.\n메뉴에서 '" + CMDER.getMainSetPrice(lang)  + "' 을 클릭해주세요."; break;
		case ID.LANG_US : msg = "Please set your investment amount first.\nPlease Click '" + CMDER.getMainSetPrice(lang)  + "' on the main menu."; break; 
		}
		return msg;
	}
	
	public String msgPleaseSetTheNumberOfCoins(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "먼저 코인개수를 설정해주세요.\n메뉴에서 '" + CMDER.getMainSetNumber(lang)  + "' 을 클릭해주세요."; break;
		case ID.LANG_US : msg = "Please set the number of coins first.\nPlease Click '" + CMDER.getMainSetNumber(lang)  + "' on the main menu."; break; 
		}
		return msg;
	}
	
	/*******************/
	/** Start Message **/
	/*******************/
	public String msgStartService(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = myCoin + " 알림이 시작되었습니다.\n\n"; break;
		case ID.LANG_US : msg = myCoin + " Noticer Start.\n\n"; break; 
		}
		return msg;
	}

	public String msgAlreadyStartService(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "이미 " + myCoin + " 알리미에 설정 정보가 기록되어있습니다.\n"; break;
		case ID.LANG_US : msg = "Already " + myCoin + " Noticer Started.\n Database have your setting information.\n"; break; 
		}
		return msg;
	}

	/*****************************/
	/** Current Price Message ****/
	/*****************************/
	public String msgCurrentPrice(double currentValue, JSONObject coinMoney, ClientVo client) {
		String msg = "";
		String lang 	= client.getLang();
		String market 	= client.getMarket();
		String date		= TimeStamper.getDateTime(client.getLocalTime());
		
		switch(lang) {
		case ID.LANG_KR :
			msg += "현재시각 : " +date + "\n";
			if(inBtcs.get(market)) {
				double currentMoney = coinMoney.getDouble("last");
				double currentBTC = currentValue;
				msg += "현재가격 : " + toMoneyStr(currentMoney, market) + " [" + toBTCStr(currentBTC) + "]\n";
			} else {
				msg += "현재가격 : " + toMoneyStr(currentValue, market) + "\n";
			}
			break;
			
		case ID.LANG_US :
			msg += "Current Time  : " + date + "\n";
			if(inBtcs.get(market)) {
				double currentMoney = coinMoney.getDouble("last");
				double currentBTC = currentValue;
				msg += "Current Price : " + toMoneyStr(currentMoney, market) + " [" + toBTCStr(currentBTC) + "]\n";
			} else {
				msg += "Current Price : " + toMoneyStr(currentValue, market) + "\n";
			}
			break; 
		}
		return msg;
	}
	
	/**********************************/
	/** Each Market Price Message *****/
	/**********************************/
	public String msgEachMarketPrice(double exchangeRate, LinkedHashMap<String, Double> lasts, ClientVo client) {
		String msg 		= "";
		String market 	= client.getMarket();
		String lang 	= client.getLang();
		String date		= TimeStamper.getDateTime(client.getLocalTime());
		double mylast 	= lasts.get(market);
		
		switch(lang) {
		case ID.LANG_KR :
			msg += "현재 시각  : "  + date + "\n";
			msg += "\n";
			msg += "나의 거래소 : "  + toMarketStr(market, lang) + "\n";
			msg += "금일의 환율 : $1 = " + toExchangeRateKRWStr(exchangeRate) + "\n";
			msg += "----------------------------\n";
			break;
		case ID.LANG_US :
			msg += "Current Time  : "  + date + "\n";
			msg += "\n";
			msg += "My Market     : "  + toMarketStr(market, lang) + "\n";
			msg += "Exchange rate : $1 = " + toKRWStr(exchangeRate) + "\n";
			msg += "----------------------------\n";
			break; 
		}
		
		Iterator<String> iter = lasts.keySet().iterator();
		
		if(market.startsWith(ID.MARKET_KR)) {
			while(iter.hasNext()) {
				String key = iter.next();
				if(key.equals(market)) {
					msg += "★ ";
					msg += toMarketStr(market, lang) + "  : ";
					if(lasts.get(key) == -1) {
						msg += "Server Error";
					} else { 
						msg += toMoneyStr(lasts.get(market), market);
						msg += "  [" + toMoneyStr(lasts.get(key)/ exchangeRate, ID.MARKET_US) + "]";
					}
					msg += "\n";
				} else {
					msg += toMarketStr(key, lang) + "  : ";
					if(lasts.get(key) == -1) {
						msg += "Server Error";
					} else { 
						if(key.startsWith(ID.MARKET_KR)) {
							msg += toMoneyStr(lasts.get(key), ID.MARKET_KR);
							msg += "  [" + toMoneyStr(lasts.get(key)/ exchangeRate, ID.MARKET_US) + "]";
						} else if(key.startsWith(ID.MARKET_US)){
							msg += toMoneyStr(lasts.get(key) * exchangeRate, ID.MARKET_KR);
							msg += "  [" + toMoneyStr(lasts.get(key), ID.MARKET_US) + "]";
							msg += " ( P. " + toSignPercent(mylast,  lasts.get(key) * exchangeRate ) + ") ";
						}
					}
					msg += "\n";
				}
			}
		}
		
		else if(market.startsWith(ID.MARKET_US)) {
			while(iter.hasNext()) {
				String key = iter.next();
				if(key.equals(market)) {
					msg += "★ ";
					msg += toMarketStr(market, lang) + "  : ";
					if(lasts.get(key) == -1) {
						msg += "Server Error";
					} else { 
						msg += toMoneyStr(lasts.get(market), market);
						msg += "  [" + toMoneyStr(lasts.get(key) * exchangeRate, ID.MARKET_KR) + "]";
					}
					msg += "\n";
					
				} else {
					msg += toMarketStr(key, lang) + "  : ";
					if(lasts.get(key) == -1) {
						msg += "Server Error";
					} else { 
						if(key.startsWith(ID.MARKET_KR)) {
							msg += toMoneyStr(lasts.get(key) / exchangeRate, ID.MARKET_US);
							msg += "  [" + toMoneyStr(lasts.get(key), ID.MARKET_KR) + "]";
						} else if(key.startsWith(ID.MARKET_US)) {
							msg += toMoneyStr(lasts.get(key), ID.MARKET_US);
							msg += "  [" + toMoneyStr(lasts.get(key) * exchangeRate, ID.MARKET_KR) + "]";
						}
					}
					msg += "\n";
				}
			}
		}
		return msg;
	}
	
	/******************************/
	/** BTC change rate Message*****/
	/******************************/
	public String msgBTCCurrentTime(String date, String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg += "현재시각 : " + date + "\n"; break;
		case ID.LANG_US : msg += "Current Time : " + date + "\n"; break; 
		}
		
		msg += "----------------------\n";
		msg += "\n";
		return msg;
	}
	
	public String msgBTCNotSupportAPI(String marketID, String lang) {
		String market = this.toMarketStr(marketID, lang);
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = market + " API는 해당 정보를 제공하지 않습니다.\n"; break;
		case ID.LANG_US : msg = market + " market API does not provide this information.\n"; break; 
		}
		return msg;
	}

	public String msgBTCReplaceAnotherMarket(String marketID, String lang) {
		String market = this.toMarketStr(marketID, lang);
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = market + " 기준 정보로 대체합니다.\n"; break;
		case ID.LANG_US : msg = "Replace with " + market  + " market information.\n"; break; 
		}
		return msg;
	}
	
	public String msgBTCResult(double coinCV, double coinBV, double btcCV, double btcBV, JSONObject coinMoney, String market, String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR :
			msg += "BTC 현재 시각 가격 : " + toOnlyBTCMoneyStr(btcCV, market) +"\n";
			msg += "BTC 24시간전 가격 : " + toOnlyBTCMoneyStr(btcBV, market) +"\n";
			msg += "\n";
			if(inBtcs.get(market)) {
				msg += myCoin + " 현재 시각 가격 : " + toMoneyStr(coinMoney.getDouble("last"), market) + " [" + toBTCStr(coinCV) + "]\n";
				msg += myCoin + " 24시간전 가격 : " + toMoneyStr(coinMoney.getDouble("first"), market) + " [" + toBTCStr(coinBV) + "]\n";
			} else {
				msg += myCoin + " 현재 시각 가격 : " + toMoneyStr(coinCV, market) + "\n";
				msg += myCoin + " 24시간전 가격 : " + toMoneyStr(coinBV, market) + "\n";
			}
			msg += "\n";
			msg += "BTC 24시간 변화량 : " + toSignPercent(btcCV, btcBV) + "\n";
			msg += myCoin + " 24시간 변화량 : " + toSignPercent(coinCV, coinBV) + "\n";
			break;
			
		case ID.LANG_US : 
			msg += "BTC Price at current time : " + toMoneyStr(btcCV, market) +"\n";
			msg += "BTC Price before 24 hours : " + toMoneyStr(btcBV, market) +"\n";
			msg += "\n";
			if(inBtcs.get(market)) {
				msg += myCoin + " Price at current time : " + toMoneyStr(coinMoney.getDouble("last"), market) + " [" + toBTCStr(coinCV) + "]\n";
				msg += myCoin + " Price before 24 hours : " + toMoneyStr(coinMoney.getDouble("first"), market) + " [" + toBTCStr(coinBV) + "]\n";
			} else {
				msg += myCoin + " Price at current time : " + toMoneyStr(coinCV, market) + "\n";
				msg += myCoin + " Price before 24 hours : " + toMoneyStr(coinBV, market) + "\n";
			}
			msg += "\n";
			msg += "BTC 24 hour rate of change : " + toSignPercent(btcCV, btcBV) + "\n";
			msg += myCoin + " 24 hour rate of change : " + toSignPercent(coinCV, coinBV) + "\n";
			break; 
		}
		return msg;
	}
	
	

	/**************************/
	/** Calculate Message *****/
	/**************************/
	public String msgCalcResult(double price, double cnt, double avgPrice, double coinValue, JSONObject btcObj, ClientVo client) {
		String msg = "";
		String market 	= client.getMarket();
		String lang 	= client.getLang();
		String date		= TimeStamper.getDateTime(client.getLocalTime());
		
		switch(lang) {
		case ID.LANG_KR :
			msg += "현재 시각  : "  + date + "\n";
			msg += "\n";
			msg += "코인개수 : " + toCoinCntStr(cnt, lang) + "\n";
			if(inBtcs.get(market)) {
				double btcMoney = btcObj.getDouble("last");
				double avgBTC = avgPrice / btcMoney;
				double coinBTC = coinValue;
				double coinMoney = coinValue * btcMoney;
				msg += "평균단가 : " + toMoneyStr(avgPrice, market) + "  [" + toBTCStr(avgBTC) + "]\n";
				msg += "현재단가 : " + toMoneyStr(coinMoney, market) + " [" + toBTCStr(coinBTC) + "]\n";
				msg += "---------------------\n";
				msg += "투자금액 : " + toInvestAmountStr(price, market) + "\n";
				msg += "현재금액 : " + toInvestAmountStr((int)(coinMoney * cnt), market) + "\n";
				msg += "손익금액 : " + toSignInvestAmountStr((int)((coinMoney * cnt) - (cnt * avgPrice)), market) + " (" + toSignPercent((int)(coinMoney * cnt), (int)(cnt * avgPrice)) + ")\n";
				msg += "\n";
			} else {
				double coinMoney = coinValue;
				msg += "평균단가 : " + toMoneyStr(avgPrice, market) + "\n";
				msg += "현재단가 : " + toMoneyStr(coinMoney, market)+ "\n";
				msg += "---------------------\n";
				msg += "투자금액 : " + toInvestAmountStr(price, market) + "\n";
				msg += "현재금액 : " + toInvestAmountStr((int)(coinMoney * cnt), market) + "\n";
				msg += "손익금액 : " + toSignInvestAmountStr((int)((coinMoney * cnt) - (cnt * avgPrice)), market) + " (" + toSignPercent((int)(coinMoney * cnt), (int)(cnt * avgPrice)) + ")\n";
				msg += "\n";
			}
			break;
			
		case ID.LANG_US : 
			msg += "Current Time  : "  + date + "\n";
			msg += "\n";
			msg += "The number of coins : " + toCoinCntStr(cnt, lang) + "\n";
			if(inBtcs.get(market)) {
				double btcMoney = btcObj.getDouble("last");
				double avgBTC = avgPrice / btcMoney;
				double coinBTC = coinValue;
				double coinMoney = coinValue * btcMoney;
				msg += "Average Coin Price : " + toMoneyStr(avgPrice, market) + "  [ " + toBTCStr(avgBTC) + "]\n";
				msg += "Current Coin Price : " + toMoneyStr(coinMoney, market) + " [ " + toBTCStr(coinBTC) + "]\n";
				msg += "---------------------\n";
				msg += "Investment Amount : " + toInvestAmountStr(price, market) + "\n";
				msg += "Curernt Amount : " + toInvestAmountStr((int)(coinMoney * cnt), market) + "\n";
				msg += "Profit and loss : " + toSignInvestAmountStr((int)((coinMoney * cnt) - (cnt * avgPrice)), market) + " (" + toSignPercent((int)(coinMoney * cnt), (int)(cnt * avgPrice)) + ")\n";
				msg += "\n";
			} else {
				double coinMoney = coinValue;
				msg += "Average Coin Price : " + toMoneyStr(avgPrice, market) + "\n";
				msg += "Current Coin Price : " + toMoneyStr(coinMoney, market)+ "\n";
				msg += "---------------------\n";
				msg += "Investment Amount : " + toInvestAmountStr(price, market) + "\n";
				msg += "Curernt Amount : " + toInvestAmountStr((int)(coinMoney * cnt), market) + "\n";
				msg += "Profit and loss : " + toSignInvestAmountStr((int)((coinMoney * cnt) - (cnt * avgPrice)), market) + " (" + toSignPercent((int)(coinMoney * cnt), (int)(cnt * avgPrice)) + ")\n";
				msg += "\n";
			}
			break; 
		}
		return msg;
	}
	
	/***************************/
	/** Happy Line Message *****/
	/***************************/
	public String warningHappyLineFormat(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg += "코인개수는 숫자로만 입력해주세요.\n"; break;
		case ID.LANG_US : msg += "Please enter the number of coins only in numbers.\n";break; 
		}
		return msg;
	}

	public String explainHappyLine(String market, String lang) {
		String msg = "";
		String exampleTarget = null;
		if(market.startsWith(ID.MARKET_KR)) { exampleTarget = targetKREx;}
		if(market.startsWith(ID.MARKET_US)) { exampleTarget = targetUSEx;}
		
		switch(lang) {
		case ID.LANG_KR :
			msg += "원하시는 코인가격을 입력해주세요.\n";
			msg += "희망 손익금을 확인 하실 수 있습니다.\n";
			msg += "\n";
			msg += "* 코인가격은 숫자로 입력해주세요.\n";
			msg += "* ex) " + exampleTarget + "  : 희망 코인가격 " + toMoneyStr(Double.parseDouble(exampleTarget), market) + "\n";
			msg += "\n";
			msg += "\n";
			msg += "# 메인으로 돌아가시려면 문자를 입력해주세요.\n";
			break;
		case ID.LANG_US : 
			msg += "Please enter the desired coin price.\n";
			msg += "if enter your desired coin price,  you can see expected profit and loss.\n";
			msg += "\n";
			msg += "* Please enter the coin price in numbers only.\n";
			msg += "* example) " + exampleTarget + "  : desired coin price " + toMoneyStr(Double.parseDouble(exampleTarget), market) + " set\n";
			msg += "\n";
			msg += "\n";
			msg += "# To return to main, enter a character.\n";
			break; 
		}
		
		return msg;
	}
	
	public String msgHappyLineResult(double price, double coinCnt, double happyPrice, String market, String lang) {
		double avgPrice = (double) price / coinCnt;
		String msg = "";
		
		switch(lang) {
		case ID.LANG_KR :
			msg += "코인개수 : " + toCoinCntStr(coinCnt, lang) + "\n";
			msg += "평균단가 : " + toMoneyStr(avgPrice, market) + "\n";
			msg += "희망단가 : " + toMoneyStr(happyPrice, market) + "\n";
			msg += "---------------------\n";
			msg += "투자금액 : " + toInvestAmountStr(price, market) + "\n"; 
			msg += "희망금액 : " + toInvestAmountStr((long)(happyPrice * coinCnt), market) + "\n";
			msg += "손익금액 : " + toSignInvestAmountStr((long)((happyPrice * coinCnt) - (price)), market) + " (" + toSignPercent((int)(happyPrice * coinCnt), price) + ")\n";
			break;
		case ID.LANG_US :
			msg += "The number of coins : " + toCoinCntStr(coinCnt, lang) + "\n";
			msg += "Average Coin Price  : " + toMoneyStr(avgPrice, market) + "\n";
			msg += "Desired Coin Price  : " + toMoneyStr(happyPrice, market) + "\n";
			msg += "---------------------\n";
			msg += "Investment Amount : " + toInvestAmountStr(price, market) + "\n"; 
			msg += "Desired Amount : " + toInvestAmountStr((long)(happyPrice * coinCnt), market) + "\n";
			msg += "Profit and loss : " + toSignInvestAmountStr((long)((happyPrice * coinCnt) - (price)), market) + "(" + toSignPercent((int)(happyPrice * coinCnt), price) + ")\n";
			break; 
		}
		
		return msg;
	}
	
	/*********************************/
	/** Set investment Price Message**/
	/********************************/
	public String explainSetPrice(String lang, String market) {
		String examplePrice = null;
		if(market.startsWith(ID.MARKET_KR)) { examplePrice = priceKREx;}
		if(market.startsWith(ID.MARKET_US)) { examplePrice = priceUSEx;}
		
		String msg = "";
		switch(lang) {
		case ID.LANG_KR :
			msg += "투자금액을 입력해주세요.\n";
			msg += "투자금액과 코인개수를 입력하시면 손익금을 확인 하실 수 있습니다.\n";
			msg += "\n";
			msg += "* 투자금액은 숫자로만 입력해주세요.\n";
			msg += "* 0을 입력하시면 초기화됩니다.\n";
			msg += "* ex) " + 0 + " : 초기화\n";
			msg += "* ex) " + examplePrice + " : 투자금액 " + toInvestAmountStr(Double.parseDouble(examplePrice), market) + " 설정\n";
			msg += "\n";
			msg += "\n";
			msg += "# 메인으로 돌아가시려면 문자를 입력해주세요.\n";
			break;
		case ID.LANG_US : 
			msg += "Please enter your investment amount.\n";
			msg += "If you enter the amount of investment and the number of coins, you can see profit and loss.\n";
			msg += "\n";
			msg += "* Please enter the investment amount in numbers only.\n";
			msg += "* If you enter 0, it is initialized.\n";
			msg += "* example) " + 0 + " : Init investment amount\n";
			msg += "* example) " + examplePrice + " : investment amount " + toInvestAmountStr(Double.parseDouble(examplePrice), market) + " set\n";
			msg += "\n";
			msg += "\n";
			msg += "# To return to main, enter a character.\n";
			break;
		}
		return msg;
	}

	public String warningPriceFormat(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "투자금액은 숫자로만 입력해주세요.\n"; break;
		case ID.LANG_US : msg = "Please enter the investment amount only in numbers.\n"; break; 
		}
		return msg;
	}

	public String msgPriceInit(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "투자금액이 초기화 되었습니다.\n"; break;
		case ID.LANG_US : msg = "Investment Price has been init.\n"; break; 
		}
		return msg;
	}

	public String msgPriceSet(double price, String market, String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "투자금액이 " + toInvestAmountStr(price, market) + "으로 설정되었습니다.\n"; break;
		case ID.LANG_US : msg = "The investment amount is set at " + toInvestAmountStr(price, market)  + "\n"; break; 
		}
		return msg;
	}
	
	/***************************/
	/** Set Coin Count Message**/
	/***************************/
	public String explainSetCoinCount(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR :
			msg += "코인개수를 입력해주세요.\n";
			msg += "투자금액과 코인개수를 입력하시면 손익금을 확인 하실 수 있습니다.\n";
			msg += "\n";
			msg += "* 코인개수는 숫자로만 입력해주세요.\n";
			msg += "* 0을 입력하시면 초기화됩니다.\n";
			msg += "* ex) " + 0 + " : 초기화\n";
			msg += "* ex) " + coinCntEx + " : 코인개수 " + toCoinCntStr(Double.parseDouble(coinCntEx), lang) + " 설정\n";
			msg += "\n";
			msg += "\n";
			msg += "# 메인으로 돌아가시려면 문자를 입력해주세요.\n";
			break;
		case ID.LANG_US : 
			msg += "Please enter your number of coins.\n";
			msg += "If you enter the amount of investment and the number of coins, you can see profit and loss.\n";
			msg += "\n";
			msg += "* Please enter the number of coins in numbers only.\n";
			msg += "* If you enter 0, it is initialized.\n";
			msg += "* example) " + 0 + " : Init the number of coins\n";
			msg += "* example) " + coinCntEx + " : the number of coins " + toCoinCntStr(Double.parseDouble(coinCntEx), lang) + " set\n";
			msg += "\n";
			msg += "\n";
			msg += "# To return to main, enter a character.\n";
			break;
		}
		return msg;
	}
	
	public String warningCoinCountFormat(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "코인개수는 숫자로만 입력해주세요.\n"; break;
		case ID.LANG_US : msg = "Please enter the number of coins only in numbers.\n"; break; 
		}
		return msg;
	}
	
	public String msgCoinCountInit(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "코인개수가 초기화 되었습니다.\n"; break;
		case ID.LANG_US : msg = "Investment Price has been init.\n"; break; 
		}
		return msg;
	}

	public String msgCoinCountSet(double number, String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "코인개수가 " + toCoinCntStr(number, lang) + "로 설정되었습니다.\n"; break;
		case ID.LANG_US : msg = "The number of coins is set at " + toCoinCntStr(number, lang)  + "\n"; break; 
		}
		return msg;
	}

	/************************/
	/** Set Market Message **/
	/************************/
	public String explainMarketSet(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR :
			msg += "거래중인 거래소를 설정해주세요.\n";
			msg += "모든 정보는 설정 거래소 기준으로 전송됩니다.\n";
			break;
		case ID.LANG_US : 
			msg += "Please select an market.\n";
			msg += "All information will be sent on the market you selected.\n";
			break;
		}
		return msg;
	}

	public String msgMarketSet(String marketID, String lang) {
		String msg = "";
		String market = this.toMarketStr(marketID, lang);
		
		switch(lang) {
		case ID.LANG_KR : msg = "거래소가 " + market + "(으)로 설정되었습니다.\n"; break;
		case ID.LANG_US : msg = "The exchange has been set up as " + market + ".\n"; break; 
		}
		return msg;
	}

	public String msgMarketNoSet(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "거래소가 설정되지 않았습니다.\n"; break;
		case ID.LANG_US : msg = "The market has not been set up.\n"; break; 
		}
		return msg;
	}
	
	public String msgMarketSetChangeCurrency(ClientVo client, Double changePrice, Double changeTargetUp, Double changeTargetDown, String changeMarket) {
		String msg = "\n";
		String market 				= client.getMarket();
		String lang					= client.getLang();
		Double currentPrice 		= client.getPrice();
		Double currentTargetUp 		= client.getTargetUpPrice();
		Double currentTargetDown	= client.getTargetDownPrice();
		if(currentPrice != null || currentTargetUp != null || currentTargetDown != null ) {
			switch(lang) {
			case ID.LANG_KR:
				msg += "변경하신 거래소의 화폐단위가 변경되어,\n";
				msg += "설정하신 투자금액/목표가를 환율에 맞추어 변동하였습니다.\n";
				if(currentPrice != null) { msg += "투자금액 : " + toInvestAmountStr(currentPrice, market) + " -> " + toInvestAmountStr(changePrice, changeMarket) + "\n"; }
				if(currentTargetUp != null) { msg += "목표가격 : " + toMoneyStr(currentTargetUp, market) + " -> " + toMoneyStr(changeTargetUp, changeMarket) + "\n"; }
				if(currentTargetDown != null) { msg += "목표가격 : " + toMoneyStr(currentTargetDown, market) + " -> " + toMoneyStr(changeTargetDown, changeMarket) + "\n"; }
				break;
			case ID.LANG_US:
				msg += "* The currency unit of the exchange has been changed,\n";
				msg += "the investment amount / target price you set has been changed to match the exchange rate.\n";
				msg += "\n";
				if(currentPrice != null) { msg += "Investment amount : " + toInvestAmountStr(currentPrice, market) + " -> " + toInvestAmountStr(changePrice, changeMarket) + "\n"; }
				if(currentTargetUp != null) { msg += "Target Price: " + toMoneyStr(currentTargetUp, market) + " -> " + toMoneyStr(changeTargetUp, changeMarket) + "\n"; }
				if(currentTargetDown != null) { msg += "Target Price : " + toMoneyStr(currentTargetDown, market) + " -> " + toMoneyStr(changeTargetDown, changeMarket) + "\n"; }
				break;
			}
		}
		
		return msg;
	}
	
	
	
	/*****************************/
	/** Target Price Message**/
	/*****************************/
	public String explainTargetPriceSet(String lang, String market) {
		String msg = "";
		String exampleTarget = null;
		if(market.startsWith(ID.MARKET_KR)) { exampleTarget = targetKREx;}
		if(market.startsWith(ID.MARKET_US)) { exampleTarget = targetUSEx;}
		
		switch(lang) {
		case ID.LANG_KR :
			msg += "목표가격을 설정해주세요.\n";
			msg += "목표가격이 되었을 때 알림이 전송됩니다.\n";
			msg += "목표가격을 위한 가격정보는 1분 주기로 갱신됩니다.\n";
			msg += "\n";
			msg += "* 목표가격은 숫자 또는 백분율로 입력해주세요.\n";
			msg += "* ex) " + 0 + "  : 목표가격 초기화\n";
			msg += "* ex) " + exampleTarget + "  : 목표가격 " + toMoneyStr(Double.parseDouble(exampleTarget), market) + "\n";
			msg += "* ex) " + targetRateEx + "    : 현재가 +" + targetRateEx + "\n";
			msg += "* ex) -" + targetRateEx + "  : 현재가 -" + targetRateEx + "\n";
			msg += "\n";
			msg += "\n";
			msg += "# 메인으로 돌아가시려면 문자를 입력해주세요.\n";
			break;
		case ID.LANG_US :
			msg += "Please set Target Price.\n";
			msg += "Once you reach the target price, you will be notified.\n";
			msg += "Coin price information is updated every 1 minute.\n";
			msg += "\n";
			msg += "* Please enter the target price in numbers or percentages.\n";
			msg += "* If you enter 0, it is initialized.\n";
			msg += "* example)  " + 0 + "  : Initial target Price\n";
			msg += "* example)  " + exampleTarget + "  : Target price " + toMoneyStr(Double.parseDouble(exampleTarget), market)+ "\n";
			msg += "* example)  " + targetRateEx + "   : Current Price +" + targetRateEx + "\n";
			msg += "* example)  -" + targetRateEx + "  : Current Prcie -" + targetRateEx + "\n";
			msg += "\n";
			msg += "\n";
			msg += "# To return to main, enter a character.\n";
			break;
		}
		
		return msg;
	}

	public String warningTargetPriceSetPercent(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "목표가격 백분율을 -100% 이하로 설정 할 수 없습니다.\n"; break;
		case ID.LANG_US : msg = "You can not set the target price percentage below -100%.\n"; break; 
		}
		return msg;
	}
	
	public String warningTargetPriceSetFormat(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "목표가격을 숫자 또는 백분율로 입력해주세요.\n"; break;
		case ID.LANG_US : msg = "Please enter target price as a number or percentage.\n"; break; 
		}
		return msg;
	}
	
	public String msgTargetPriceInit(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "목표가격이 초기화되었습니다.\n"; break;
		case ID.LANG_US : msg = "Target price has been init.\n"; break; 
		}
		return msg;
	}
	
	public String msgTargetPriceSetResult(double targetPrice, double currentValue, String market, String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR :
			msg += "목표가격 " + toMoneyStr(targetPrice, market) + "으로 설정되었습니다.\n";
			msg += "------------------------\n";
			msg += "목표가격 : " + toMoneyStr(targetPrice, market) + "\n";
			msg += "현재가격 : " + toMoneyStr(currentValue, market) + "\n";
			msg += "가격차이 : " + toSignMoneyStr(targetPrice - currentValue, market) + "(" + toSignPercent(targetPrice, currentValue) + " )\n";
			break;
		case ID.LANG_US : 
			msg += "The target price is set at " + toMoneyStr(targetPrice, market) + ".\n";
			msg += "------------------------\n";
			msg += "Target Price       : " + toMoneyStr(targetPrice, market) + "\n";
			msg += "Current Price      : " + toMoneyStr(currentValue, market) + "\n";
			msg += "Price difference : " + toSignMoneyStr(targetPrice - currentValue, market) + " (" + toSignPercent(targetPrice, currentValue) + " )\n";
			break;
		}
		return msg;
	}

	public String msgTargetPriceNotify(double currentValue, double price, String market, String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR :
			msg += "목표가격에 도달하였습니다!\n";
			msg += "목표가격 : " + toMoneyStr(price, market) + "\n"; 
			msg += "현재가격 : " + toMoneyStr(currentValue, market) + "\n";
			break;
		case ID.LANG_US : 
			msg += "Target price reached!\n";
			msg += "Traget Price : " + toMoneyStr(price, market) + "\n"; 
			msg += "Current Price : " + toMoneyStr(currentValue, market) + "\n";
			break;
		}
		return msg;
	}
	
	/******************************/
	/** Set Daily Notification **/
	/******************************/
	public String explainSetDayloop(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : 
			msg += "일일 알림 주기를 선택해주세요.\n";
			msg += "선택 하신 일일주기로 알림이 전송됩니다.\n";
			break;
		case ID.LANG_US : 
			msg += "Please select daily notifications cycle.\n";
			msg += "Coin Price information will be sent according to the cycle.\n";
			break;
		}
		return msg;
	}

	public String msgDayloopSet(int dayloop, String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "일일 알림이 매 " +  dayloop + " 일주기로 전송됩니다.\n"; break;
		case ID.LANG_US : msg = "Daily notifications are sent every " + dayloop + " days.\n"; break; 
		}
		return msg;
	}

	public String msgDayloopNoSet(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "일일 알림 주기가 설정 되지 않았습니다.\n"; break;
		case ID.LANG_US : msg = "Daily notifications cycle is not set.\n"; break; 
		}
		return msg;
	}

	public String msgDayloopStop(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "일일 알림이 전송되지 않습니다.\n"; break;
		case ID.LANG_US : msg = "Daily notifications are not sent.\n"; break; 
		}
		return msg;
	}
	

	/*******************************/
	/** Set Hourly Notification **/
	/*******************************/
	public String explainSetTimeloop(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR :
			msg += "시간 알림 주기를 선택해주세요.\n";
			msg += "선택 하신 시간주기로 알림이 전송됩니다.\n";
			break;
		case ID.LANG_US :
			msg += "Please select hourly notifications cycle.\n";
			msg += "Coin Price information will be sent according to the cycle.\n";
			break;
		}
		return msg;
	}
	
	public String msgTimeloopSet(int timeloop, String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "시간 알림이 " +  timeloop + " 시간 주기로 전송됩니다.\n"; break;
		case ID.LANG_US : msg = "Houly notifications are sent every " + timeloop + " hours.\n"; break; 
		}
		return msg;
	}
	
	public String msgTimeloopNoSet(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "시간알림 주기가 설정 되지 않았습니다.\n"; break;
		case ID.LANG_US : msg = "Hourly notifications cycle is not set.\n"; break; 
		}
		return msg;
	}


	public String msgTimeloopStop(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = "시간 알림이 전송되지 않습니다.\n"; break;
		case ID.LANG_US : msg = "Hourly notifications are not sent.\n"; break; 
		}
		return msg;
	}
	
	/*********************/
	/** Show setting *****/
	/*********************/
	public String msgClientSetting(ClientVo client, String lang) {
		String msg = "";
		String market = client.getMarket();
		switch(lang) {
		case ID.LANG_KR :
			msg += "현재 설정은 다음과 같습니다.\n";
			msg += "-----------------\n";
			
			msg += "거래소     = ";
			msg += toMarketStr(client.getMarket(), lang) + "\n";
					
			
			if(client.getDayLoop() != 0){ msg += "일일알림 = 매 " + client.getDayLoop() + " 일 주기 알림\n";} 
			else{ msg += "일일알림 = 알람 없음\n";}
			
			if(client.getTimeLoop() != 0){ msg += "시간알림 = 매 " + client.getTimeLoop() + " 시간 주기 알림\n";} 
			else{ msg += "시간알림 = 알람 없음\n";}
			
			if(client.getTargetUpPrice() != null){msg += "목표가격 = " + toMoneyStr(client.getTargetUpPrice(), market) + "\n";}
			else if(client.getTargetDownPrice() != null){msg += "목표가격 = " + toMoneyStr(client.getTargetDownPrice(), market) + "\n";}
			else { msg += "목표가격 = 입력되어있지 않음.\n";}
			
			if(client.getPrice() != null){msg += "투자금액 = " + toInvestAmountStr(client.getPrice(), market) + "\n";}
			else { msg += "투자금액 = 입력되어있지 않음.\n";}
			
			if(client.getCoinCount() != null){msg += "코인개수 = " + toCoinCntStr(client.getCoinCount(), lang) + "\n"; }
			else { msg += "코인개수 = 입력되어있지 않음.\n";}
			
			break;
			
		case ID.LANG_US : 
			msg += "The current setting is as follows.\n";
			msg += "-----------------\n";
			
			msg += "Market = ";
			msg += toMarketStr(client.getMarket(), lang) + "\n";
			
			if(client.getDayLoop() != 0){ msg += "Daily Notification = every " + client.getDayLoop() + " days\n";} 
			else{ msg += "Daily Notification = No notifications.\n";}
			
			if(client.getTimeLoop() != 0){ msg += "Hourly Notification = every " + client.getTimeLoop() + " hours\n";} 
			else{ msg += "Hourly Notification = No notifications.\n";}
			
			if(client.getTargetUpPrice() != null){msg += "Target price = " + toMoneyStr(client.getTargetUpPrice(), market) + "\n";}
			else if(client.getTargetDownPrice() != null){msg += "Target price = " + toMoneyStr(client.getTargetDownPrice(), market) + "\n";}
			else { msg += "Target Price = Not entered.\n";}
			
			if(client.getPrice() != null){msg += "Investment amount = " + toInvestAmountStr(client.getPrice(), market) + "\n";}
			else { msg += "Investment amount = Not entered.\n";}
			
			if(client.getCoinCount() != null){msg += "The number of coins = " + toCoinCntStr(client.getCoinCount(), lang) + "\n"; }
			else { msg += "The number of coins = Not entered.\n";}
			break;
		}
		return msg;
	}
	
	
	/****************************/
	/** Stop all notifications **/
	/****************************/
	public String explainStop(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR :
			msg += "모든 알림(일일알림, 시간알림, 목표가알림)을 중지하시겠습니까?\n";
			msg += "\n";
			msg += "★ 필독!\n";
			msg += "1. 모든알림이 중지되더라도 공지사항은 전송됩니다.\n";
			msg += "2. 모든알림이 중지되더라도 버튼을 통해 코인관련정보를 받을 수 있습니다.\n";
			msg += "3. 서비스를 완전히 중지하시려면 대화방을 삭제해주세요!\n";
			break;
		case ID.LANG_US : 
			msg += "Are you sure you want to stop all notifications (daily, hourly , target price notifications )?\n";
			msg += "\n";
			msg += "★  Must read!\n";
			msg += "1. Even if all notifications have been stopped, you will continue to receive notification of service usage.\n";
			msg += "2. Even if all notifications have been stopped, you received coin information using menu.\n";
			msg += "3. If you want to stop completry this service, Please block bot.\n";
			break;
		}
		return msg;
	}
	
	public String msgStopAllNotice(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg = myCoin + " 모든알림(시간알림, 일일알림, 목표가격알림)이 중지되었습니다.\n"; break;
		case ID.LANG_US : msg = "All notifications (daily, hourly , target price notifications ) be stoped.\n"; break; 
		}
		return msg;
	}
	
	/***********************/
	/** Explain Coin List **/
	/***********************/
	public String explainCoinList(List<CoinInfoVo> coinInfos, String lang) {
		String msg = "";
		CoinInfoVo coinInfo = null;
		int coinInfosLen = coinInfos.size();
		
		switch(lang) {
		case ID.LANG_KR:
			msg += "링크를 클릭 하시면,\n";
			msg += "해당 코인알리미 봇으로 이동합니다.\n";
			
			msg += "-----------------------\n";
			for (int i = 0; i < coinInfosLen; i++) {
				coinInfo = coinInfos.get(i);
				msg += coinInfo.getCoinId() + " [" + coinInfo.getKrName() + "] \n";
				msg += coinInfo.getChatAddr() + "\n";
				msg += "\n";
			}
			msg += "\n";
			break;
		case ID.LANG_US:
			msg += "Click on the link to go to other Coin Noticer.\n";
			msg += "-----------------------\n";
			for (int i = 0; i < coinInfosLen; i++) {
				coinInfo = coinInfos.get(i);
				msg += coinInfo.getCoinId() + " [" + coinInfo.getUsName() + "] \n";
				msg += coinInfo.getChatAddr() + "\n";
				msg += "\n";
			}
			msg += "\n";
			break;
		}
	
		
		return msg;
	}
	
	/***********/
	/** Help  **/
	/***********/
	public String explainHelp(List<String> enabledMarkets, String lang) {
		String msg = "";
		if (lang.equals(ID.LANG_KR)) {
			msg += myCoin + " 알리미 ver" + version + "\n";
			msg += "\n";
			msg += "별도의 시간 알림 주기 설정을 안하셨다면,\n";
			msg += "3시간 주기로 " + myCoin + " 가격 알림이 전송됩니다.\n";
			msg += "\n";
			msg += "별도의 일일 알림 주기 설정을 안하셨다면,\n";
			msg += "1일 주기로 거래량, 상한가, 하한가, 종가가 비교되어 전송됩니다.\n";
			msg += "\n";
			msg += "별도의 거래소 설정을 안하셨다면,\n";
	
			//
			msg += toMarketStr(enabledMarkets.get(0), lang) + " 기준의 정보가 전송됩니다.\n";
			msg += "\n";
			msg += "투자금액,코인개수를 설정하시면,\n";
			msg += "원금, 현재금액, 손익금을 확인 하실 수 있습니다.\n";
			msg += "\n";
			msg += "목표가격을 설정하시면,\n";
			msg += "목표가격이 되었을때 알림을 받을 수 있습니다.\n";
			msg += "목표가격을 위한 가격정보는 각 거래소에서 1분 주기로 갱신됩니다.\n";
			msg += "\n";
			msg += "프리미엄 정보를 확인 하실 수 있습니다.\n";
			msg += "\n";
			msg += "비트코인대비 변화량을 확인 하실 수 있습니다.\n";
			msg += "\n";
			
			msg += "거래소 By ";
			for(int i = 0; i < enabledMarkets.size(); i++) {
				msg += toMarketStr(enabledMarkets.get(i), lang) + ", ";
			}
			msg += "\n";
			msg += "환율정보 By the European Central Bank\n";
			msg += "\n";
			msg += "Developed By CGLEE ( cglee079@gmail.com )\n";
		} else if(lang.equals(ID.LANG_US)) {
			msg += myCoin + " Coin Noticer Ver" + version + "\n";
			msg += "\n";
			msg += "If you are using this service for the first time,\n";
			msg += myCoin + " price are sent every 3 hours.\n";
			msg += "\n";
			msg += "If you are using this service for the first time,\n";
			msg += myCoin + " price are sent every 1 days. (with high, low, last price and volume)\n";
			msg += "\n";
			msg += "If you are using this service for the first time,\n";
			msg += "Information based on ";
			//
			String market = "";
			msg += toMarketStr(enabledMarkets.get(0), lang) + "  market will be sent.\n";
			msg += "\n";
			msg += "If you set the amount of investment and the number of coins,\n";
			msg += "you can check the current amount of profit and loss.\n";
			msg += "\n";
			msg += "If you set Target price,\n";
			msg += "Once you reach the target price, you will be notified.\n";
			msg += "Coin price information is updated every 1 minute.\n";
			msg += "\n";
			msg += "You can check the coin price on each market\n";
			msg += "\n";
			msg += "You can check coin price change rate compared to BTC\n";
			msg += "\n";
			
			msg += "* Markets : ";
			for(int i = 0; i < enabledMarkets.size(); i++) {
				msg += toMarketStr(enabledMarkets.get(i), lang) + ", ";
			}
			msg += "\n";
			msg += "* Exchange Rate Information By the European Central Bank\n";
			msg += "\n";
			msg += "Developed By CGLEE ( cglee079@gmail.com )\n";
		}
		return msg;
	}
	
	public String explainSetForeginer(String lang) {
		String msg = "";
		msg += "★  If you are not Korean, Must read!!\n";
		msg += "* Use the " + CMDER.getMainPref(ID.LANG_US) + " Menu.\n";
		msg += "* First. Please set language to English.\n";
		msg += "* Second. Set the time adjustment for accurate notifications. Because of time difference by country.\n";
//		msg += "* Last. if you set market in USA using '" +CMDER.getMainSetMarket(ID.LANG_US) + "' menu, the currency unit is changed to USD.\n";
		return msg;
	}
	
	/*******************************/
	/** Send message to developer **/
	/*******************************/
	public String explainSendSuggest(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR :
			msg += "개발자에게 내용이 전송되어집니다.\n";
			msg += "내용을 입력해주세요.\n";
			msg += "\n";
			msg += "\n";
			msg += "# 메인으로 돌아가시려면 " + CMDER.getSendMsgOut(lang) + " 를 입력해주세요.\n";
			break;
		case ID.LANG_US : 
			msg += "Please enter message.\n";
			msg += "A message is sent to the developer.\n";
			msg += "\n";
			msg += "\n";
			msg += "# To return to main, enter " + CMDER.getSendMsgOut(lang) + "\n";
			break;
		}
		return msg;
	}
	
	public String msgThankyouSuggest(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR :
			msg += "의견 감사드립니다.\n";
			msg += "성투하세요^^!\n";
			break;
		case ID.LANG_US : 
			msg += "Thank you for your suggest.\n";
			msg += "You will succeed in your investment :)!\n";
			break;
		}
		return msg;
	}

	
	/***************************/
	/*** Sponsoring Message ****/
	/***************************/
	public String explainSupport(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR :
			msg += "안녕하세요. 개발자 CGLEE 입니다.\n";
			msg += "본 서비스는 무료 서비스 임을 다시 한번 알려드리며,\n";
			msg += "절대로! 후원하지 않는다하여 사용자 여러분에게 불이익을 제공하지 않습니다.^^\n";
			msg += "\n";
			msg += "후원된 금액은 다음 용도로 소중히 사용하겠습니다\n";
			msg += "\n";
			msg += "1 순위. 서버 업그레이드 (타 코인 알리미 추가)\n";
			msg += "2 순위. 서버 운영비 (전기세...^^)\n";
			msg += "3 순위. 취업자금\n";
			msg += "4 순위. 개발보상 (치킨 냠냠)\n";
			msg += "\n";
			msg += "감사합니다.\n";
			msg += "하단에 정보를 참고하여주세요^^\n";
			break;
			
		case ID.LANG_US : 
			msg += "Hi. I'm developer CGLEE\n";
			msg += "Never! I don't offer disadvantages to users by not sponsoring. :D\n";
			msg += "\n";
			msg += "Thank you for sponsoring.\n";
			msg += "See the information below.\n";
			break; 
		}
		return msg;
	}
	
	public String explainSupportWallet(CoinWalletVo wallet, CoinWalletVo xrpWallet, String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR :
			if (wallet != null) {
				msg += "* " + wallet.getSymbol() + " [ " + wallet.getKrName() + " ]  지갑주소 : \n";
				msg += wallet.getAddr1() + "\n";
				if (myCoin.equals(ID.COIN_XRP)) {
					msg += "데스티네이션 태그 :  " + wallet.getAddr2() + "\n";
				}
			} else {
				msg += "해당 코인은 지갑을 개설 할 수 없어,\n";
				msg += "타 코인 지갑 정보를 전송합니다.\n";
				msg += "\n";

				wallet = xrpWallet;
				msg += "* " + wallet.getSymbol() + " [ " + wallet.getKrName() + " ]  지갑주소 : \n";
				msg += wallet.getAddr1() + "\n";
				msg += "데스티네이션 태그 :  " + wallet.getAddr2() + "\n";
			}
			break;
			
		case ID.LANG_US : 
			if (wallet != null) {
				msg += "* " + wallet.getSymbol() + " [ " + wallet.getUsName() + " ]  Wallet address : \n";
				msg += wallet.getAddr1() + "\n";
				if (myCoin.equals(ID.COIN_XRP)) {
					msg += "destination tag :  " + wallet.getAddr2() + "\n";
				}
			} else {
				msg += "Because this coin cannot open a wallet,\n";
				msg += "another coin wallet address send.\n";
				msg += "\n";

				wallet = xrpWallet;
				msg += "* " + wallet.getSymbol() + " [ " + wallet.getUsName() + " ]  Wallet address : \n";
				msg += wallet.getAddr1() + "\n";
				msg += "destination tag :  " + wallet.getAddr2() + "\n";
			}
			break; 
		}
		
		return msg;
	}

	public String explainSupportAN(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR :
			msg += "* 후원계좌\n";
			msg += "예금주: 이찬구\n";
			msg += "은행   : 신한은행 \n";
			msg += "번호   : 110 409 338434";
			break;
			
		case ID.LANG_US : 
			msg += "* Sponsored account\n";
			msg += "Bank   : Shinhan Bank (in Korea)\n";
			msg += "Account Holder: Lee Changoo(이찬구)\n";
			msg += "Account Number: 110 409 338434";
			break; 
		}
		
		return msg;
	}

	/*********************/
	/*** Language Set  ***/
	/*********************/
	
	public String explainSetLanguage(String lang) {
		String msg = "";
		msg += "Please select a language.";
		return msg;
	}
	
	public String msgSetLanguageSuccess(String lang) {
		String msg = "";
		switch(lang) {
		case ID.LANG_KR : msg += "언어가 한국어로 변경되었습니다.\n"; break;
		case ID.LANG_US : msg += "Language changed to English.\n"; break; 
		}
		return msg;
	}

	
	/*********************/
	/** Time Adjust  *****/
	/*********************/
	public String explainTimeAdjust(String lang) {
		String msg = "";
		msg += "한국분이시라면 별도의 시차조절을 필요로하지 않습니다.^^  <- for korean\n";
		msg += "\n";
		msg += "Please enter the current time for accurate time notification.\n";
		msg += "because the time differs for each country.\n";
		msg += "\n";
		msg += "* Please enter in the following format.\n";
		msg += "* if you entered 0, time adjust initialized.\n";
		msg += "* example) 0 : init time adjust\n";
		msg += "* example) " + TimeStamper.getDateBefore() + " 23:00 \n";
		msg += "* example) " + TimeStamper.getDate() + " 00:33 \n";
		msg += "* example) " + TimeStamper.getDate() +  " 14:30 \n";
		msg += "\n";
		msg += "\n";
		msg += "# To return to main, enter a character.\n";
		return msg;
	}
	
	public String warningTimeAdjustFormat(String lang) {
		String msg = "";
		msg += "Please type according to the format.\n";
		return msg;
	}
	
	public String msgTimeAdjustSuccess(Date date) {
		String msg = "";
		msg += "Time adjustment succeeded.\n";
		msg += "Current Time : " + TimeStamper.getDateTime(date) + "\n";
		return msg;
	}
	
//	public String msgToPreference() {
//		String msg = "";
//		msg += "\n# Changed to Preference menu\n";
//		return msg;
//	}
	
	/**********************************/
	/** Daily Notification Message ***/
	/**********************************/
	public String msgSendDailyMessage(ClientVo client, TimelyInfoVo coinCurrent, TimelyInfoVo coinBefore, JSONObject coinCurrentMoney, JSONObject coinBeforeMoney) {
		long currentVolume = coinCurrent.getVolume();
		long beforeVolume  = coinBefore.getVolume();
		double currentHigh = coinCurrent.getHigh();
		double beforeHigh = coinBefore.getHigh();
		double currentLow = coinCurrent.getLow();
		double beforeLow = coinBefore.getLow();
		double currentLast = coinCurrent.getLast();
		double beforeLast = coinBefore.getLast();
		double currentHighBTC	= 0;
		double beforeHighBTC	= 0;
		double currentLowBTC	= 0;
		double beforeLowBTC		= 0;
		double currentLastBTC 	= 0;
		double beforeLastBTC	= 0;
		
		String market 	= client.getMarket();
		String lang		= client.getLang();
		long localTime	= client.getLocalTime();
		String date 	= TimeStamper.getDateTime(localTime);
		int dayLoop 	= client.getDayLoop();

		String msg = "";
		msg += date + "\n";
		
		switch(lang) {
		case ID.LANG_KR :
			String dayLoopStr = "";
			switch(dayLoop){
			case 1 : dayLoopStr ="하루"; break;
			case 2 : dayLoopStr ="이틀"; break;
			case 3 : dayLoopStr ="삼일"; break;
			case 4 : dayLoopStr ="사일"; break;
			case 5 : dayLoopStr ="오일"; break;
			case 6 : dayLoopStr ="육일"; break;
			case 7 : dayLoopStr ="한주"; break;
			}
			
			if(inBtcs.get(market)) {
				currentHighBTC	= currentHigh;
				beforeHighBTC	= beforeHigh;
				currentLowBTC	= currentLow;
				beforeLowBTC	= beforeLow;
				currentLastBTC 	= currentLast;
				beforeLastBTC	= beforeLast;
				
				currentLast = coinCurrentMoney.getDouble("last");
				currentHigh = coinCurrentMoney.getDouble("high");
				currentLow = coinCurrentMoney.getDouble("low");
				beforeLast = coinBeforeMoney.getDouble("last");
				beforeHigh = coinBeforeMoney.getDouble("high");
				beforeLow = coinBeforeMoney.getDouble("low");
				
				msg += "---------------------\n";
				msg += "금일의 거래량 : " + toVolumeStr(currentVolume) + " \n";
				msg += dayLoopStr + "전 거래량 : " + toVolumeStr(beforeVolume) + " \n";
				msg += "거래량의 차이 : " + toSignVolumeStr(currentVolume - beforeVolume) + " (" + toSignPercent(currentVolume, beforeVolume) + ")\n";
				msg += "\n";
				
				msg += "금일의 상한가 : " + toMoneyStr(currentHigh, market) + " ["+ toBTCStr(currentHighBTC) + "]\n";
				msg += dayLoopStr + "전 상한가 : " + toMoneyStr(beforeHigh, market) + " ["+ toBTCStr(beforeHighBTC) + "]\n";
				msg += "상한가의 차이 : " + toSignMoneyStr(currentHigh - beforeHigh, market) + " (" + toSignPercent(currentHigh, beforeHigh) + ")\n";
				msg += "\n";
				
				msg += "금일의 하한가 : " + toMoneyStr(currentLow, market) + " ["+ toBTCStr(currentLowBTC) + "]\n";
				msg += dayLoopStr + "전 하한가 : " + toMoneyStr(beforeLow, market) + " ["+ toBTCStr(beforeLowBTC) + "]\n";
				msg += "하한가의 차이 : " + toSignMoneyStr(currentLow - beforeLow, market) + " (" + toSignPercent(currentLow, beforeLow) + ")\n";
				msg += "\n";
				
				msg += "금일의 종가 : " + toMoneyStr(currentLast, market) + " ["+ toBTCStr(currentLastBTC) + "]\n";
				msg += dayLoopStr + "전 종가 : " + toMoneyStr(beforeLast, market) + " ["+ toBTCStr(beforeLastBTC) + "]\n";
				msg += "종가의 차이 : " + toSignMoneyStr(currentLast - beforeLast, market) + " (" + toSignPercent(currentLast, beforeLast) + ")\n";
				msg += "\n";
			} else {
				msg += "---------------------\n";
				msg += "금일의 거래량 : " + toVolumeStr(currentVolume) + " \n";
				msg += dayLoopStr + "전 거래량 : " + toVolumeStr(beforeVolume) + " \n";
				msg += "거래량의 차이 : " + toSignVolumeStr(currentVolume - beforeVolume) + " (" + toSignPercent(currentVolume, beforeVolume) + ")\n";
				msg += "\n";
				
				msg += "금일의 상한가 : " + toMoneyStr(currentHigh, market) + "\n";
				msg += dayLoopStr + "전 상한가 : " + toMoneyStr(beforeHigh, market) + "\n";
				msg += "상한가의 차이 : " + toSignMoneyStr(currentHigh - beforeHigh, market) + " (" + toSignPercent(currentHigh, beforeHigh) + ")\n";
				msg += "\n";
				
				msg += "금일의 하한가 : " + toMoneyStr(currentLow, market) + "\n";
				msg += dayLoopStr + "전 하한가 : " + toMoneyStr(beforeLow, market) + "\n";
				msg += "하한가의 차이 : " + toSignMoneyStr(currentLow - beforeLow, market) + " (" + toSignPercent(currentLow, beforeLow) + ")\n";
				msg += "\n";
				
				
				msg += "금일의 종가 : " + toMoneyStr(currentLast, market) + "\n";
				msg += dayLoopStr + "전 종가 : " + toMoneyStr(beforeLast, market) + "\n";
				msg += "종가의 차이 : " + toSignMoneyStr(currentLast - beforeLast, market) + " (" + toSignPercent(currentLast, beforeLast) + ")\n";
				msg += "\n";
			}
			break;
			
		case ID.LANG_US : 
			if(inBtcs.get(market)) {
				currentHighBTC	= currentHigh;
				beforeHighBTC	= beforeHigh;
				currentLowBTC	= currentLow;
				beforeLowBTC	= beforeLow;
				currentLastBTC 	= currentLast;
				beforeLastBTC	= beforeLast;
				
				currentLast = coinCurrentMoney.getDouble("last");
				currentHigh = coinCurrentMoney.getDouble("high");
				currentLow = coinCurrentMoney.getDouble("low");
				beforeLast = coinBeforeMoney.getDouble("last");
				beforeHigh = coinBeforeMoney.getDouble("high");
				beforeLow = coinBeforeMoney.getDouble("low");
				
				msg += "---------------------\n";
				msg += "Volume at today : " + toVolumeStr(currentVolume) + " \n";
				msg += "Volume before " + dayLoop + " day : " + toVolumeStr(beforeVolume) + " \n";
				msg += "Volume difference : " + toSignVolumeStr(currentVolume - beforeVolume) + " (" + toSignPercent(currentVolume, beforeVolume) + ")\n";
				msg += "\n";
				msg += "High at Today : " + toMoneyStr(currentHigh, market) + " ["+ toBTCStr(currentHighBTC) + "]\n";
				msg += "High before " + dayLoop + " day : " + toMoneyStr(beforeHigh, market) + " ["+ toBTCStr(beforeHighBTC) + "]\n";
				msg += "High difference : " + toSignMoneyStr(currentHigh - beforeHigh, market) + " (" + toSignPercent(currentHigh, beforeHigh) + ")\n";
				msg += "\n";
				msg += "Low at Today : " + toMoneyStr(currentLow, market) + " ["+ toBTCStr(currentLowBTC) + "]\n";
				msg += "Low before " + dayLoop + " day : "+ toMoneyStr(beforeLow, market) + " ["+ toBTCStr(beforeLowBTC) + "]\n";
				msg += "Low difference : " + toSignMoneyStr(currentLow - beforeLow, market) + " (" + toSignPercent(currentLow, beforeLow) + ")\n";
				msg += "\n";
				msg += "Last at Today : " + toMoneyStr(currentLast, market) + " ["+ toBTCStr(currentLastBTC) + "]\n";
				msg += "Last before " + dayLoop + " day : "+ toMoneyStr(beforeLast, market) + " ["+ toBTCStr(beforeLastBTC) + "]\n";
				msg += "Last difference : " + toSignMoneyStr(currentLast - beforeLast, market) + " (" + toSignPercent(currentLast, beforeLast) + ")\n";
				msg += "\n";
			} else {
				msg += "---------------------\n";
				msg += "Volume at today : " + toVolumeStr(currentVolume) + " \n";
				msg += "Volume before " + dayLoop + " day : " + toVolumeStr(beforeVolume) + " \n";
				msg += "Volume difference : " + toSignVolumeStr(currentVolume - beforeVolume) + " (" + toSignPercent(currentVolume, beforeVolume) + ")\n";
				msg += "\n";
				msg += "High at Today : " + toMoneyStr(currentHigh, market) + "\n";
				msg += "High before " + dayLoop + " day : " + toMoneyStr(beforeHigh, market) + "\n";
				msg += "High difference : " + toSignMoneyStr(currentHigh - beforeHigh, market) + " (" + toSignPercent(currentHigh, beforeHigh) + ")\n";
				msg += "\n";
				msg += "Low at Today : " + toMoneyStr(currentLow, market) + "\n";
				msg += "Low before " + dayLoop + " day : " + toMoneyStr(beforeLow, market) + "\n";
				msg += "Low difference : " + toSignMoneyStr(currentLow - beforeLow, market) + " (" + toSignPercent(currentLow, beforeLow) + ")\n";
				msg += "\n";
				msg += "Last at Today : "  + toMoneyStr(currentLast, market) + "\n";
				msg += "Last before " + dayLoop + " day : " + toMoneyStr(beforeLast, market) + "\n";
				msg += "Last difference : " + toSignMoneyStr(currentLast - beforeLast, market) + " (" + toSignPercent(currentLast, beforeLast) + ")\n";
				msg += "\n";
			}
			break; 
		}
		
		return msg;
	}
	
	/**********************************/
	/** Timely Notification Message ***/
	/**********************************/
	public String msgSendTimelyMessage(ClientVo client, TimelyInfoVo coinCurrent, TimelyInfoVo coinBefore, JSONObject coinCurrentMoney, JSONObject coinBeforeMoney) {
		String msg 		=  "";
		String market 	= client.getMarket();
		String lang		= client.getLang();
		int timeLoop 	= client.getTimeLoop();
		long localTime	= client.getLocalTime();
		String date 	= TimeStamper.getDateTime(localTime);
		
		double currentValue = coinCurrent.getLast();
		double beforeValue = coinBefore.getLast();
		
		switch(lang) {
		case ID.LANG_KR :
			msg += "현재시각: " + date + "\n";
			if(!coinCurrent.getResult().equals("success")){
				String currentErrorMsg = coinCurrent.getErrorMsg();
				String currentErrorCode = coinCurrent.getErrorCode();
				msg += "에러발생: " + currentErrorMsg +"\n";
				msg += "에러코드: " + currentErrorCode +"\n";
				
				if(inBtcs.get(market)) {
					double beforeBTC = beforeValue;
					double beforeMoney = coinBeforeMoney.getDouble("last");
					
					msg += timeLoop + " 시간 전: " + toMoneyStr(beforeMoney, market) + " 원 [" + toBTCStr(beforeBTC) + " BTC]\n";
				} else {
					msg += timeLoop + " 시간 전: " + toMoneyStr(beforeValue, market) + " 원\n";
				}
			} else{
				if(inBtcs.get(market)) {
					double currentBTC = currentValue;
					double beforeBTC = beforeValue;
					double currentMoney = coinCurrentMoney.getDouble("last");
					double beforeMoney = coinBeforeMoney.getDouble("last");

					msg += "현재가격: " + toMoneyStr(currentMoney, market) + " [" + toBTCStr(currentBTC)+ "]\n";
					msg += timeLoop + " 시간 전: " + toMoneyStr(beforeMoney, market) + " [" + toBTCStr(beforeBTC) + "]\n";
					msg += "가격차이: " + toSignMoneyStr(currentMoney - beforeMoney, market) + " (" + toSignPercent(currentMoney, beforeMoney) + ")\n";
				} else {
					msg += "현재가격: " + toMoneyStr(currentValue, market) + "\n";
					msg += timeLoop + " 시간 전: " + toMoneyStr(beforeValue, market) + "\n";
					msg += "가격차이: " + toSignMoneyStr(currentValue - beforeValue, market) + " (" + toSignPercent(currentValue, beforeValue) + ")\n";
				}
			}
			break;
			
		case ID.LANG_US :
			msg += "Current Time: " + date + "\n";
			if(!coinCurrent.getResult().equals("success")){
				String currentErrorMsg = coinCurrent.getErrorMsg();
				String currentErrorCode = coinCurrent.getErrorCode();
				msg += "Error Msg : " + currentErrorMsg +"\n";
				msg += "Error Code: " + currentErrorCode +"\n";
				
				if(inBtcs.get(market)) {
					double beforeBTC = beforeValue;
					double beforeMoney = coinBeforeMoney.getDouble("last");
					
					msg += "Coin Price before " + timeLoop + " hour : " + toMoneyStr(beforeMoney, market) + " [" + toBTCStr(beforeBTC) + "]\n";
				} else {
					msg += "Coin Price before " + timeLoop + " hour : " + toMoneyStr(beforeValue, market) + "\n";
				}
			} else{
				if(inBtcs.get(market)) {
					double currentBTC = currentValue;
					double beforeBTC = beforeValue;
					double currentMoney = coinCurrentMoney.getDouble("last");
					double beforeMoney = coinBeforeMoney.getDouble("last");

					msg += "Coin Price at Current Time : " + toMoneyStr(currentMoney, market) + " [" + toBTCStr(currentBTC)+ "]\n";
					msg += "Coin Price before " + timeLoop + " hour : " + toMoneyStr(beforeMoney, market) + " [" + toBTCStr(beforeBTC) + "]\n";
					msg += "Coin Price Difference : " + toSignMoneyStr(currentMoney - beforeMoney, market) + " (" + toSignPercent(currentMoney, beforeMoney) + ")\n";
				} else {
					msg += "Coin Price at Current Time : " + toMoneyStr(currentValue, market) + "\n";
					msg += "Coin Price before " + timeLoop + " hour : " + toMoneyStr(beforeValue, market) + "\n";
					msg += "Coin Price Difference : " + toSignMoneyStr(currentValue - beforeValue, market) + " (" + toSignPercent(currentValue, beforeValue) + ")\n";
				}
			}
			break; 
		}
		
		return msg;
	}

	
	
	

}