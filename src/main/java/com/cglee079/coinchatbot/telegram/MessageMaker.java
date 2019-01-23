package com.cglee079.coinchatbot.telegram;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;

import com.cglee079.coinchatbot.config.cmd.MainCmd;
import com.cglee079.coinchatbot.config.cmd.MarketCmd;
import com.cglee079.coinchatbot.config.cmd.SendMessageCmd;
import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.config.id.Lang;
import com.cglee079.coinchatbot.config.id.Market;
import com.cglee079.coinchatbot.model.ClientVo;
import com.cglee079.coinchatbot.model.CoinConfigVo;
import com.cglee079.coinchatbot.model.CoinInfoVo;
import com.cglee079.coinchatbot.model.CoinWalletVo;
import com.cglee079.coinchatbot.model.TimelyInfoVo;
import com.cglee079.coinchatbot.util.TimeStamper;

public class MessageMaker {
	private Coin myCoinId;
	private String version;
	private String exInvestKR;
	private String exInvestUS;
	private String exCoinCnt;
	private String exTargetKR;
	private String exTargetUS;
	private String exTargetRate;
	private int digitKRW;
	private int digitUSD;
	private int digitBTC;
	private HashMap<Market, Boolean> inBtcs;
	
		
	public MessageMaker(Coin myCoinId, CoinConfigVo config, HashMap<Market, Boolean> inBtcs) {
		this.myCoinId		= myCoinId;
		this.inBtcs		= inBtcs;
		version			= config.getVersion();
		exInvestKR 		= config.getExInvestKRW();
		exInvestUS 		= config.getExInvestUSD();
		exCoinCnt 		= config.getExCoinCnt();
		exTargetKR 		= config.getExTargetKRW();
		exTargetUS 		= config.getExTargetUSD();
		exTargetRate 	= config.getExTargetRate();
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
	
	private String toOnlyBTCMoneyStr(double i, Market marketId) {
		String result = "";
		
		if(marketId.isKRW()) {
			DecimalFormat df = new DecimalFormat("#,###"); 
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositiveSuffix("원");
			result = df.format(i);
		} else if(marketId.isUSD()) {
			DecimalFormat df = new DecimalFormat("#,###");
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositivePrefix("$");
			result = df.format(i);
		}
		return result;
	}
	
	private String toInvestAmountStr(double i, Market marketId) {
		String result = "";
		
		if(marketId.isKRW()) {
			DecimalFormat df = new DecimalFormat("#,###"); 
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositiveSuffix("원");
			result = df.format(i);
		} else if(marketId.isUSD()) {
			DecimalFormat df = new DecimalFormat("#,###");
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositivePrefix("$");
			result = df.format(i);
		}
		return result;
	}
	
	private String toSignInvestAmountStr(double i, Market marketId) {
		String result = "";
		
		if(marketId.isKRW()) {
			DecimalFormat df = new DecimalFormat("#,###"); 
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositivePrefix("+");
			df.setNegativePrefix("-");
			df.setPositiveSuffix("원");
			df.setNegativeSuffix("원");
			result = df.format(i);
		} else if(marketId.isUSD()) {
			DecimalFormat df = new DecimalFormat("#,###");
			df.setMinimumFractionDigits(0);
			df.setMaximumFractionDigits(0);
			df.setPositivePrefix("+$");
			df.setNegativePrefix("-$");
			result = df.format(i);
		}
		return result;
	}
	
	private String toMoneyStr(double i, Market marketId){
		String result = "";
		if(marketId.isKRW()) { result = toKRWStr(i);}
		if(marketId.isUSD()) { result = toUSDStr(i);}
		return result;
	}
	
	private String toSignMoneyStr(double i, Market marketId) {
		String result = "";
		if(marketId.isKRW()) { result = toSignKRWStr(i);}
		if(marketId.isUSD()) { result = toSignUSDStr(i);}
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
	
	private String toCoinCntStr(double i, Lang lang) {
		DecimalFormat df = new DecimalFormat("#.##");
		if(lang.equals(Lang.KR)) {
			df.setPositiveSuffix("개");
			df.setNegativeSuffix("개");
		} else if(lang.equals(Lang.US)) {
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

	private String toMarketStr(Market marketId, Lang lang) {
		String marketStr = "";
		switch(lang) {
		case KR : 	marketStr = MarketCmd.from(marketId).getKr();break;
		case US : 	marketStr = MarketCmd.from(marketId).getUs();break;
		}
		return marketStr;
	}
	
	/********************/
	/** Common Message **/
	/********************/
	public String warningNeedToStart(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("알림을 먼저 시작해주세요. \n 명령어 /start  <<< 클릭!.\n"); break;
		case US : msg.append("Please start this service first.\n /start <<< click here.\n"); break; 
		}
		return msg.toString();
	}
	
	public String warningWaitSecond(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("잠시 후 다시 보내주세요.\n"); break;
		case US : msg.append("Please send message again after a while.\n"); break; 
		}
		return msg.toString();
	}
	
	
	public String msgToMain(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("\n# 메인화면으로 이동합니다.\n"); break;
		case US : msg.append("\n# Changed to main menu.\n"); break; 
		}
		return msg.toString();
	}
	
	public String msgPleaseSetInvestmentAmount(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("먼저 투자금액을 설정해주세요.\n메뉴에서 '" + MainCmd.SET_INVEST.getCmd(lang)  + "' 을 클릭해주세요."); break;
		case US : msg.append("Please set your investment amount first.\nPlease Click '" + MainCmd.SET_INVEST.getCmd(lang)  + "' on the main menu."); break; 
		}
		return msg.toString();
	}
	
