package com.cglee079.cointelebot.telegram;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.cglee079.cointelebot.cmd.CMDER;
import com.cglee079.cointelebot.coin.CoinManager;
import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.constants.SET;
import com.cglee079.cointelebot.exception.ServerErrorException;
import com.cglee079.cointelebot.log.Log;
import com.cglee079.cointelebot.model.ClientVo;
import com.cglee079.cointelebot.model.TimelyInfoVo;
import com.cglee079.cointelebot.service.ClientMsgService;
import com.cglee079.cointelebot.service.ClientService;
import com.cglee079.cointelebot.service.ClientSuggestService;
import com.cglee079.cointelebot.util.TimeStamper;

public class TelegramBot extends AbilityBot  {
	@Autowired
	private MessageMaker msgMaker;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ClientMsgService clientMsgService;
	
	@Autowired
	private ClientSuggestService clientSuggestService;
	
	@Autowired
	private CoinManager coinManager;
	
	private KeyboardManager km;
	
	protected TelegramBot(String botToken, String botUsername) {
		super(botToken, botUsername);
		km = new KeyboardManager(); 
	}
	
	@Override
	public int creatorId() {
		return 503609560;
	}

	@Override
	public void onUpdateReceived(Update update) {
		clientMsgService.insert(update);
		
		Message message = null;
		if(update.getMessage() != null) {
			message = update.getMessage();
		} else if( update.getEditedMessage() != null) {
			message = update.getEditedMessage();
		}
		
		User user = message.getFrom();
		String username = user.getLastName() + " " + user.getFirstName();
		Integer userId = user.getId();
		Integer messageId = message.getMessageId();
		String cmd = message.getText();
		
		ClientVo client = clientService.get(userId);
		
		if(message.getText().equals("/start") || client == null) {
			String lang= ID.LANG_KR;
			String msg = "";
			if (clientService.openChat(userId, username)) {
				msg = msgMaker.msgStartService(lang);
				msg += msgMaker.explainHelp(lang);
				sendMessage(userId, null, msg, km.getMainKeyboard(lang));
			} else {
				msg = msgMaker.msgAlreadyStartService(lang);
				sendMessage(userId, null, msg, km.getMainKeyboard(lang));
				sendMessage(userId, messageId, messageInfo(userId), km.getMainKeyboard(lang));
			}
			return ;
		} 
		
		String lang 	= client.getLang();
		String state 	= client.getState();
		String market	= client.getMarket();
		
		switch(state) {
		case ID.STATE_MAIN			: handleMenu(userId, messageId, cmd, market, lang); break;
		case ID.STATE_SET_DAYLOOP 	: handleSetDayloop(userId, messageId, cmd, lang); break;
		case ID.STATE_SET_TIMELOOP 	: handleSetTimeloop(userId, messageId, cmd, lang); break;
		case ID.STATE_SET_MARKET 	: handleSetMarket(userId, messageId, cmd, lang); break;
		case ID.STATE_SET_TARGET 	: handleSetTarget(userId, messageId, cmd, lang); break;
		case ID.STATE_SET_PRICE 	: handleSetPrice(userId, messageId, cmd, market, lang); break;
		case ID.STATE_SET_NUMBER 	: handleSetNumber(userId, messageId, cmd, lang); break;
		case ID.STATE_SEND_MSG 		: handleSendMsg(userId, username, messageId, cmd, lang); break;
		case ID.STATE_CONFIRM_STOP 	: handleConfirmStop(userId, username, messageId, cmd, lang); break;
		case ID.STATE_HAPPY_LINE 	: handleHappyLine(userId, username, messageId, cmd, lang); break;
		case ID.STATE_PREFERENCE	: handlePreference(userId, username, messageId, cmd, lang); break;
		case ID.STATE_PREF_LANGUAGE	: handleSetLanguage(userId, username, messageId, cmd, lang); break;
		case ID.STATE_PREF_TIMEADJUST: handleTimeAdjust(userId, username, messageId, cmd, lang); break;
		}
	}