	public String msgPleaseSetTheNumberOfCoins(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("먼저 코인개수를 설정해주세요.\n메뉴에서 '" + MainCmd.SET_COINCNT.getCmd(lang)  + "' 을 클릭해주세요."); break;
		case US : msg.append("Please set the number of coins first.\nPlease Click '" + MainCmd.SET_COINCNT.getCmd(lang)  + "' on the main menu."); break; 
		}
		return msg.toString();
	}
	
	/*******************/
	/** Start Message **/
	/*******************/
	public String msgStartService(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append(myCoinId + " 알림이 시작되었습니다.\n\n"); break;
		case US : msg.append(myCoinId + " Noticer Start.\n\n"); break; 
		}
		return msg.toString();
	}

	public String msgAlreadyStartService(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("이미 " + myCoinId + " 알리미에 설정 정보가 기록되어있습니다.\n"); break;
		case US : msg.append("Already " + myCoinId + " Noticer Started.\n Database have your setting information.\n"); break; 
		}
		return msg.toString();
	}

	/*****************************/
	/** Current Price Message ****/
	/*****************************/
	public String msgCurrentPrice(double currentValue, JSONObject coinMoney, ClientVo client) {
		StringBuilder msg = new StringBuilder();
		Lang lang 	= client.getLang();
		Market marketId 	= client.getMarketId();
		String date		= TimeStamper.getDateTime(client.getLocaltime());
		
		switch(lang) {
		case KR :
			msg.append("현재시각 : " +date + "\n");
			if(inBtcs.get(marketId)) {
				double currentMoney = coinMoney.getDouble("last");
				double currentBTC = currentValue;
				msg.append("현재가격 : " + toMoneyStr(currentMoney, marketId) + " [" + toBTCStr(currentBTC) + "]\n");
			} else {
				msg.append("현재가격 : " + toMoneyStr(currentValue, marketId) + "\n");
			}
			break;
			
		case US :
			msg.append("Current Time  : " + date + "\n");
			if(inBtcs.get(marketId)) {
				double currentMoney = coinMoney.getDouble("last");
				double currentBTC = currentValue;
				msg.append("Current Price : " + toMoneyStr(currentMoney, marketId) + " [" + toBTCStr(currentBTC) + "]\n");
			} else {
				msg.append("Current Price : " + toMoneyStr(currentValue, marketId) + "\n");
			}
			break; 
		}
		return msg.toString();
	}
	
	/**********************************/
	/** Each Market Price Message *****/
	/**********************************/
	public String msgEachMarketPrice(double exchangeRate, LinkedHashMap<Market, Double> lasts, ClientVo client) {
		StringBuilder msg = new StringBuilder();
		Market marketId = client.getMarketId();
		Lang lang 	= client.getLang();
		String date		= TimeStamper.getDateTime(client.getLocaltime());
		double mylast 	= lasts.get(marketId);
		
		switch(lang) {
		case KR :
			msg.append("현재 시각  : "  + date + "\n");
			msg.append("\n");
			msg.append("나의 거래소 : "  + toMarketStr(marketId, lang) + "\n");
			msg.append("금일의 환율 : $1 = " + toExchangeRateKRWStr(exchangeRate) + "\n");
			msg.append("----------------------------\n");
			break;
		case US :
			msg.append("Current Time  : "  + date + "\n");
			msg.append("\n");
			msg.append("My Market     : "  + toMarketStr(marketId, lang) + "\n");
			msg.append("Exchange rate : $1 = " + toKRWStr(exchangeRate) + "\n");
			msg.append("----------------------------\n");
			break; 
		}
		
		Iterator<Market> iter = lasts.keySet().iterator();
		
		Market marketKRW = Market.COINONE; // KRW 대표
		Market marketUSD = Market.BITFINEX; // USD 대표
		
		if(marketId.isKRW()) { // 설정된 마켓이 한화인 경우,
			while(iter.hasNext()) {
				Market key = iter.next();
				
				if(key == marketId) { // 내 마켓
					msg.append("★ ");
				} 
				
				msg.append(toMarketStr(key, lang) + "  : ");
				if(lasts.get(key) == -1) {
					msg.append("Server Error");
				} else { 
					if(key.isKRW()) {
						msg.append(toMoneyStr(lasts.get(key), marketKRW));
						msg.append("  [" + toMoneyStr(lasts.get(key)/ exchangeRate, marketUSD) + "]");
					} else if(key.isUSD()){
						msg.append(toMoneyStr(lasts.get(key) * exchangeRate, marketKRW));
						msg.append("  [" + toMoneyStr(lasts.get(key), marketUSD) + "]");
						msg.append(" ( P. " + toSignPercent(mylast,  lasts.get(key) * exchangeRate ) + ") ");
					}
				}
				msg.append("\n");
			}
		}
		
		else if(marketId.isUSD()) { // 설정된 마켓이 달러인 경우,
			while(iter.hasNext()) {
				Market key = iter.next();
				if(key == marketId) {
					msg.append("★ ");
				}
				
				msg.append(toMarketStr(key, lang) + "  : ");
				if(lasts.get(key) == -1) {
					msg.append("Server Error");
				} else { 
					if(key.isKRW()) {
						msg.append(toMoneyStr(lasts.get(key) / exchangeRate, marketUSD));
						msg.append("  [" + toMoneyStr(lasts.get(key), marketKRW) + "]");
					} else if(key.isUSD()) {
						msg.append(toMoneyStr(lasts.get(key), marketUSD));
						msg.append("  [" + toMoneyStr(lasts.get(key) * exchangeRate, marketKRW) + "]");
					}
				}
				msg.append("\n");
			}
		}
		return msg.toString();
	}
	
	/******************************/
	/** BTC change rate Message*****/
	/******************************/
	public String msgBTCCurrentTime(String date, Lang lang) {
		StringBuilder msg = new StringBuilder();
		switch(lang) {
		case KR : msg.append("현재시각 : " + date + "\n"); break;
		case US : msg.append("Current Time : " + date + "\n"); break; 
		}
		
		msg.append("----------------------\n");
		msg.append("\n");
		return msg.toString();
	}
	
	public String msgBTCNotSupportAPI(Market marketID, Lang lang) {
		String marketStr = this.toMarketStr(marketID, lang);
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append(marketStr + " API는 해당 정보를 제공하지 않습니다.\n"); break;
		case US : msg.append(marketStr + " market API does not provide this information.\n"); break; 
		}
		return msg.toString();
	}

	public String msgBTCReplaceAnotherMarket(Market marketId, Lang lang) {
		String marketStr = this.toMarketStr(marketId, lang);
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append(marketStr + " 기준 정보로 대체합니다.\n"); break;
		case US : msg.append("Replace with " + marketStr  + " market information.\n"); break; 
		}
		return msg.toString();
	}
	
	public String msgBTCResult(double coinCV, double coinBV, double btcCV, double btcBV, JSONObject coinMoney, Market marketId, Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR :
			msg.append("BTC 현재 시각 가격 : " + toOnlyBTCMoneyStr(btcCV, marketId) +"\n");
			msg.append("BTC 24시간전 가격 : " + toOnlyBTCMoneyStr(btcBV, marketId) +"\n");
			msg.append("\n");
			if(inBtcs.get(marketId)) {
				msg.append(myCoinId + " 현재 시각 가격 : " + toMoneyStr(coinMoney.getDouble("last"), marketId) + " [" + toBTCStr(coinCV) + "]\n");
				msg.append(myCoinId + " 24시간전 가격 : " + toMoneyStr(coinMoney.getDouble("first"), marketId) + " [" + toBTCStr(coinBV) + "]\n");
			} else {
				msg.append(myCoinId + " 현재 시각 가격 : " + toMoneyStr(coinCV, marketId) + "\n");
				msg.append(myCoinId + " 24시간전 가격 : " + toMoneyStr(coinBV, marketId) + "\n");
			}
			msg.append("\n");
			msg.append("BTC 24시간 변화량 : " + toSignPercent(btcCV, btcBV) + "\n");
			msg.append(myCoinId + " 24시간 변화량 : " + toSignPercent(coinCV, coinBV) + "\n");
			break;
			
		case US : 
			msg.append("BTC Price at current time : " + toMoneyStr(btcCV, marketId) +"\n");
			msg.append("BTC Price before 24 hours : " + toMoneyStr(btcBV, marketId) +"\n");
			msg.append("\n");
			if(inBtcs.get(marketId)) {
				msg.append(myCoinId + " Price at current time : " + toMoneyStr(coinMoney.getDouble("last"), marketId) + " [" + toBTCStr(coinCV) + "]\n");
				msg.append(myCoinId + " Price before 24 hours : " + toMoneyStr(coinMoney.getDouble("first"), marketId) + " [" + toBTCStr(coinBV) + "]\n");
			} else {
				msg.append(myCoinId + " Price at current time : " + toMoneyStr(coinCV, marketId) + "\n");
				msg.append(myCoinId + " Price before 24 hours : " + toMoneyStr(coinBV, marketId) + "\n");
			}
			msg.append("\n");
			msg.append("BTC 24 hour rate of change : " + toSignPercent(btcCV, btcBV) + "\n");
			msg.append(myCoinId + " 24 hour rate of change : " + toSignPercent(coinCV, coinBV) + "\n");
			break; 
		}
		return msg.toString();
	}
	
	

	/**************************/
	/** Calculate Message *****/
	/**************************/
	public String msgCalcResult(double price, double cnt, double avgPrice, double coinValue, JSONObject btcObj, ClientVo client) {
		StringBuilder msg = new StringBuilder();
		Market marketId 	= client.getMarketId();
		Lang lang 	= client.getLang();
		String date		= TimeStamper.getDateTime(client.getLocaltime());
		
		switch(lang) {
		case KR :
			msg.append("현재 시각  : "  + date + "\n");
			msg.append("\n");
			msg.append("코인개수 : " + toCoinCntStr(cnt, lang) + "\n");
			if(inBtcs.get(marketId)) {
				double btcMoney = btcObj.getDouble("last");
				double avgBTC = avgPrice / btcMoney;
				double coinBTC = coinValue;
				double coinMoney = coinValue * btcMoney;
				msg.append("평균단가 : " + toMoneyStr(avgPrice, marketId) + "  [" + toBTCStr(avgBTC) + "]\n");
				msg.append("현재단가 : " + toMoneyStr(coinMoney, marketId) + " [" + toBTCStr(coinBTC) + "]\n");
				msg.append("---------------------\n");
				msg.append("투자금액 : " + toInvestAmountStr(price, marketId) + "\n");
				msg.append("현재금액 : " + toInvestAmountStr((int)(coinMoney * cnt), marketId) + "\n");
				msg.append("손익금액 : " + toSignInvestAmountStr((int)((coinMoney * cnt) - (cnt * avgPrice)), marketId) + " (" + toSignPercent((int)(coinMoney * cnt), (int)(cnt * avgPrice)) + ")\n");
				msg.append("\n");
			} else {
				double coinMoney = coinValue;
				msg.append("평균단가 : " + toMoneyStr(avgPrice, marketId) + "\n");
				msg.append("현재단가 : " + toMoneyStr(coinMoney, marketId)+ "\n");
				msg.append("---------------------\n");
				msg.append("투자금액 : " + toInvestAmountStr(price, marketId) + "\n");
				msg.append("현재금액 : " + toInvestAmountStr((int)(coinMoney * cnt), marketId) + "\n");
				msg.append("손익금액 : " + toSignInvestAmountStr((int)((coinMoney * cnt) - (cnt * avgPrice)), marketId) + " (" + toSignPercent((int)(coinMoney * cnt), (int)(cnt * avgPrice)) + ")\n");
				msg.append("\n");
			}
			break;
			
		case US : 
			msg.append("Current Time  : "  + date + "\n");
			msg.append("\n");
			msg.append("The number of coins : " + toCoinCntStr(cnt, lang) + "\n");
			if(inBtcs.get(marketId)) {
				double btcMoney = btcObj.getDouble("last");
				double avgBTC = avgPrice / btcMoney;
				double coinBTC = coinValue;
				double coinMoney = coinValue * btcMoney;
				msg.append("Average Coin Price : " + toMoneyStr(avgPrice, marketId) + "  [ " + toBTCStr(avgBTC) + "]\n");
				msg.append("Current Coin Price : " + toMoneyStr(coinMoney, marketId) + " [ " + toBTCStr(coinBTC) + "]\n");
				msg.append("---------------------\n");
				msg.append("Investment Amount : " + toInvestAmountStr(price, marketId) + "\n");
				msg.append("Curernt Amount : " + toInvestAmountStr((int)(coinMoney * cnt), marketId) + "\n");
				msg.append("Profit and loss : " + toSignInvestAmountStr((int)((coinMoney * cnt) - (cnt * avgPrice)), marketId) + " (" + toSignPercent((int)(coinMoney * cnt), (int)(cnt * avgPrice)) + ")\n");
				msg.append("\n");
			} else {
				double coinMoney = coinValue;
				msg.append("Average Coin Price : " + toMoneyStr(avgPrice, marketId) + "\n");
				msg.append("Current Coin Price : " + toMoneyStr(coinMoney, marketId)+ "\n");
				msg.append("---------------------\n");
				msg.append("Investment Amount : " + toInvestAmountStr(price, marketId) + "\n");
				msg.append("Curernt Amount : " + toInvestAmountStr((int)(coinMoney * cnt), marketId) + "\n");
				msg.append("Profit and loss : " + toSignInvestAmountStr((int)((coinMoney * cnt) - (cnt * avgPrice)), marketId) + " (" + toSignPercent((int)(coinMoney * cnt), (int)(cnt * avgPrice)) + ")\n");
				msg.append("\n");
			}
			break; 
		}
		return msg.toString();
	}
	
	/***************************/
	/** Happy Line Message *****/
	/***************************/
	public String warningHappyLineFormat(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("코인개수는 숫자로만 입력해주세요.\n"); break;
		case US : msg.append("Please enter the number of coins only in numbers.\n");break; 
		}
		return msg.toString();
	}

	public String explainHappyLine(Market marketId, Lang lang) {
		StringBuilder msg = new StringBuilder();
		String exampleTarget = null;
		if(marketId.isKRW()) { exampleTarget = exTargetKR;}
		if(marketId.isUSD()) { exampleTarget = exTargetUS;}
		
		switch(lang) {
		case KR :
			msg.append("원하시는 코인가격을 입력해주세요.\n");
			msg.append("희망 손익금을 확인 하실 수 있습니다.\n");
			msg.append("\n");
			msg.append("* 코인가격은 숫자로 입력해주세요.\n");
			msg.append("* ex) " + exampleTarget + "  : 희망 코인가격 " + toMoneyStr(Double.parseDouble(exampleTarget), marketId) + "\n");
			msg.append("\n");
			msg.append("\n");
			msg.append("# 메인으로 돌아가시려면 문자를 입력해주세요.\n");
			break;
		case US : 
			msg.append("Please enter the desired coin price.\n");
			msg.append("if enter your desired coin price,  you can see expected profit and loss.\n");
			msg.append("\n");
			msg.append("* Please enter the coin price in numbers only.\n");
			msg.append("* example) " + exampleTarget + "  : desired coin price " + toMoneyStr(Double.parseDouble(exampleTarget), marketId) + " set\n");
			msg.append("\n");
			msg.append("\n");
			msg.append("# To return to main, enter a character.\n");
			break; 
		}
		
		return msg.toString();
	}
	
	public String msgHappyLineResult(double price, double coinCnt, double happyPrice, Market marketId, Lang lang) {
		double avgPrice = (double) price / coinCnt;
		StringBuilder msg = new StringBuilder();
		
		switch(lang) {
		case KR :
			msg.append("코인개수 : " + toCoinCntStr(coinCnt, lang) + "\n");
			msg.append("평균단가 : " + toMoneyStr(avgPrice, marketId) + "\n");
			msg.append("희망단가 : " + toMoneyStr(happyPrice, marketId) + "\n");
			msg.append("---------------------\n");
			msg.append("투자금액 : " + toInvestAmountStr(price, marketId) + "\n"); 
			msg.append("희망금액 : " + toInvestAmountStr((long)(happyPrice * coinCnt), marketId) + "\n");
			msg.append("손익금액 : " + toSignInvestAmountStr((long)((happyPrice * coinCnt) - (price)), marketId) + " (" + toSignPercent((int)(happyPrice * coinCnt), price) + ")\n");
			break;
		case US :
			msg.append("The number of coins : " + toCoinCntStr(coinCnt, lang) + "\n");
			msg.append("Average Coin Price  : " + toMoneyStr(avgPrice, marketId) + "\n");
			msg.append("Desired Coin Price  : " + toMoneyStr(happyPrice, marketId) + "\n");
			msg.append("---------------------\n");
			msg.append("Investment Amount : " + toInvestAmountStr(price, marketId) + "\n"); 
			msg.append("Desired Amount : " + toInvestAmountStr((long)(happyPrice * coinCnt), marketId) + "\n");
			msg.append("Profit and loss : " + toSignInvestAmountStr((long)((happyPrice * coinCnt) - (price)), marketId) + "(" + toSignPercent((int)(happyPrice * coinCnt), price) + ")\n");
			break; 
		}
		
		return msg.toString();
	}
	
	/*********************************/
	/** Set investment Price Message**/
	/********************************/
	public String explainSetPrice(Lang lang, Market marketId) {
		String exampleInvest = null;
		if(marketId.isKRW()) { exampleInvest = exInvestKR;}
		if(marketId.isUSD()) { exampleInvest = exInvestUS;}
		
		StringBuilder msg = new StringBuilder();
		switch(lang) {
		case KR :
			msg.append("투자금액을 입력해주세요.\n");
			msg.append("투자금액과 코인개수를 입력하시면 손익금을 확인 하실 수 있습니다.\n");
			msg.append("\n");
			msg.append("* 투자금액은 숫자로만 입력해주세요.\n");
			msg.append("* 0을 입력하시면 초기화됩니다.\n");
			msg.append("* ex) " + 0 + " : 초기화\n");
			msg.append("* ex) " + exampleInvest + " : 투자금액 " + toInvestAmountStr(Double.parseDouble(exampleInvest), marketId) + " 설정\n");
			msg.append("\n");
			msg.append("\n");
			msg.append("# 메인으로 돌아가시려면 문자를 입력해주세요.\n");
			break;
		case US : 
			msg.append("Please enter your investment amount.\n");
			msg.append("If you enter the amount of investment and the number of coins, you can see profit and loss.\n");
			msg.append("\n");
			msg.append("* Please enter the investment amount in numbers only.\n");
			msg.append("* If you enter 0, it is initialized.\n");
			msg.append("* example) " + 0 + " : Init investment amount\n");
			msg.append("* example) " + exampleInvest + " : investment amount " + toInvestAmountStr(Double.parseDouble(exampleInvest), marketId) + " set\n");
			msg.append("\n");
			msg.append("\n");
			msg.append("# To return to main, enter a character.\n");
			break;
		}
		return msg.toString();
	}

	public String warningPriceFormat(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("투자금액은 숫자로만 입력해주세요.\n"); break;
		case US : msg.append("Please enter the investment amount only in numbers.\n"); break; 
		}
		return msg.toString();
	}

	public String msgPriceInit(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("투자금액이 초기화 되었습니다.\n"); break;
		case US : msg.append("Investment Price has been init.\n"); break; 
		}
		return msg.toString();
	}

	public String msgPriceSet(double price, Market marketId, Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("투자금액이 " + toInvestAmountStr(price, marketId) + "으로 설정되었습니다.\n"); break;
		case US : msg.append("The investment amount is set at " + toInvestAmountStr(price, marketId)  + "\n"); break; 
		}
		return msg.toString();
	}
	
	/***************************/
	/** Set Coin Count Message**/
	/***************************/
	public String explainSetCoinCount(Lang lang) {
		StringBuilder msg = new StringBuilder();
		switch(lang) {
		case KR :
			msg.append("코인개수를 입력해주세요.\n");
			msg.append("투자금액과 코인개수를 입력하시면 손익금을 확인 하실 수 있습니다.\n");
			msg.append("\n");
			msg.append("* 코인개수는 숫자로만 입력해주세요.\n");
			msg.append("* 0을 입력하시면 초기화됩니다.\n");
			msg.append("* ex) " + 0 + " : 초기화\n");
			msg.append("* ex) " + exCoinCnt + " : 코인개수 " + toCoinCntStr(Double.parseDouble(exCoinCnt), lang) + " 설정\n");
			msg.append("\n");
			msg.append("\n");
			msg.append("# 메인으로 돌아가시려면 문자를 입력해주세요.\n");
			break;
		case US : 
			msg.append("Please enter your number of coins.\n");
			msg.append("If you enter the amount of investment and the number of coins, you can see profit and loss.\n");
			msg.append("\n");
			msg.append("* Please enter the number of coins in numbers only.\n");
			msg.append("* If you enter 0, it is initialized.\n");
			msg.append("* example) " + 0 + " : Init the number of coins\n");
			msg.append("* example) " + exCoinCnt + " : the number of coins " + toCoinCntStr(Double.parseDouble(exCoinCnt), lang) + " set\n");
			msg.append("\n");
			msg.append("\n");
			msg.append("# To return to main, enter a character.\n");
			break;
		}
		return msg.toString();
	}
	
	public String warningCoinCountFormat(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("코인개수는 숫자로만 입력해주세요.\n"); break;
		case US : msg.append("Please enter the number of coins only in numbers.\n"); break; 
		}
		return msg.toString();
	}
	
	public String msgCoinCountInit(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("코인개수가 초기화 되었습니다.\n"); break;
		case US : msg.append("Investment Price has been init.\n"); break; 
		}
		return msg.toString();
	}

	public String msgCoinCountSet(double number, Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("코인개수가 " + toCoinCntStr(number, lang) + "로 설정되었습니다.\n"); break;
		case US : msg.append("The number of coins is set at " + toCoinCntStr(number, lang)  + "\n"); break; 
		}
		return msg.toString();
	}

	/************************/
	/** Set Market Message **/
	/************************/
	public String explainMarketSet(Lang lang) {
		StringBuilder msg = new StringBuilder();
		switch(lang) {
		case KR :
			msg.append("거래중인 거래소를 설정해주세요.\n");
			msg.append("모든 정보는 설정 거래소 기준으로 전송됩니다.\n");
			break;
		case US : 
			msg.append("Please select an market.\n");
			msg.append("All information will be sent on the market you selected.\n");
			break;
		}
		return msg.toString();
	}

	public String msgMarketSet(Market marketId, Lang lang) {
		StringBuilder msg = new StringBuilder("");
		String marketStr = this.toMarketStr(marketId, lang);
		
		switch(lang) {
		case KR : msg.append("거래소가 " + marketStr + "(으)로 설정되었습니다.\n"); break;
		case US : msg.append("The exchange has been set up as " + marketStr + ".\n"); break; 
		}
		return msg.toString();
	}

	public String msgMarketNoSet(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("거래소가 설정되지 않았습니다.\n"); break;
		case US : msg.append("The market has not been set up.\n"); break; 
		}
		return msg.toString();
	}
	
	public String msgMarketSetChangeCurrency(ClientVo client, Double changePrice, Double changeTargetUp, Double changeTargetDown, Market changeMarketId) {
		StringBuilder msg = new StringBuilder();
		Market marketId 				= client.getMarketId();
		Lang lang					= client.getLang();
		Double currentPrice 		= client.getInvest();
		Double currentTargetUp 		= client.getTargetUp();
		Double currentTargetDown	= client.getTargetDown();
		if(currentPrice != null || currentTargetUp != null || currentTargetDown != null ) {
			switch(lang) {
			case KR:
				msg.append("변경하신 거래소의 화폐단위가 변경되어,\n");
				msg.append("설정하신 투자금액/목표가를 환율에 맞추어 변동하였습니다.\n");
				if(currentPrice != null) { msg.append("투자금액 : " + toInvestAmountStr(currentPrice, marketId) + " -> " + toInvestAmountStr(changePrice, changeMarketId) + "\n"); }
				if(currentTargetUp != null) { msg.append("목표가격 : " + toMoneyStr(currentTargetUp, marketId) + " -> " + toMoneyStr(changeTargetUp, changeMarketId) + "\n"); }
				if(currentTargetDown != null) { msg.append("목표가격 : " + toMoneyStr(currentTargetDown, marketId) + " -> " + toMoneyStr(changeTargetDown, changeMarketId) + "\n"); }
				break;
			case US:
				msg.append("* The currency unit of the exchange has been changed,\n");
				msg.append("the investment amount / target price you set has been changed to match the exchange rate.\n");
				msg.append("\n");
				if(currentPrice != null) { msg.append("Investment amount : " + toInvestAmountStr(currentPrice, marketId) + " -> " + toInvestAmountStr(changePrice, changeMarketId) + "\n"); }
				if(currentTargetUp != null) { msg.append("Target Price: " + toMoneyStr(currentTargetUp, marketId) + " -> " + toMoneyStr(changeTargetUp, changeMarketId) + "\n"); }
				if(currentTargetDown != null) { msg.append("Target Price : " + toMoneyStr(currentTargetDown, marketId) + " -> " + toMoneyStr(changeTargetDown, changeMarketId) + "\n"); }
				break;
			}
		}
		
		return msg.toString();
	}
	
	
	
	/*****************************/
	/** Target Price Message**/
	/*****************************/
	public String explainTargetPriceSet(Lang lang, Market marketId) {
		StringBuilder msg = new StringBuilder();
		String exampleTarget = null;
		if(marketId.isKRW()) { exampleTarget = exTargetKR;}
		if(marketId.isUSD()) { exampleTarget = exTargetUS;}
		
		switch(lang) {
		case KR :
			msg.append("목표가격을 설정해주세요.\n");
			msg.append("목표가격이 되었을 때 알림이 전송됩니다.\n");
			msg.append("목표가격을 위한 가격정보는 1분 주기로 갱신됩니다.\n");
			msg.append("\n");
			msg.append("* 목표가격은 숫자 또는 백분율로 입력해주세요.\n");
			msg.append("* ex) " + 0 + "  : 목표가격 초기화\n");
			msg.append("* ex) " + exampleTarget + "  : 목표가격 " + toMoneyStr(Double.parseDouble(exampleTarget), marketId) + "\n");
			msg.append("* ex) " + exTargetRate + "    : 현재가 +" + exTargetRate + "\n");
			msg.append("* ex) -" + exTargetRate + "  : 현재가 -" + exTargetRate + "\n");
			msg.append("\n");
			msg.append("\n");
			msg.append("# 메인으로 돌아가시려면 문자를 입력해주세요.\n");
			break;
		case US :
			msg.append("Please set Target Price.\n");
			msg.append("Once you reach the target price, you will be notified.\n");
			msg.append("Coin price information is updated every 1 minute.\n");
			msg.append("\n");
			msg.append("* Please enter the target price in numbers or percentages.\n");
			msg.append("* If you enter 0, it is initialized.\n");
			msg.append("* example)  " + 0 + "  : Initial target Price\n");
			msg.append("* example)  " + exampleTarget + "  : Target price " + toMoneyStr(Double.parseDouble(exampleTarget), marketId)+ "\n");
			msg.append("* example)  " + exTargetRate + "   : Current Price +" + exTargetRate + "\n");
			msg.append("* example)  -" + exTargetRate + "  : Current Prcie -" + exTargetRate + "\n");
			msg.append("\n");
			msg.append("\n");
			msg.append("# To return to main, enter a character.\n");
			break;
		}
		
		return msg.toString();
	}

	public String warningTargetPriceSetPercent(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("목표가격 백분율을 -100% 이하로 설정 할 수 없습니다.\n"); break;
		case US : msg.append("You can not set the target price percentage below -100%.\n"); break; 
		}
		return msg.toString();
	}
	
	public String warningTargetPriceSetFormat(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("목표가격을 숫자 또는 백분율로 입력해주세요.\n"); break;
		case US : msg.append("Please enter target price as a number or percentage.\n"); break; 
		}
		return msg.toString();
	}
	
	public String msgTargetPriceInit(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("목표가격이 초기화되었습니다.\n"); break;
		case US : msg.append("Target price has been init.\n"); break; 
		}
		return msg.toString();
	}
	
	public String msgTargetPriceSetResult(double TargetPrice, double currentValue, Market marketId, Lang lang) {
		StringBuilder msg = new StringBuilder();
		switch(lang) {
		case KR :
			msg.append("목표가격 " + toMoneyStr(TargetPrice, marketId) + "으로 설정되었습니다.\n");
			msg.append("------------------------\n");
			msg.append("목표가격 : " + toMoneyStr(TargetPrice, marketId) + "\n");
			msg.append("현재가격 : " + toMoneyStr(currentValue, marketId) + "\n");
			msg.append("가격차이 : " + toSignMoneyStr(TargetPrice - currentValue, marketId) + "(" + toSignPercent(TargetPrice, currentValue) + " )\n");
			break;
		case US : 
			msg.append("The target price is set at " + toMoneyStr(TargetPrice, marketId) + ".\n");
			msg.append("------------------------\n");
			msg.append("Target Price       : " + toMoneyStr(TargetPrice, marketId) + "\n");
			msg.append("Current Price      : " + toMoneyStr(currentValue, marketId) + "\n");
			msg.append("Price difference : " + toSignMoneyStr(TargetPrice - currentValue, marketId) + " (" + toSignPercent(TargetPrice, currentValue) + " )\n");
			break;
		}
		return msg.toString();
	}

	public String msgTargetPriceNotify(double currentValue, double price, Market marketId, Lang lang) {
		StringBuilder msg = new StringBuilder();
		switch(lang) {
		case KR :
			msg.append("목표가격에 도달하였습니다!\n");
			msg.append("목표가격 : " + toMoneyStr(price, marketId) + "\n"); 
			msg.append("현재가격 : " + toMoneyStr(currentValue, marketId) + "\n");
			break;
		case US : 
			msg.append("Target price reached!\n");
			msg.append("Traget Price : " + toMoneyStr(price, marketId) + "\n"); 
			msg.append("Current Price : " + toMoneyStr(currentValue, marketId) + "\n");
			break;
		}
		return msg.toString();
	}
	
	/******************************/
	/** Set Daily Notification **/
	/******************************/
	public String explainSetDayloop(Lang lang) {
		StringBuilder msg = new StringBuilder();
		switch(lang) {
		case KR : 
			msg.append("일일 알림 주기를 선택해주세요.\n");
			msg.append("선택 하신 일일주기로 알림이 전송됩니다.\n");
			break;
		case US : 
			msg.append("Please select daily notifications cycle.\n");
			msg.append("Coin Price information will be sent according to the cycle.\n");
			break;
		}
		return msg.toString();
	}

	public String msgDayloopSet(int dayloop, Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("일일 알림이 매 " +  dayloop + " 일주기로 전송됩니다.\n"); break;
		case US : msg.append("Daily notifications are sent every " + dayloop + " days.\n"); break; 
		}
		return msg.toString();
	}

	public String msgDayloopNoSet(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("일일 알림 주기가 설정 되지 않았습니다.\n"); break;
		case US : msg.append("Daily notifications cycle is not set.\n"); break; 
		}
		return msg.toString();
	}

	public String msgDayloopStop(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("일일 알림이 전송되지 않습니다.\n"); break;
		case US : msg.append("Daily notifications are not sent.\n"); break; 
		}
		return msg.toString();
	}
	

	/*******************************/
	/** Set Hourly Notification **/
	/*******************************/
	public String explainSetTimeloop(Lang lang) {
		StringBuilder msg = new StringBuilder();
		switch(lang) {
		case KR :
			msg.append("시간 알림 주기를 선택해주세요.\n");
			msg.append("선택 하신 시간주기로 알림이 전송됩니다.\n");
			break;
		case US :
			msg.append("Please select hourly notifications cycle.\n");
			msg.append("Coin Price information will be sent according to the cycle.\n");
			break;
		}
		return msg.toString();
	}
	
	public String msgTimeloopSet(int timeloop, Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("시간 알림이 " +  timeloop + " 시간 주기로 전송됩니다.\n"); break;
		case US : msg.append("Houly notifications are sent every " + timeloop + " hours.\n"); break; 
		}
		return msg.toString();
	}
	
	public String msgTimeloopNoSet(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("시간알림 주기가 설정 되지 않았습니다.\n"); break;
		case US : msg.append("Hourly notifications cycle is not set.\n"); break; 
		}
		return msg.toString();
	}


	public String msgTimeloopStop(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("시간 알림이 전송되지 않습니다.\n"); break;
		case US : msg.append("Hourly notifications are not sent.\n"); break; 
		}
		return msg.toString();
	}
	
	/*********************/
	/** Show setting *****/
	/*********************/
	public String msgClientSetting(ClientVo client, Lang lang) {
		StringBuilder msg = new StringBuilder();
		Market marketId = client.getMarketId();
		switch(lang) {
		case KR :
			msg.append("현재 설정은 다음과 같습니다.\n");
			msg.append("-----------------\n");
			
			msg.append("거래소     = ");
			msg.append(toMarketStr(client.getMarketId(), lang) + "\n");
					
			
			if(client.getDayloop() != 0){ msg.append("일일알림 = 매 " + client.getDayloop() + " 일 주기 알림\n");} 
			else{ msg.append("일일알림 = 알람 없음\n");}
			
			if(client.getTimeloop() != 0){ msg.append("시간알림 = 매 " + client.getTimeloop() + " 시간 주기 알림\n");} 
			else{ msg.append("시간알림 = 알람 없음\n");}
			
			if(client.getTargetUp() != null){msg.append("목표가격 = " + toMoneyStr(client.getTargetUp(), marketId) + "\n");}
			else if(client.getTargetDown() != null){msg.append("목표가격 = " + toMoneyStr(client.getTargetDown(), marketId) + "\n");}
			else { msg.append("목표가격 = 입력되어있지 않음.\n");}
			
			if(client.getInvest() != null){msg.append("투자금액 = " + toInvestAmountStr(client.getInvest(), marketId) + "\n");}
			else { msg.append("투자금액 = 입력되어있지 않음.\n");}
			
			if(client.getCoinCnt() != null){msg.append("코인개수 = " + toCoinCntStr(client.getCoinCnt(), lang) + "\n"); }
			else { msg.append("코인개수 = 입력되어있지 않음.\n");}
			
			break;
			
		case US : 
			msg.append("The current setting is as follows.\n");
			msg.append("-----------------\n");
			
			msg.append("Market = ");
			msg.append(toMarketStr(client.getMarketId(), lang) + "\n");
			
			if(client.getDayloop() != 0){ msg.append("Daily Notification = every " + client.getDayloop() + " days\n");} 
			else{ msg.append("Daily Notification = No notifications.\n");}
			
			if(client.getTimeloop() != 0){ msg.append("Hourly Notification = every " + client.getTimeloop() + " hours\n");} 
			else{ msg.append("Hourly Notification = No notifications.\n");}
			
			if(client.getTargetUp() != null){msg.append("Target price = " + toMoneyStr(client.getTargetUp(), marketId) + "\n");}
			else if(client.getTargetDown() != null){msg.append("Target price = " + toMoneyStr(client.getTargetDown(), marketId) + "\n");}
			else { msg.append("Target Price = Not entered.\n");}
			
			if(client.getInvest() != null){msg.append("Investment amount = " + toInvestAmountStr(client.getInvest(), marketId) + "\n");}
			else { msg.append("Investment amount = Not entered.\n");}
			
			if(client.getCoinCnt() != null){msg.append("The number of coins = " + toCoinCntStr(client.getCoinCnt(), lang) + "\n"); }
			else { msg.append("The number of coins = Not entered.\n");}
			break;
		}
		
		return msg.toString();
	}
	
	
	/****************************/
	/** Stop all notifications **/
	/****************************/
	public String explainStop(Lang lang) {
		StringBuilder msg = new StringBuilder();
		switch(lang) {
		case KR :
			msg.append( "모든 알림(일일알림, 시간알림, 목표가알림)을 중지하시겠습니까?\n");
			msg.append( "\n");
			msg.append( "★ 필독!\n");
			msg.append( "1. 모든알림이 중지되더라도 공지사항은 전송됩니다.\n");
			msg.append( "2. 모든알림이 중지되더라도 버튼을 통해 코인관련정보를 받을 수 있습니다.\n");
			msg.append( "3. 서비스를 완전히 중지하시려면 대화방을 삭제해주세요!\n");
			break;
		case US : 
			msg.append( "Are you sure you want to stop all notifications (daily, hourly , target price notifications )?\n");
			msg.append( "\n");
			msg.append( "★  Must read!\n");
			msg.append( "1. Even if all notifications have been stopped, you will continue to receive notification of service usage.\n");
			msg.append( "2. Even if all notifications have been stopped, you received coin information using menu.\n");
			msg.append( "3. If you want to stop completry this service, Please block bot.\n");
			break;
		}
		
		return msg.toString();
	}
	
	public String msgStopAllNotice(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append(myCoinId + " 모든알림(시간알림, 일일알림, 목표가격알림)이 중지되었습니다.\n"); break;
		case US : msg.append("All notifications (daily, hourly , target price notifications ) be stoped.\n"); break; 
		}
		return msg.toString();
	}
	
	/***********************/
	/** Explain Coin List **/
	/***********************/
	public String explainCoinList(List<CoinInfoVo> coinInfos, Lang lang) {
		StringBuilder msg = new StringBuilder();
		CoinInfoVo coinInfo = null;
		int coinInfosLen = coinInfos.size();
		
		switch(lang) {
		case KR:
			msg.append("링크를 클릭 하시면,\n");
			msg.append("해당 코인알리미 봇으로 이동합니다.\n");
			
			msg.append("-----------------------\n");
			for (int i = 0; i < coinInfosLen; i++) {
				coinInfo = coinInfos.get(i);
				msg.append(coinInfo.getCoinId() + " [" + coinInfo.getCoinId().getKr() + "] \n");
				msg.append(coinInfo.getChatAddr() + "\n");
				msg.append("\n");
			}
			msg.append("\n");
			break;
		case US:
			msg.append("Click on the link to go to other Coin Noticer.\n");
			msg.append("-----------------------\n");
			for (int i = 0; i < coinInfosLen; i++) {
				coinInfo = coinInfos.get(i);
				msg.append(coinInfo.getCoinId() + " [" + coinInfo.getCoinId().getUs() + "] \n");
				msg.append(coinInfo.getChatAddr() + "\n");
				msg.append("\n");
			}
			msg.append("\n");
			break;
		}
	
		
		return msg.toString();
	}
	
	/***********/
	/** Help  **/
	/***********/
	public String explainHelp(List<Market> enabledMarketIds, Lang lang) {
		StringBuilder msg = new StringBuilder();
		if (lang.equals(Lang.KR)) {
			msg.append(myCoinId + " 알리미 ver" + version + "\n");
			msg.append("\n");
			msg.append("별도의 시간 알림 주기 설정을 안하셨다면,\n");
			msg.append("3시간 주기로 " + myCoinId + " 가격 알림이 전송됩니다.\n");
			msg.append("\n");
			msg.append("별도의 일일 알림 주기 설정을 안하셨다면,\n");
			msg.append("1일 주기로 거래량, 상한가, 하한가, 종가가 비교되어 전송됩니다.\n");
			msg.append("\n");
			msg.append("별도의 거래소 설정을 안하셨다면,\n");
	
			//
			msg.append(toMarketStr(enabledMarketIds.get(0), lang) + " 기준의 정보가 전송됩니다.\n");
			msg.append("\n");
			msg.append("투자금액,코인개수를 설정하시면,\n");
			msg.append("원금, 현재금액, 손익금을 확인 하실 수 있습니다.\n");
			msg.append("\n");
			msg.append("목표가격을 설정하시면,\n");
			msg.append("목표가격이 되었을때 알림을 받을 수 있습니다.\n");
			msg.append("목표가격을 위한 가격정보는 각 거래소에서 1분 주기로 갱신됩니다.\n");
			msg.append("\n");
			msg.append("프리미엄 정보를 확인 하실 수 있습니다.\n");
			msg.append("\n");
			msg.append("비트코인대비 변화량을 확인 하실 수 있습니다.\n");
			msg.append("\n");
			
			msg.append("거래소 By ");
			for(int i = 0; i < enabledMarketIds.size(); i++) {
				msg.append(toMarketStr(enabledMarketIds.get(i), lang) + ", ");
			}
			msg.append("\n");
			msg.append("환율정보 By the European Central Bank\n");
			msg.append("\n");
			msg.append("Developed By CGLEE ( cglee079@gmail.com )\n");
		} else if(lang.equals(Lang.US)) {
			msg.append(myCoinId + " Coin Noticer Ver" + version + "\n");
			msg.append("\n");
			msg.append("If you are using this service for the first time,\n");
			msg.append(myCoinId + " price are sent every 3 hours.\n");
			msg.append("\n");
			msg.append("If you are using this service for the first time,\n");
			msg.append(myCoinId + " price are sent every 1 days. (with high, low, last price and volume)\n");
			msg.append("\n");
			msg.append("If you are using this service for the first time,\n");
			msg.append("Information based on ");
			//
			
			msg.append(toMarketStr(enabledMarketIds.get(0), lang) + "  market will be sent.\n");
			msg.append("\n");
			msg.append("If you set the amount of investment and the number of coins,\n");
			msg.append("you can check the current amount of profit and loss.\n");
			msg.append("\n");
			msg.append("If you set Target price,\n");
			msg.append("Once you reach the target price, you will be notified.\n");
			msg.append("Coin price information is updated every 1 minute.\n");
			msg.append("\n");
			msg.append("You can check the coin price on each market\n");
			msg.append("\n");
			msg.append("You can check coin price change rate compared to BTC\n");
			msg.append("\n");
			
			msg.append("* Markets : ");
			for(int i = 0; i < enabledMarketIds.size(); i++) {
				msg.append(toMarketStr(enabledMarketIds.get(i), lang) + ", ");
			}
			msg.append("\n");
			msg.append("* Exchange Rate Information By the European Central Bank\n");
			msg.append("\n");
			msg.append("Developed By CGLEE ( cglee079@gmail.com )\n");
		}
		return msg.toString();
	}
	
	public String explainSetForeginer(Lang lang) {
		StringBuilder msg = new StringBuilder();
		msg.append("★  If you are not Korean, Must read!!\n");
		msg.append("* Use the " + MainCmd.PREFERENCE.getCmd(Lang.US) + " Menu.\n");
		msg.append("* First. Please set language to English.\n");
		msg.append("* Second. Set the time adjustment for accurate notifications. Because of time difference by country.\n");
//		msg.append("* Last. if you set market in USA using '" +CMDER.getMainSetMarket(Lang.US) + "' menu, the currency unit is changed to USD.\n");
		return msg.toString();
	}
	
	/*******************************/
	/** Send message to developer **/
	/*******************************/
	public String explainSendSuggest(Lang lang) {
		StringBuilder msg = new StringBuilder();
		switch(lang) {
		case KR :
			msg.append("개발자에게 내용이 전송되어집니다.\n");
			msg.append("내용을 입력해주세요.\n");
			msg.append("\n");
			msg.append("\n");
			msg.append("# 메인으로 돌아가시려면 " + SendMessageCmd.OUT.getCmd(lang)  + " 를 입력해주세요.\n");
			break;
		case US : 
			msg.append("Please enter message.\n");
			msg.append("A message is sent to the developer.\n");
			msg.append("\n");
			msg.append("\n");
			msg.append("# To return to main, enter " + SendMessageCmd.OUT.getCmd(lang) + "\n");
			break;
		}
		return msg.toString();
	}
	
	public String msgThankyouSuggest(Lang lang) {
		StringBuilder msg = new StringBuilder();
		switch(lang) {
		case KR :
			msg.append("의견 감사드립니다.\n");
			msg.append("성투하세요^^!\n");
			break;
		case US : 
			msg.append("Thank you for your suggest.\n");
			msg.append("You will succeed in your investment :)!\n");
			break;
		}
		return msg.toString();
	}

	
	/***************************/
	/*** Sponsoring Message ****/
	/***************************/
	public String explainSupport(Lang lang) {
		StringBuilder msg = new StringBuilder();
		switch(lang) {
		case KR :
			msg.append("안녕하세요. 개발자 CGLEE 입니다.\n");
			msg.append("본 서비스는 무료 서비스 임을 다시 한번 알려드리며,\n");
			msg.append("절대로! 후원하지 않는다하여 사용자 여러분에게 불이익을 제공하지 않습니다.^^\n");
			msg.append("\n");
			msg.append("후원된 금액은 다음 용도로 소중히 사용하겠습니다\n");
			msg.append("\n");
			msg.append("1 순위. 서버 업그레이드 (타 코인 알리미 추가)\n");
			msg.append("2 순위. 서버 운영비 (전기세...^^)\n");
			msg.append("3 순위. 취업자금\n");
			msg.append("4 순위. 개발보상 (치킨 냠냠)\n");
			msg.append("\n");
			msg.append("감사합니다.\n");
			msg.append("하단에 정보를 참고하여주세요^^\n");
			break;
			
		case US : 
			msg.append("Hi. I'm developer CGLEE\n");
			msg.append("Never! I don't offer disadvantages to users by not sponsoring. :D\n");
			msg.append("\n");
			msg.append("Thank you for sponsoring.\n");
			msg.append("See the information below.\n");
			break; 
		}
		return msg.toString();
	}
	
	public String explainSupportWallet(CoinWalletVo wallet, CoinWalletVo xrpWallet, Lang lang) {
		StringBuilder msg = new StringBuilder();
		switch(lang) {
		case KR :
			if (wallet != null) {
				msg.append("* " + wallet.getCoinId() + " [ " + wallet.getCoinId().getKr() + " ]  지갑주소 : \n");
				msg.append(wallet.getAddr1() + "\n");
				if (myCoinId == Coin.XRP) {
					msg.append("데스티네이션 태그 :  " + wallet.getAddr2() + "\n");
				}
			} else {
				msg.append("해당 코인은 지갑을 개설 할 수 없어,\n");
				msg.append("타 코인 지갑 정보를 전송합니다.\n");
				msg.append("\n");

				wallet = xrpWallet;
				msg.append("* " + wallet.getCoinId() + " [ " +wallet.getCoinId().getKr() + " ]  지갑주소 : \n");
				msg.append(wallet.getAddr1() + "\n");
				msg.append("데스티네이션 태그 :  " + wallet.getAddr2() + "\n");
			}
			break;
			
		case US : 
			if (wallet != null) {
				msg.append("* " + wallet.getCoinId() + " [ " + wallet.getCoinId().getUs() + " ]  Wallet address : \n");
				msg.append(wallet.getAddr1() + "\n");
				if (myCoinId == Coin.XRP) {
					msg.append("destination tag :  " + wallet.getAddr2() + "\n");
				}
			} else {
				msg.append("Because this coin cannot open a wallet,\n");
				msg.append("another coin wallet address send.\n");
				msg.append("\n");

				wallet = xrpWallet;
				msg.append("* " + wallet.getCoinId() + " [ " + wallet.getCoinId().getUs() + " ]  Wallet address : \n");
				msg.append(wallet.getAddr1() + "\n");
				msg.append("destination tag :  " + wallet.getAddr2() + "\n");
			}
			break; 
		}
		
		return msg.toString();
	}

	public String explainSupportAN(Lang lang) {
		StringBuilder msg = new StringBuilder();
		switch(lang) {
		case KR :
			msg.append("* 후원계좌\n");
			msg.append("예금주: 이찬구\n");
			msg.append("은행   : 신한은행 \n");
			msg.append("번호   : 110 409 338434");
			break;
			
		case US : 
			msg.append("* Sponsored account\n");
			msg.append("Bank   : Shinhan Bank (in Korea)\n");
			msg.append("Account Holder: Lee Changoo(이찬구)\n");
			msg.append("Account Number: 110 409 338434");
			break; 
		}
		
		return msg.toString();
	}

	/*********************/
	/*** Language Set  ***/
	/*********************/
	public String explainSetLanguage(Lang lang) {
		return "Please select a language.";
	}
	
	public String msgSetLanguageSuccess(Lang lang) {
		StringBuilder msg = new StringBuilder("");
		switch(lang) {
		case KR : msg.append("언어가 한국어로 변경되었습니다.\n"); break;
		case US : msg.append("Language changed to English.\n"); break; 
		}
		return msg.toString();
	}

	
	/*********************/
	/** Time Adjust  *****/
	/*********************/
	public String explainTimeAdjust(Lang lang) {
		StringBuilder msg = new StringBuilder();
		msg.append("한국분이시라면 별도의 시차조절을 필요로하지 않습니다.^^  <- for korean\n");
		msg.append("\n");
		msg.append("Please enter the current time for accurate time notification.\n");
		msg.append("because the time differs for each country.\n");
		msg.append("\n");
		msg.append("* Please enter in the following format.\n");
		msg.append("* if you entered 0, time adjust initialized.\n");
		msg.append("* example) 0 : init time adjust\n");
		msg.append("* example) " + TimeStamper.getDateBefore() + " 23:00 \n");
		msg.append("* example) " + TimeStamper.getDate() + " 00:33 \n");
		msg.append("* example) " + TimeStamper.getDate() +  " 14:30 \n");
		msg.append("\n");
		msg.append("\n");
		msg.append("# To return to main, enter a character.\n");
		return msg.toString();
	}
	
	public String warningTimeAdjustFormat(Lang lang) {
		return "Please type according to the format.\n";
	}
	
	public String msgTimeAdjustSuccess(Date date) {
		StringBuilder msg = new StringBuilder();
		msg.append("Time adjustment succeeded.\n");
		msg.append("Current Time : " + TimeStamper.getDateTime(date) + "\n");
		return msg.toString();
	}
	