	/* 메인 메뉴 응답 처리 */
	private void handleMenu(Integer userId, Integer messageId, String cmd, String market, String lang) {
		String state = ID.STATE_MAIN;
		
		if(cmd.equals(CMDER.getMainCurrentPrice(lang))){ //현재가
			sendMessage(userId, messageId, messageCurrentPrice(userId), km.getMainKeyboard(lang));
		} else if(cmd.equals(CMDER.getMainKoreaPremium(lang))){ //한국 프리미엄
			sendMessage(userId, messageId, messageEachMarketPrice(userId), km.getMainKeyboard(lang));
		} else if(cmd.equals(CMDER.getMainBtc(lang))){ // 비트대비변화량
			sendMessage(userId, messageId, messageBTC(userId), km.getMainKeyboard(lang));
		} else if(cmd.equals(CMDER.getMainCalculate(lang))){ //손익금 계산
			sendMessage(userId, messageId, messageCalc(userId), km.getMainKeyboard(lang));
		} else if(cmd.equals(CMDER.getMainInfo(lang))){ // 설정 정보
			sendMessage(userId, messageId, messageInfo(userId), km.getMainKeyboard(lang));
		} else if(cmd.equals(CMDER.getMainCoinList(lang))){ //타 코인 알리미
			sendMessage(userId, messageId, msgMaker.explainCoinList(lang), km.getMainKeyboard(lang));
		} else if(cmd.equals(CMDER.getMainHelp(lang))) { // 도움말
			sendMessage(userId, messageId, msgMaker.explainHelp(lang), km.getMainKeyboard(lang));
		} else if(cmd.equals(CMDER.getMainSupport(lang))){ // 후원하기
			sendMessage(userId, messageId, msgMaker.explainSupport(lang), null);
			sendMessage(userId, null, msgMaker.explainSupportWallet(lang), null);
			sendMessage(userId, null, msgMaker.explainSupportAN(lang), km.getMainKeyboard(lang));
		} else if(cmd.equals(CMDER.getMainSetDayloop(lang))){ // 일일 알림 주기설정
			sendMessage(userId, messageId, msgMaker.explainSetDayloop(lang), km.getSetDayloopKeyboard(lang));
			state = ID.STATE_SET_DAYLOOP;
		} else if(cmd.equals(CMDER.getMainSetTimeloop(lang))){ // 시간 알림 주기 설정
			sendMessage(userId, messageId, msgMaker.explainSetTimeloop(lang), km.getSetTimeloopKeyboard(lang));
			state = ID.STATE_SET_TIMELOOP;
		} else if(cmd.equals(CMDER.getMainSetMarket(lang))){ // 거래소 설정
			sendMessage(userId, messageId, msgMaker.explainMarketSet(lang), km.getSetMarketKeyboard(lang));
			state = ID.STATE_SET_MARKET;
		} else if(cmd.equals(CMDER.getMainSetTarget(lang))){ // 목표가 설정
			sendMessage(userId, messageId, msgMaker.explainTargetPriceSet(lang, market), km.getDefaultKeyboard());
			state = ID.STATE_SET_TARGET;
		} else if(cmd.equals(CMDER.getMainSetPrice(lang))){ // 투자금액 설정
			sendMessage(userId, messageId, msgMaker.explainSetPrice(lang, market), km.getDefaultKeyboard());
			state = ID.STATE_SET_PRICE;
		} else if(cmd.equals(CMDER.getMainSetNumber(lang))){ // 코인개수 설정
			sendMessage(userId, messageId, msgMaker.explainSetCoinCount(lang), km.getDefaultKeyboard());
			state = ID.STATE_SET_NUMBER;
		} else if(cmd.equals(CMDER.getMainSendMsg(lang))) {// 문의/건의
			sendMessage(userId, messageId, msgMaker.explainSendSuggest(lang), km.getDefaultKeyboard());
			state = ID.STATE_SEND_MSG;
		} else if(cmd.equals(CMDER.getMainStop(lang))){ // 모든알림 중지
			sendMessage(userId, messageId, msgMaker.explainStop(lang), km.getConfirmStopKeyboard(lang));
			state = ID.STATE_CONFIRM_STOP;
		} else if (cmd.equals(CMDER.getMainHappyLine(lang))){ // 행복회로
			state = checkHappyLine(userId, messageId, market, lang);
		} else if(cmd.equals(CMDER.getMainPref(lang))){ // 환경설정
			sendMessage(userId, messageId, "Set Preference", km.getPreferenceKeyboard(lang));
			state = ID.STATE_PREFERENCE;
		} else {
			sendMessage(userId, messageId, messageCurrentPrice(userId), km.getMainKeyboard(lang));
		}
		
		clientService.updateState(userId.toString(), state);
	}

	
	/* 일일 알림 설정 응답 처리 */
	private void handleSetDayloop(Integer userId, Integer messageId, String cmd, String lang) {
		int dayloop = -1;
		String msg = "";
		
		if(cmd.equals(CMDER.getSetDayloop01(lang))) { dayloop = 1;}
		else if(cmd.equals(CMDER.getSetDayloop02(lang))) { dayloop = 2;}
		else if(cmd.equals(CMDER.getSetDayloop03(lang))) { dayloop = 3;}
		else if(cmd.equals(CMDER.getSetDayloop04(lang))) { dayloop = 4;}
		else if(cmd.equals(CMDER.getSetDayloop05(lang))) { dayloop = 5;}
		else if(cmd.equals(CMDER.getSetDayloop06(lang))) { dayloop = 6;}
		else if(cmd.equals(CMDER.getSetDayloop07(lang))) { dayloop = 7;}
		else if(cmd.equals(CMDER.getSetDayloopOff(lang))) { dayloop = 0;}
		
		if(dayloop == -1) {
			msg = msgMaker.msgDayloopNoSet(lang);
		} else {
			if(clientService.updateDayLoop(userId.toString(), dayloop)) {
				if(dayloop == 0) {
					msg = msgMaker.msgDayloopStop(lang);
				} else {
					msg = msgMaker.msgDayloopSet(dayloop, lang);
				}
			} else {
				msg =  msgMaker.msgDayloopNoSet(lang);
			}
		}
		
		msg += msgMaker.msgToMain(lang);
		
		sendMessage(userId, messageId, msg, km.getMainKeyboard(lang));
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	/* 시간 알림 설정 응답 처리 */
	private void handleSetTimeloop(Integer userId, Integer messageId, String cmd, String lang) {
		int timeloop = -1;
		String msg = "";
		
		if(cmd.equals(CMDER.getSetTimeloop01(lang))) { timeloop = 1;}
		else if(cmd.equals(CMDER.getSetTimeloop02(lang))) { timeloop = 2;}
		else if(cmd.equals(CMDER.getSetTimeloop03(lang))) { timeloop = 3;}
		else if(cmd.equals(CMDER.getSetTimeloop04(lang))) { timeloop = 4;}
		else if(cmd.equals(CMDER.getSetTimeloop05(lang))) { timeloop = 5;}
		else if(cmd.equals(CMDER.getSetTimeloop06(lang))) { timeloop = 6;}
		else if(cmd.equals(CMDER.getSetTimeloop07(lang))) { timeloop = 7;}
		else if(cmd.equals(CMDER.getSetTimeloop08(lang))) { timeloop = 8;}
		else if(cmd.equals(CMDER.getSetTimeloop09(lang))) { timeloop = 9;}
		else if(cmd.equals(CMDER.getSetTimeloop10(lang))) { timeloop = 10;}
		else if(cmd.equals(CMDER.getSetTimeloop11(lang))) { timeloop = 11;}
		else if(cmd.equals(CMDER.getSetTimeloop12(lang))) { timeloop = 12;}
		else if(cmd.equals(CMDER.getSetTimeloopOff(lang))) { timeloop = 0;}
		
		if(timeloop == -1) {
			msg = msgMaker.msgTimeloopNoSet(lang);
		} else {
			if(clientService.updateTimeLoop(userId.toString(), timeloop)) {
				 if(timeloop == 0) {
					msg = msgMaker.msgTimeloopStop(lang);
				 } else {
					msg = msgMaker.msgTimeloopSet(timeloop, lang);
				 }
			} else {
				msg = msgMaker.msgTimeloopNoSet(lang);
			}
		}
		
		msg += msgMaker.msgToMain(lang);
		
		sendMessage(userId, messageId, msg, km.getMainKeyboard(lang));
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	/* 마켓 설정 응답 처리 */
	private void handleSetMarket(Integer userId, Integer messageId, String cmd, String lang) {
		String market = null;
		String msg = "";
		msg = msgMaker.msgMarketNoSet(lang);
		
		if(SET.ENABLED_COINONE && cmd.equals(CMDER.getSetMarketCoinone(lang))) {
			market = ID.MARKET_COINONE;
			msg = msgMaker.msgMarketSet( ID.MARKET_COINONE, lang);
		}
		
		if(SET.ENABLED_BITHUMB && cmd.equals(CMDER.getSetMarketBithumb(lang))) {
			market = ID.MARKET_BITHUMB;
			msg = msgMaker.msgMarketSet( ID.MARKET_BITHUMB, lang);
		}
		
		if(SET.ENABLED_UPBIT && cmd.equals(CMDER.getSetMarketUpbit(lang))) {
			market = ID.MARKET_UPBIT;
			msg = msgMaker.msgMarketSet(ID.MARKET_UPBIT, lang);
		}
		
		if(SET.ENABLED_COINNEST && cmd.equals(CMDER.getSetMarketCoinnest(lang))) {
			market = ID.MARKET_COINNEST;
			msg = msgMaker.msgMarketSet(ID.MARKET_COINNEST, lang);
		}
		
		if(SET.ENABLED_KORBIT && cmd.equals(CMDER.getSetMarketKorbit(lang))) {
			market = ID.MARKET_KORBIT;
			msg = msgMaker.msgMarketSet(ID.MARKET_KORBIT, lang);
		}
		
		if(SET.ENABLED_BITFINEX && cmd.equals(CMDER.getSetMarketBitfinex(lang))) {
			market = ID.MARKET_BITFINNEX;
			msg = msgMaker.msgMarketSet(ID.MARKET_BITFINNEX, lang);
		}
		
		if(SET.ENABLED_BITTREX && cmd.equals(CMDER.getSetMarketBittrex(lang))) {
			market = ID.MARKET_BITTREX;
			msg = msgMaker.msgMarketSet(ID.MARKET_BITTREX, lang);
		}
		
		if(SET.ENABLED_POLONIEX && cmd.equals(CMDER.getSetMarketPoloniex(lang))) {
			market = ID.MARKET_POLONIEX;
			msg = msgMaker.msgMarketSet(ID.MARKET_POLONIEX, lang);
		}
		
		if(SET.ENABLED_BINANCE && cmd.equals(CMDER.getSetMarketBinance(lang))) {
			market = ID.MARKET_BINANCE;
			msg = msgMaker.msgMarketSet(ID.MARKET_BINANCE, lang);
		}
		
		
		if(market != null) {
			ClientVo client = clientService.get(userId);
			String currentMarket 		= client.getMarket();
			Double currentPrice 		= client.getPrice();
			Double currentTargetUp 		= client.getTargetUpPrice();
			Double currentTargetDown	= client.getTargetDownPrice();
			double exchangeRate			= coinManager.getExchangeRate();
			
			Double changePrice 	 	= currentPrice;
			Double changeTargetUp	= currentTargetUp;
			Double changeTargetDown	= currentTargetDown;
			
			if(currentMarket.startsWith(ID.MARKET_KR) && market.startsWith(ID.MARKET_US)) {
				if(currentPrice != null) { changePrice = currentPrice / exchangeRate; }
				if(currentTargetUp != null) { changeTargetUp = currentTargetUp / exchangeRate; }
				if(currentTargetDown != null) { changeTargetDown = currentTargetDown / exchangeRate; }
				
				msg += msgMaker.msgMarketSetChangeCurrency(client, changePrice, changeTargetUp, changeTargetDown, market);
				
			}
			
			if(currentMarket.startsWith(ID.MARKET_US) && market.startsWith(ID.MARKET_KR)) {
				if(currentPrice != null) { changePrice = currentPrice * exchangeRate; }
				if(currentTargetUp != null) { changeTargetUp = currentTargetUp * exchangeRate; }
				if(currentTargetDown != null) { changeTargetDown = currentTargetDown * exchangeRate; }
				
				msg += msgMaker.msgMarketSetChangeCurrency(client, changePrice, changeTargetUp, changeTargetDown, market);
			}
			
			client.setPrice(changePrice);
			client.setTargetUpPrice(changeTargetUp);
			client.setTargetDownPrice(currentTargetDown);
			client.setMarket(market);
			clientService.update(client);
			
		} else {
			msg = msgMaker.msgMarketNoSet(lang);
		}
		msg += msgMaker.msgToMain(lang);
		
		sendMessage(userId, messageId, msg, km.getMainKeyboard(lang));
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	
	/* 목표가 설정 응답 처리 */
	private void handleSetTarget(Integer userId, Integer messageId, String cmd, String lang) {
		ClientVo client = clientService.get(userId);
		String market = client.getMarket();
		String msg = "";
		boolean valid = false;
		
		double currentValue = -1;
		try {
			JSONObject coinObj = coinManager.getCoin(SET.MY_COIN, market);
			currentValue = coinObj.getDouble("last");
			
			if(SET.isInBtcMarket(market)) {
				currentValue = coinManager.getMoney(coinObj, market).getDouble("last");
			}
			
		}
		catch (ServerErrorException e) {
			Log.i(e.log());
			e.printStackTrace();
			msg = msgMaker.warningWaitSecond(lang)+ e.getTelegramMsg();
		}
		
		if(currentValue != -1) {
			String priceStr = cmd.trim();
			double targetPrice = -1;

			if(priceStr.matches("^\\d*(\\.?\\d*)$")) {
				targetPrice = Double.valueOf(priceStr);
				
				if (targetPrice == 0) {  // case4. 초기화
					if(clientService.clearTargetPrice(userId.toString())) {
						msg = msgMaker.msgTargetPriceInit(lang);
					}
				} else {
					valid = true;
				}
				
			} else if( priceStr.matches("^[+-]?\\d*(\\.?\\d*)%$")) {
				if(priceStr.equals("%")) {
					priceStr = "0%";
				}
					
				priceStr = priceStr.replace("%", "");
				double percent = (Double.valueOf(priceStr)/100);
				
				if(percent == 0) {
					valid = true;
					targetPrice =  currentValue;
				} else if(percent > 0) {
					valid = true;
					targetPrice = currentValue + currentValue * percent;
				}  else if( percent < 0 && percent >= -100) {
					valid = true;
					double a = (currentValue * percent) * -1;
					targetPrice = currentValue - a;
				} else if( percent < -100) {
					msg = msgMaker.warningTargetPriceSetPercent(lang);
				}
				
			} else {
				msg = msgMaker.warningTargetPriceSetFormat(lang);
			}
			
			if(valid) {
				msg += msgMaker.msgTargetPriceSetResult(targetPrice, currentValue, market, lang);
				if(targetPrice >= currentValue) {
					if(!clientService.updateTargetUpPrice(userId.toString(), targetPrice)){
						msg = msgMaker.warningNeedToStart(lang);
					}
				} else {
					if(!clientService.updateTargetDownPrice(userId.toString(), targetPrice)){
						msg = msgMaker.warningNeedToStart(lang);
					}
				}
			}
			
		}
		
		msg += msgMaker.msgToMain(lang);
		sendMessage(userId, messageId, msg, km.getMainKeyboard(lang));
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	
	/* 투자 금액 설정 응답 처리 */
	private void handleSetPrice(Integer userId, Integer messageId, String cmd, String market, String lang) {
		String priceStr = cmd;
		String msg = "";
		double price = -1;

		try { // case1. 평균단가에 문자가 포함될때
			price = Double.parseDouble(priceStr);
		} catch (NumberFormatException e) {
			msg = msgMaker.warningPriceFormat(lang);
		}

		if(price != -1) {
			if(clientService.updatePrice(userId.toString(), price)){
				if (price == 0) { msg = msgMaker.msgPriceInit(lang);} // case2. 초기화
				else {msg = msgMaker.msgPriceSet(price, market, lang);} // case3.설정완료
			} else{
				msg = msgMaker.warningNeedToStart(lang);
			}
		}
		
		msg += msgMaker.msgToMain(lang);
		
		sendMessage(userId, messageId, msg, km.getMainKeyboard(lang));
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	/* 코인 개수 설정 응답 처리 */
	private void handleSetNumber(Integer userId, Integer messageId, String cmd, String lang) {
		String numberStr = cmd;
		String msg = "";
		double number = -1;

		try { // case1. 코인개수에 문자가 포함될때
			number = Double.parseDouble(numberStr);
		} catch (NumberFormatException e) {
			msg = msgMaker.warningCoinCountFormat(lang);
		}

		if(number != -1) {
			if(clientService.updateNumber(userId.toString(), number)){
				if (number == 0) { msg = msgMaker.msgCoinCountInit(lang);} // case2. 초기화
				else {msg = msgMaker.msgCoinCountSet(number, lang);} // case3.설정완료
			} else{
				msg = msgMaker.warningNeedToStart(lang);
			}
		}
		
		msg += msgMaker.msgToMain(lang);
		
		sendMessage(userId, messageId, msg, km.getMainKeyboard(lang));
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	/* 개발자에게 메세지 보내기, 응답 처리 */
	private void handleSendMsg(Integer userId, String username, Integer messageId, String message, String lang) {
		if(message.equals(CMDER.getSendMsgOut(lang))){
			sendMessage(userId, messageId, msgMaker.msgToMain(lang), km.getMainKeyboard(lang));
		} else if(message.equals(CMDER.getOut(lang))){
			sendMessage(userId, messageId, msgMaker.msgToMain(lang), km.getMainKeyboard(lang));
		} else if(message.equals(CMDER.getOut(lang))){
			clientSuggestService.insert(userId, username, message);
			sendMessage(userId, messageId, msgMaker.msgThankyouSuggest(lang), km.getMainKeyboard(lang));
			
			//To Developer
			String msg = "";
			msg += "메세지가 도착했습니다!\n------------------\n\n";
			msg += message;
			msg += "\n\n------------------\n";
			msg += " By ";
			msg += username + " [" + userId + " ]";
			
			sendMessage(this.creatorId(), null, msg, km.getMainKeyboard(lang));
		}
		
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	
	/* 모든 알림 중지 응답 처리 */
	private void handleConfirmStop(Integer userId, String username, Integer messageId, String cmd, String lang) {
		String msg = "";
		if(cmd.equals(CMDER.getConfirmStopYes(lang))) {
			if (clientService.stopChat(userId)) {
				msg = msgMaker.msgStopAllNotice(lang);
			} else {
				msg = msgMaker.warningNeedToStart(lang);
			}
		} else {
			msg = "\n";
		} 
		
		msg += msgMaker.msgToMain(lang);
		
		sendMessage(userId, messageId, msg, km.getMainKeyboard(lang));
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	
	/* 행복회로 메뉴 클릭 시, 투자금액 && 코인개수 설정 입력되었는지 확인 */
	private String checkHappyLine(Integer userId, Integer messageId, String market, String lang) {
		String state = ID.STATE_MAIN;
		String msg = "";
		ClientVo client = clientService.get(userId);
		if(client != null){
			if( client.getPrice() == null){ 
				msg = msgMaker.msgPleaseSetInvestmentAmount(lang);
				sendMessage(userId, messageId, msg, km.getMainKeyboard(lang));
			} else if( client.getCoinCount() == null){ 
				msg = msgMaker.msgPleaseSetTheNumberOfCoins(lang);
				sendMessage(userId, messageId, msg, km.getMainKeyboard(lang));
			} else {
				msg = msgMaker.explainHappyLine(market, lang);
				sendMessage(userId, messageId, msg, km.getDefaultKeyboard());
				state = ID.STATE_HAPPY_LINE;
			}
		} 
		
		return state;
	}
	
	/* 행복 회로 응답 처리 */
	private void handleHappyLine(Integer userId, String username, Integer messageId, String cmd, String lang) {
		String msg = "";
		String priceStr = cmd;
		double happyPrice = -1;

		try { // case1. 평균단가에 문자가 포함될때
			happyPrice = Double.parseDouble(priceStr);
		} catch (NumberFormatException e) {
			msg = msgMaker.warningHappyLineFormat(lang);
		}

		if(happyPrice != -1) {
			ClientVo client = clientService.get(userId);
			double price = client.getPrice();
			double coinCnt = client.getCoinCount();
			String market = client.getMarket();
			
			msg = msgMaker.msgHappyLineResult(price, coinCnt, happyPrice, market, lang);
		}
		
		msg += msgMaker.msgToMain(lang);
		
		sendMessage(userId, messageId, msg, km.getMainKeyboard(lang));
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
		
	}

	/* 환경설정 응답 처리 */
	private void handlePreference(Integer userId, String username, Integer messageId, String cmd, String lang) {
		String state = ID.STATE_MAIN;
		if(cmd.equals(CMDER.getPrefLang(lang))) {
			sendMessage(userId, messageId, msgMaker.explainSetLanguage(lang), km.getSetLanguageKeyboard(lang));
			state = ID.STATE_PREF_LANGUAGE;
		} else if(cmd.equals(CMDER.getPrefTimeAjdust(lang))) {
			sendMessage(userId, messageId, msgMaker.explainTimeAdjust(lang), km.getDefaultKeyboard(lang));
			state = ID.STATE_PREF_TIMEADJUST;
		} else if(cmd.equals(CMDER.getPrefOut(lang))){
			sendMessage(userId, messageId, msgMaker.msgToMain(lang), km.getMainKeyboard(lang));
		} else {
			sendMessage(userId, messageId, msgMaker.msgToMain(lang), km.getMainKeyboard(lang));
		}
		
		clientService.updateState(userId.toString(), state);
	}
	
	
	/* 환경설정 - 언어 응답 처리 */
	private void handleSetLanguage(Integer userId, String username, Integer messageId, String cmd, String lang) {
		String msg = "";
		String langID = lang;
		
		if(cmd.equals(CMDER.getSetLanguageKR(lang))) {
			langID = ID.LANG_KR;
		} else if(cmd.equals(CMDER.getSetLanguageUS(lang))) {
			langID = ID.LANG_US;
		} else if(cmd.equals(CMDER.getSetLanguageOut(lang))) {
			
		} 
		
		if(clientService.updateLanguage(userId.toString(), langID)) {
			msg += msgMaker.msgSetLanguageSuccess(langID);
			msg += msgMaker.msgToMain(langID);
		} else {
			msg += msgMaker.warningWaitSecond(lang);
			msg += msgMaker.msgToMain(lang);
		}
		
		
		sendMessage(userId, messageId, msg, km.getMainKeyboard(langID));
		
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
		
	}
	
	
	/* 환경설정 시차 조절 응답 처리 */
	private void handleTimeAdjust(Integer userId, String username, Integer messageId, String cmd, String lang) {
		String msg = "";
		String enteredDateStr = cmd;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		if(cmd.equals("0")) {
			clientService.updateLocalTime(userId.toString(), (long)0);
			msg += msgMaker.msgTimeAdjustSuccess(new Date());
			msg += msgMaker.msgToMain(lang);
		} else {
			try {
				Date enteredDate = format.parse(enteredDateStr);
				Date currentDate = new Date();
				long time = enteredDate.getTime() - currentDate.getTime();
				
				clientService.updateLocalTime(userId.toString(), time);
				
				msg += msgMaker.msgTimeAdjustSuccess(enteredDate);
				msg += msgMaker.msgToMain(lang);
			} catch (ParseException e) {
				msg += msgMaker.warningTimeAdjustFormat("");
				msg += msgMaker.msgToMain(lang);
			}
		}
		
		
		sendMessage(userId, messageId, msg, km.getMainKeyboard(lang));
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	
	/* 현재가 */
	private String messageCurrentPrice(Integer userId) {
		JSONObject coinObj = null;
		double currentValue = 0;
		ClientVo client = clientService.get(userId);
		String market 	= client.getMarket();
		String lang		= client.getLang();
		
		try {
			coinObj = coinManager.getCoin(SET.MY_COIN, market);
		} catch (ServerErrorException e) {
			Log.i(e.log());
			e.printStackTrace();
			return msgMaker.warningWaitSecond(lang) + e.getTelegramMsg();
		}
		
		if(coinObj == null) {
			Log.i("가격정보를 보낼 수 없습니다. : return NULL");
			return msgMaker.warningWaitSecond(lang) + "Coin NULL";
		}
		
		currentValue = coinObj.getDouble("last");
		
			
		JSONObject coinMoney = null;
		if(SET.isInBtcMarket(market)) { 
			coinMoney = coinManager.getMoney(coinObj, market);
		}
		
		return msgMaker.msgCurrentPrice(currentValue, coinMoney, client);
	}

	
	/* 거래소별 가격 */
	public String messageEachMarketPrice(Integer userId) {
		LinkedHashMap<String, Double> lasts = new LinkedHashMap<>();
		
		if(SET.ENABLED_COINONE) { lasts.put(ID.MARKET_COINONE, coinManager.getCoinLast(ID.MARKET_COINONE));}
		if(SET.ENABLED_BITHUMB) { lasts.put(ID.MARKET_BITHUMB, coinManager.getCoinLast(ID.MARKET_BITHUMB));}
		if(SET.ENABLED_UPBIT)	{ lasts.put(ID.MARKET_UPBIT, coinManager.getCoinLast(ID.MARKET_UPBIT));}
		if(SET.ENABLED_COINNEST){ lasts.put(ID.MARKET_COINNEST, coinManager.getCoinLast(ID.MARKET_COINNEST));}
		if(SET.ENABLED_KORBIT) 	{ lasts.put(ID.MARKET_KORBIT, coinManager.getCoinLast(ID.MARKET_KORBIT));}
		if(SET.ENABLED_BITFINEX){ lasts.put(ID.MARKET_BITFINNEX, coinManager.getCoinLast(ID.MARKET_BITFINNEX));}
		if(SET.ENABLED_BITTREX) { lasts.put(ID.MARKET_BITTREX, coinManager.getCoinLast(ID.MARKET_BITTREX));}
		if(SET.ENABLED_POLONIEX){ lasts.put(ID.MARKET_POLONIEX, coinManager.getCoinLast(ID.MARKET_POLONIEX));}
		if(SET.ENABLED_BINANCE) { lasts.put(ID.MARKET_BINANCE, coinManager.getCoinLast(ID.MARKET_BINANCE));}
		
		ClientVo client = clientService.get(userId);
		double exchangeRate = coinManager.getExchangeRate();
		return msgMaker.msgEachMarketPrice(exchangeRate, lasts, client);
	}
	
	/* 비트코인 대비 변화량 */
	public String messageBTC(Integer userId) {
		JSONObject coin = null;
		JSONObject btc = null;
		String msg = "";
		
		ClientVo client = clientService.get(userId);
		String market 	= client.getMarket();
		String lang 	= client.getLang();
		String date		= TimeStamper.getDateTime(client.getLocalTime());
		
		msg += msgMaker.msgBTCCurrentTime(date, lang);
		
		if(market.startsWith(ID.MARKET_KR) && (market.equals(ID.MARKET_COINNEST) || market.equals(ID.MARKET_KORBIT))) {
			if(market.equals(ID.MARKET_COINNEST)) { msg += msgMaker.msgBTCNotSupportAPI(ID.MARKET_COINNEST, lang); }
			if(market.equals(ID.MARKET_KORBIT)) { msg += msgMaker.msgBTCNotSupportAPI(ID.MARKET_KORBIT, lang); }
			
			if(SET.ENABLED_COINONE) {
				msg += msgMaker.msgBTCReplaceAnotherMarket(ID.MARKET_COINONE, lang);
				market = ID.MARKET_COINONE;
			} else if(SET.ENABLED_BITHUMB) {
				msg += msgMaker.msgBTCReplaceAnotherMarket(ID.MARKET_BITHUMB, lang);
				market = ID.MARKET_BITHUMB;
			} else if(SET.ENABLED_UPBIT) {
				msg += msgMaker.msgBTCReplaceAnotherMarket(ID.MARKET_UPBIT, lang);
				market = ID.MARKET_UPBIT;
			} else {
				msg += msgMaker.msgToMain(lang);
				return msg;
			}
			msg += "\n";
		}
		
		if(market.startsWith(ID.MARKET_US) && (market.equals(ID.MARKET_BITTREX))) {
			if(market.equals(ID.MARKET_BITTREX)) { msg += msgMaker.msgBTCNotSupportAPI(ID.MARKET_BITTREX, lang); }
			
			if(SET.ENABLED_BITFINEX) {
				msg += msgMaker.msgBTCReplaceAnotherMarket(ID.MARKET_BITFINNEX, lang);
				market = ID.MARKET_POLONIEX;
			} else if(SET.ENABLED_POLONIEX) {
				msg += msgMaker.msgBTCReplaceAnotherMarket(ID.MARKET_POLONIEX, lang);
				market = ID.MARKET_POLONIEX;
			} else if(SET.ENABLED_BINANCE) {
				msg += msgMaker.msgBTCReplaceAnotherMarket(ID.MARKET_BINANCE, lang);
				market = ID.MARKET_BINANCE;
			}
			msg += "\n";
		}
		
		try {
			coin 	= coinManager.getCoin(SET.MY_COIN, market);
			btc 	= coinManager.getCoin(ID.COIN_BTC, market);
		} catch (ServerErrorException e) {
			Log.i(e.log());
			e.printStackTrace();			
			return msgMaker.warningWaitSecond(lang) + e.getTelegramMsg();
		}
	
		if(coin != null && btc != null){
			double coinCV = coin.getDouble("last");
			double coinBV = coin.getDouble("first");
			double btcCV = btc.getDouble("last");
			double btcBV = btc.getDouble("first");
			JSONObject coinMoney = null;
			if(SET.isInBtcMarket(market)) {
				coinMoney = coinManager.getMoney(coin, market);
			}
			msg += msgMaker.msgBTCResult(coinCV, coinBV, btcCV, btcBV, coinMoney, market, lang);
			
			return msg;
		} else {
			return msgMaker.warningNeedToStart(lang) + "Coin NULL\n";
		}
		
	}
	
	/* 손익금 계산 */
	public String messageCalc(Integer userId) {
		ClientVo client = clientService.get(userId);
		String lang		= client.getLang();
		
		if( client.getPrice() == null){ return msgMaker.msgPleaseSetInvestmentAmount(lang);}
		else if( client.getCoinCount() == null){ return msgMaker.msgPleaseSetTheNumberOfCoins(lang);}
		else {
			try {
				JSONObject coin = coinManager.getCoin(SET.MY_COIN, client.getMarket());
				return calcResult(client, coin.getDouble("last"));
			} catch (ServerErrorException e) {
				Log.i(e.log());
				e.printStackTrace();
				return msgMaker.warningWaitSecond(lang) + e.getTelegramMsg();
			}
		}
	}
	
	public String calcResult(ClientVo client, Double coinValue) {
		double price 		= client.getPrice();
		double cnt 		= client.getCoinCount();
		String lang 	= client.getLang();
		String market	= client.getMarket();
		
		double avgPrice = (double)((double)price / cnt);
		
		JSONObject btcObj = null;
		if(SET.isInBtcMarket(market)) {
			try {
				btcObj = coinManager.getCoin(ID.COIN_BTC, client.getMarket());
			} catch (ServerErrorException e) {
				Log.i(e.log());
				e.printStackTrace();
				return msgMaker.warningWaitSecond(lang) + e.getTelegramMsg();
			}
		}
		
		return msgMaker.msgCalcResult(price, cnt, avgPrice, coinValue, btcObj, client);
	}
	
	
	/* 설정 정보 확인 */
	public String messageInfo(Integer userId) {
		ClientVo client = clientService.get(userId);
		String lang = client.getLang();
		
		if(client == null){
			return msgMaker.warningNeedToStart(lang);
		}
		
		return msgMaker.msgClientSetting(client, lang);
	}
	
	
	/******************/
	/** Send Message **/
	/******************/
	public void sendMessage(Integer id, Integer msgId, String msg, ReplyKeyboard keyboard){
		this.sendMessage(id.toString(), msgId, msg, keyboard);
	}
	
	public void sendMessage(String id, Integer msgId, String msg, ReplyKeyboard keyboard){
		Log.i("To Client\t:\t[id :" +id + " ]  " + msg.replace("\n", "  "));
		
		SendMessage sendMessage = new SendMessage(id, msg);
		sendMessage.setReplyToMessageId(msgId);
		
		if(keyboard != null) { 
			sendMessage.setReplyMarkup(keyboard);
		} 
		
		try {
			sender.execute(sendMessage);
		} catch (TelegramApiException e) {
			Log.i("To Client Error\t:\t[id :" + id + " ]  에게 메세지를 보낼 수 없습니다.  :" + e.getMessage());
			e.printStackTrace();
			clientService.increaseErrCnt(id);
			return ;
		}
		
	}
	
	/* 목표가 알림 */
	public void sendTargetPriceMessage(List<ClientVo> clients, String market, JSONObject coinObj, boolean isUp) {
		double currentValue = coinObj.getDouble("last");
		if(SET.isInBtcMarket(market)) {
			currentValue = coinManager.getMoney(coinObj, market).getDouble("last");
		}

		ClientVo client = null;
		String lang 	= null;
		String msg 		= "";
		
		double targetPrice = -1;
		int clientLength = clients.size();
		for(int i = 0; i < clientLength; i++){
			client 	= clients.get(i);
			lang 	= client.getLang();
			targetPrice 	= -1;
			
			if(isUp) { targetPrice =  client.getTargetUpPrice(); }
			else  { targetPrice =  client.getTargetDownPrice(); }
				
			msg = msgMaker.msgTargetPriceNotify(currentValue, targetPrice, market, lang);
			sendMessage(client.getUserId(), null, msg, null);
			if(clientService.clearTargetPrice(client.getUserId())) {
				sendMessage(client.getUserId(), null, msgMaker.msgTargetPriceInit(lang), null);
			}
		}
	}
	
	/* 시간 알림 */
	public void sendTimelyMessage(List<ClientVo> clients, String market, TimelyInfoVo coinCurrent, TimelyInfoVo coinBefore){
		ClientVo client = null;
		
		JSONObject coinCurrentMoney = null;
		JSONObject coinBeforeMoney = null;
		if(SET.isInBtcMarket(market)) {
			coinCurrentMoney = coinManager.getMoney(coinCurrent, market);
			coinBeforeMoney = coinManager.getMoney(coinBefore, market);
		}
		
		int clientLength = clients.size();
		for (int i = 0; i < clientLength; i++) {
			client = clients.get(i);
			String msg = msgMaker.msgSendTimelyMessage(client, coinCurrent, coinBefore, coinCurrentMoney, coinBeforeMoney);
			sendMessage(client.getUserId(), null, msg, null);
		}
	}
	
	/* 일일 알림 */
	public void sendDailyMessage(List<ClientVo> clients, String market, TimelyInfoVo coinCurrent, TimelyInfoVo coinBefore){
		ClientVo client = null;
		int clientLength = clients.size();
		
		JSONObject coinCurrentMoney = null;
		JSONObject coinBeforeMoney = null;
		if(SET.isInBtcMarket(market)) {
			coinCurrentMoney = coinManager.getMoney(coinCurrent, market);
			coinBeforeMoney = coinManager.getMoney(coinBefore, market);
		}
		
		String msg = null;
		for(int i = 0; i < clientLength; i++){
			client 	= clients.get(i);
			msg 	= msgMaker.msgSendDailyMessage(client, coinCurrent, coinBefore, coinCurrentMoney, coinBeforeMoney);
			sendMessage(client.getUserId(), null, msg, null);
			
			if(client.getCoinCount() != null && client.getPrice() != null){
				sendMessage(client.getUserId(), null, calcResult(client, coinCurrent.getLast()), null);
			}
		}
	}
}