//	public String msgToPreference() {
//		StringBuilder msg = new StringBuilder("");
//		msg.append("\n# Changed to Preference menu\n");
//		return msg.toString();
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
		
		Market marketId 	= client.getMarketId();
		Lang lang		= client.getLang();
		long localTime	= client.getLocaltime();
		String date 	= TimeStamper.getDateTime(localTime);
		int dayLoop 	= client.getDayloop();

		StringBuilder msg = new StringBuilder();
		msg.append(date + "\n");
		
		switch(lang) {
		case KR :
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
			
			if(inBtcs.get(marketId)) {
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
				
				msg.append("---------------------\n");
				msg.append("금일의 거래량 : " + toVolumeStr(currentVolume) + " \n");
				msg.append(dayLoopStr + "전 거래량 : " + toVolumeStr(beforeVolume) + " \n");
				msg.append("거래량의 차이 : " + toSignVolumeStr(currentVolume - beforeVolume) + " (" + toSignPercent(currentVolume, beforeVolume) + ")\n");
				msg.append("\n");
				
				msg.append("금일의 상한가 : " + toMoneyStr(currentHigh, marketId) + " ["+ toBTCStr(currentHighBTC) + "]\n");
				msg.append(dayLoopStr + "전 상한가 : " + toMoneyStr(beforeHigh, marketId) + " ["+ toBTCStr(beforeHighBTC) + "]\n");
				msg.append("상한가의 차이 : " + toSignMoneyStr(currentHigh - beforeHigh, marketId) + " (" + toSignPercent(currentHigh, beforeHigh) + ")\n");
				msg.append("\n");
				
				msg.append("금일의 하한가 : " + toMoneyStr(currentLow, marketId) + " ["+ toBTCStr(currentLowBTC) + "]\n");
				msg.append(dayLoopStr + "전 하한가 : " + toMoneyStr(beforeLow, marketId) + " ["+ toBTCStr(beforeLowBTC) + "]\n");
				msg.append("하한가의 차이 : " + toSignMoneyStr(currentLow - beforeLow, marketId) + " (" + toSignPercent(currentLow, beforeLow) + ")\n");
				msg.append("\n");
				
				msg.append("금일의 종가 : " + toMoneyStr(currentLast, marketId) + " ["+ toBTCStr(currentLastBTC) + "]\n");
				msg.append(dayLoopStr + "전 종가 : " + toMoneyStr(beforeLast, marketId) + " ["+ toBTCStr(beforeLastBTC) + "]\n");
				msg.append("종가의 차이 : " + toSignMoneyStr(currentLast - beforeLast, marketId) + " (" + toSignPercent(currentLast, beforeLast) + ")\n");
				msg.append("\n");
			} else {
				msg.append("---------------------\n");
				msg.append("금일의 거래량 : " + toVolumeStr(currentVolume) + " \n");
				msg.append(dayLoopStr + "전 거래량 : " + toVolumeStr(beforeVolume) + " \n");
				msg.append("거래량의 차이 : " + toSignVolumeStr(currentVolume - beforeVolume) + " (" + toSignPercent(currentVolume, beforeVolume) + ")\n");
				msg.append("\n");
				
				msg.append("금일의 상한가 : " + toMoneyStr(currentHigh, marketId) + "\n");
				msg.append(dayLoopStr + "전 상한가 : " + toMoneyStr(beforeHigh, marketId) + "\n");
				msg.append("상한가의 차이 : " + toSignMoneyStr(currentHigh - beforeHigh, marketId) + " (" + toSignPercent(currentHigh, beforeHigh) + ")\n");
				msg.append("\n");
				
				msg.append("금일의 하한가 : " + toMoneyStr(currentLow, marketId) + "\n");
				msg.append(dayLoopStr + "전 하한가 : " + toMoneyStr(beforeLow, marketId) + "\n");
				msg.append("하한가의 차이 : " + toSignMoneyStr(currentLow - beforeLow, marketId) + " (" + toSignPercent(currentLow, beforeLow) + ")\n");
				msg.append("\n");
				
				
				msg.append("금일의 종가 : " + toMoneyStr(currentLast, marketId) + "\n");
				msg.append(dayLoopStr + "전 종가 : " + toMoneyStr(beforeLast, marketId) + "\n");
				msg.append("종가의 차이 : " + toSignMoneyStr(currentLast - beforeLast, marketId) + " (" + toSignPercent(currentLast, beforeLast) + ")\n");
				msg.append("\n");
			}
			break;
			
		case US : 
			if(inBtcs.get(marketId)) {
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
				
				msg.append("---------------------\n");
				msg.append("Volume at today : " + toVolumeStr(currentVolume) + " \n");
				msg.append("Volume before " + dayLoop + " day : " + toVolumeStr(beforeVolume) + " \n");
				msg.append("Volume difference : " + toSignVolumeStr(currentVolume - beforeVolume) + " (" + toSignPercent(currentVolume, beforeVolume) + ")\n");
				msg.append("\n");
				msg.append("High at Today : " + toMoneyStr(currentHigh, marketId) + " ["+ toBTCStr(currentHighBTC) + "]\n");
				msg.append("High before " + dayLoop + " day : " + toMoneyStr(beforeHigh, marketId) + " ["+ toBTCStr(beforeHighBTC) + "]\n");
				msg.append("High difference : " + toSignMoneyStr(currentHigh - beforeHigh, marketId) + " (" + toSignPercent(currentHigh, beforeHigh) + ")\n");
				msg.append("\n");
				msg.append("Low at Today : " + toMoneyStr(currentLow, marketId) + " ["+ toBTCStr(currentLowBTC) + "]\n");
				msg.append("Low before " + dayLoop + " day : "+ toMoneyStr(beforeLow, marketId) + " ["+ toBTCStr(beforeLowBTC) + "]\n");
				msg.append("Low difference : " + toSignMoneyStr(currentLow - beforeLow, marketId) + " (" + toSignPercent(currentLow, beforeLow) + ")\n");
				msg.append("\n");
				msg.append("Last at Today : " + toMoneyStr(currentLast, marketId) + " ["+ toBTCStr(currentLastBTC) + "]\n");
				msg.append("Last before " + dayLoop + " day : "+ toMoneyStr(beforeLast, marketId) + " ["+ toBTCStr(beforeLastBTC) + "]\n");
				msg.append("Last difference : " + toSignMoneyStr(currentLast - beforeLast, marketId) + " (" + toSignPercent(currentLast, beforeLast) + ")\n");
				msg.append("\n");
			} else {
				msg.append("---------------------\n");
				msg.append("Volume at today : " + toVolumeStr(currentVolume) + " \n");
				msg.append("Volume before " + dayLoop + " day : " + toVolumeStr(beforeVolume) + " \n");
				msg.append("Volume difference : " + toSignVolumeStr(currentVolume - beforeVolume) + " (" + toSignPercent(currentVolume, beforeVolume) + ")\n");
				msg.append("\n");
				msg.append("High at Today : " + toMoneyStr(currentHigh, marketId) + "\n");
				msg.append("High before " + dayLoop + " day : " + toMoneyStr(beforeHigh, marketId) + "\n");
				msg.append("High difference : " + toSignMoneyStr(currentHigh - beforeHigh, marketId) + " (" + toSignPercent(currentHigh, beforeHigh) + ")\n");
				msg.append("\n");
				msg.append("Low at Today : " + toMoneyStr(currentLow, marketId) + "\n");
				msg.append("Low before " + dayLoop + " day : " + toMoneyStr(beforeLow, marketId) + "\n");
				msg.append("Low difference : " + toSignMoneyStr(currentLow - beforeLow, marketId) + " (" + toSignPercent(currentLow, beforeLow) + ")\n");
				msg.append("\n");
				msg.append("Last at Today : "  + toMoneyStr(currentLast, marketId) + "\n");
				msg.append("Last before " + dayLoop + " day : " + toMoneyStr(beforeLast, marketId) + "\n");
				msg.append("Last difference : " + toSignMoneyStr(currentLast - beforeLast, marketId) + " (" + toSignPercent(currentLast, beforeLast) + ")\n");
				msg.append("\n");
			}
			break; 
		}
		
		return msg.toString();
	}
	
	/**********************************/
	/** Timely Notification Message ***/
	/**********************************/
	public String msgSendTimelyMessage(ClientVo client, TimelyInfoVo coinCurrent, TimelyInfoVo coinBefore, JSONObject coinCurrentMoney, JSONObject coinBeforeMoney) {
		StringBuilder msg = new StringBuilder();
		Market marketId = client.getMarketId();
		Lang lang		= client.getLang();
		int timeLoop 	= client.getTimeloop();
		long localTime	= client.getLocaltime();
		String date 	= TimeStamper.getDateTime(localTime);
		
		double currentValue = coinCurrent.getLast();
		double beforeValue = coinBefore.getLast();
		
		switch(lang) {
		case KR :
			msg.append("현재시각: " + date + "\n");
			if(!coinCurrent.getResult().equals("success")){
				String currentErrorMsg = coinCurrent.getErrorMsg();
				String currentErrorCode = coinCurrent.getErrorCode();
				msg.append("에러발생: " + currentErrorMsg +"\n");
				msg.append("에러코드: " + currentErrorCode +"\n");
				
				if(inBtcs.get(marketId)) {
					double beforeBTC = beforeValue;
					double beforeMoney = coinBeforeMoney.getDouble("last");
					
					msg.append(timeLoop + " 시간 전: " + toMoneyStr(beforeMoney, marketId) + " 원 [" + toBTCStr(beforeBTC) + " BTC]\n");
				} else {
					msg.append(timeLoop + " 시간 전: " + toMoneyStr(beforeValue, marketId) + " 원\n");
				}
			} else{
				if(inBtcs.get(marketId)) {
					double currentBTC = currentValue;
					double beforeBTC = beforeValue;
					double currentMoney = coinCurrentMoney.getDouble("last");
					double beforeMoney = coinBeforeMoney.getDouble("last");

					msg.append("현재가격: " + toMoneyStr(currentMoney, marketId) + " [" + toBTCStr(currentBTC)+ "]\n");
					msg.append(timeLoop + " 시간 전: " + toMoneyStr(beforeMoney, marketId) + " [" + toBTCStr(beforeBTC) + "]\n");
					msg.append("가격차이: " + toSignMoneyStr(currentMoney - beforeMoney, marketId) + " (" + toSignPercent(currentMoney, beforeMoney) + ")\n");
				} else {
					msg.append("현재가격: " + toMoneyStr(currentValue, marketId) + "\n");
					msg.append(timeLoop + " 시간 전: " + toMoneyStr(beforeValue, marketId) + "\n");
					msg.append("가격차이: " + toSignMoneyStr(currentValue - beforeValue, marketId) + " (" + toSignPercent(currentValue, beforeValue) + ")\n");
				}
			}
			break;
			
		case US :
			msg.append("Current Time: " + date + "\n");
			if(!coinCurrent.getResult().equals("success")){
				String currentErrorMsg = coinCurrent.getErrorMsg();
				String currentErrorCode = coinCurrent.getErrorCode();
				msg.append("Error Msg : " + currentErrorMsg +"\n");
				msg.append("Error Code: " + currentErrorCode +"\n");
				
				if(inBtcs.get(marketId)) {
					double beforeBTC = beforeValue;
					double beforeMoney = coinBeforeMoney.getDouble("last");
					
					msg.append("Coin Price before " + timeLoop + " hour : " + toMoneyStr(beforeMoney, marketId) + " [" + toBTCStr(beforeBTC) + "]\n");
				} else {
					msg.append("Coin Price before " + timeLoop + " hour : " + toMoneyStr(beforeValue, marketId) + "\n");
				}
			} else{
				if(inBtcs.get(marketId)) {
					double currentBTC = currentValue;
					double beforeBTC = beforeValue;
					double currentMoney = coinCurrentMoney.getDouble("last");
					double beforeMoney = coinBeforeMoney.getDouble("last");

					msg.append("Coin Price at Current Time : " + toMoneyStr(currentMoney, marketId) + " [" + toBTCStr(currentBTC)+ "]\n");
					msg.append("Coin Price before " + timeLoop + " hour : " + toMoneyStr(beforeMoney, marketId) + " [" + toBTCStr(beforeBTC) + "]\n");
					msg.append("Coin Price Difference : " + toSignMoneyStr(currentMoney - beforeMoney, marketId) + " (" + toSignPercent(currentMoney, beforeMoney) + ")\n");
				} else {
					msg.append("Coin Price at Current Time : " + toMoneyStr(currentValue, marketId) + "\n");
					msg.append("Coin Price before " + timeLoop + " hour : " + toMoneyStr(beforeValue, marketId) + "\n");
					msg.append("Coin Price Difference : " + toSignMoneyStr(currentValue - beforeValue, marketId) + " (" + toSignPercent(currentValue, beforeValue) + ")\n");
				}
			}
			break; 
		}
		
		return msg.toString();
	}

	
	
	

}