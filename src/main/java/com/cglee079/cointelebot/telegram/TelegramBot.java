package com.cglee079.cointelebot.telegram;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.cglee079.cointelebot.coin.CoinManager;
import com.cglee079.cointelebot.constants.CMD;
import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.constants.MSG;
import com.cglee079.cointelebot.constants.SET;
import com.cglee079.cointelebot.exception.ServerErrorException;
import com.cglee079.cointelebot.keyboard.ConfirmStopKeyboard;
import com.cglee079.cointelebot.keyboard.MainKeyboard;
import com.cglee079.cointelebot.keyboard.SetDayloopKeyboard;
import com.cglee079.cointelebot.keyboard.SetExchangeKeyboard;
import com.cglee079.cointelebot.keyboard.SetTimeloopKeyboard;
import com.cglee079.cointelebot.log.Log;
import com.cglee079.cointelebot.model.ClientVo;
import com.cglee079.cointelebot.model.DailyInfoVo;
import com.cglee079.cointelebot.model.TimelyInfoVo;
import com.cglee079.cointelebot.service.ClientMsgService;
import com.cglee079.cointelebot.service.ClientService;
import com.cglee079.cointelebot.service.ClientSuggestService;
import com.cglee079.cointelebot.util.TimeStamper;

public class TelegramBot extends AbilityBot  {
	@Autowired
	private Explainer exp;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ClientMsgService clientMsgService;
	
	@Autowired
	private ClientSuggestService clientSuggestService;
	
	@Autowired
	private CoinManager coinManager;
	
	private ReplyKeyboardRemove defaultKeyboard;
	private MainKeyboard mainKeyboard;
	private SetDayloopKeyboard setDayloopKeyboard;
	private SetTimeloopKeyboard setTimeloopKeyboard;
	private SetExchangeKeyboard setExchangeKeyboard;
	private ConfirmStopKeyboard confirmStopKeyboard;
	
	protected TelegramBot(String botToken, String botUsername) {
		super(botToken, botUsername);
		
		defaultKeyboard = new ReplyKeyboardRemove();
		mainKeyboard = new MainKeyboard();
		setDayloopKeyboard = new SetDayloopKeyboard();
		setTimeloopKeyboard = new SetTimeloopKeyboard();
		setExchangeKeyboard = new SetExchangeKeyboard();
		confirmStopKeyboard = new ConfirmStopKeyboard();
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
			String msg = "";
			if (clientService.openChat(userId, username)) {
				msg = SET.MY_COIN + " 알림이 시작되었습니다.\n\n";
				msg += exp.explainHelp();
				sendMessage(userId, null, msg, mainKeyboard);
			} else {
				msg = "이미 " + SET.MY_COIN + " 알리미에 설정 정보가 기록되어있습니다.";
				sendMessage(userId, null, msg, mainKeyboard);
				sendMessage(userId, messageId, messageInfo(userId), mainKeyboard);
			}
			return ;
		} 
		
		String state = client.getState();
		switch(state) {
		case ID.STATE_MAIN: handleMenu(userId, messageId, cmd); break;
		case ID.STATE_SET_DAYLOOP : handleSetDayloop(userId, messageId, cmd); break;
		case ID.STATE_SET_TIMELOOP : handleSetTimeloop(userId, messageId, cmd); break;
		case ID.STATE_SET_EXCHANGE : handleSetExchange(userId, messageId, cmd); break;
		case ID.STATE_SET_TARGET : handleSetTarget(userId, messageId, cmd); break;
		case ID.STATE_SET_PRICE : handleSetPrice(userId, messageId, cmd); break;
		case ID.STATE_SET_NUMBER : handleSetNumber(userId, messageId, cmd); break;
		case ID.STATE_SEND_MSG : handleSendMsg(userId, username, messageId, cmd); break;
		case ID.STATE_CONFIRM_STOP : handleConfirmStop(userId, username, messageId, cmd); break;
		case ID.STATE_HAPPY_LINE : handleHappyLine(userId, username, messageId, cmd); break;
		}
	}

	private void handleMenu(Integer userId, Integer messageId, String cmd) {
		String state = ID.STATE_MAIN;
		switch(cmd){
		//One Menu Event
		case CMD.MAIN_CURRENT_PRICE:
			sendMessage(userId, messageId, messageCurrentPrice(userId), mainKeyboard);
			break;
		case CMD.MAIN_KOREA_PREMIUM:
			sendMessage(userId, messageId, messageKimp(userId), mainKeyboard);
			break;
		case CMD.MAIN_BTC:
			sendMessage(userId, messageId, messageBtc(userId), mainKeyboard);
			break;
		case CMD.MAIN_CALCULATE:
			sendMessage(userId, messageId, messageCalc(userId), mainKeyboard);
			break;
		case CMD.MAIN_INFO:
			sendMessage(userId, messageId, messageInfo(userId), mainKeyboard);
			break;
		case CMD.MAIN_COIN_LIST:
			sendMessage(userId, messageId, exp.explainCoinList(), mainKeyboard);
			break;
		case CMD.MAIN_HELP:
			sendMessage(userId, messageId, exp.explainHelp(), mainKeyboard);
			break;
		case CMD.MAIN_SUPPORT:
			sendMessage(userId, messageId, exp.explainSupport(), null);
			sendMessage(userId, null, exp.explainSupportWallet(), null);
			sendMessage(userId, null, exp.explainSupportAN(), mainKeyboard);
			break;
	
			
		//One more Menu
		case CMD.MAIN_SET_DAYLOOP:
			sendMessage(userId, messageId, exp.explainSetDayloop(), setDayloopKeyboard);
			state = ID.STATE_SET_DAYLOOP;
			break;
		case CMD.MAIN_SET_TIMELOOP:
			sendMessage(userId, messageId, exp.explainSetTimeloop(), setTimeloopKeyboard);
			state = ID.STATE_SET_TIMELOOP;
			break;
		case CMD.MAIN_SET_EXCHANGE:
			sendMessage(userId, messageId, exp.explainSetExchange(), setExchangeKeyboard);
			state = ID.STATE_SET_EXCHANGE;
			break;
		case CMD.MAIN_SET_TARGET:
			sendMessage(userId, messageId, exp.explainSetTarget(), defaultKeyboard);
			state = ID.STATE_SET_TARGET;
			break;
		case CMD.MAIN_SET_PRICE:
			sendMessage(userId, messageId, exp.explainSetPrice(), defaultKeyboard);
			state = ID.STATE_SET_PRICE;
			break;
		case CMD.MAIN_SET_NUMBER:
			sendMessage(userId, messageId, exp.explainSetNumber(), defaultKeyboard);
			state = ID.STATE_SET_NUMBER;
			break;
		case CMD.MAIN_SEND_MSG:
			sendMessage(userId, messageId, exp.explainSendMsg(), defaultKeyboard);
			state = ID.STATE_SEND_MSG;
			break;
		case CMD.MAIN_STOP:
			sendMessage(userId, messageId, exp.explainStop(), confirmStopKeyboard);
			state = ID.STATE_CONFIRM_STOP;
			break;
		case CMD.MAIN_HAPPY_LINE:
			state = checkHappyLine(userId, messageId);
			break;
		default :
			sendMessage(userId, messageId, messageCurrentPrice(userId), mainKeyboard);	
		}
		
		clientService.updateState(userId.toString(), state);
	}


	private void handleSetDayloop(Integer userId, Integer messageId, String cmd) {
		int dayloop = -1;
		String msg = "";
		switch(cmd) {
		case CMD.SET_DAYLOOP_01 : dayloop = 1; break;
		case CMD.SET_DAYLOOP_02 : dayloop = 2; break;
		case CMD.SET_DAYLOOP_03 : dayloop = 3; break;
		case CMD.SET_DAYLOOP_04 : dayloop = 4; break;
		case CMD.SET_DAYLOOP_05 : dayloop = 5; break;
		case CMD.SET_DAYLOOP_06 : dayloop = 6; break;
		case CMD.SET_DAYLOOP_07 : dayloop = 7; break;
		case CMD.SET_DAYLOOP_OFF : dayloop = 0; break;
		}
		
		if(dayloop == -1) {
			msg = "일일 알림 주기가 설정 되지 않았습니다.\n";
		} else {
			if(clientService.updateDayLoop(userId.toString(), dayloop)) {
				if(dayloop == 0) {
					msg = "일일 알림이 전송되지 않습니다.\n";
				} else {
					msg = "일일 알림이 매 " +  dayloop + " 일주기로 전송됩니다.\n";
				}
			} else {
				msg = "일일 알림 주기가 설정 되지 않았습니다.\n";
			}
		}
		
		msg += MSG.TO_MAIN;
		
		sendMessage(userId, messageId, msg, mainKeyboard);
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	private void handleSetTimeloop(Integer userId, Integer messageId, String cmd) {
		int timeloop = -1;
		String msg = "";
		
		switch(cmd) {
		case CMD.SET_TIMELOOP_01 : timeloop = 1; break;
		case CMD.SET_TIMELOOP_02 : timeloop = 2; break;
		case CMD.SET_TIMELOOP_03 : timeloop = 3; break;
		case CMD.SET_TIMELOOP_04 : timeloop = 4; break;
		case CMD.SET_TIMELOOP_05 : timeloop = 5; break;
		case CMD.SET_TIMELOOP_06 : timeloop = 6; break;
		case CMD.SET_TIMELOOP_07 : timeloop = 7; break;
		case CMD.SET_TIMELOOP_08 : timeloop = 8; break;
		case CMD.SET_TIMELOOP_09 : timeloop = 9; break;
		case CMD.SET_TIMELOOP_10 : timeloop = 10; break;
		case CMD.SET_TIMELOOP_11 : timeloop = 11; break;
		case CMD.SET_TIMELOOP_12 : timeloop = 12; break;
		case CMD.SET_TIMELOOP_OFF : timeloop = 0; break;
		}
		
		if(timeloop == -1) {
			msg = "시간 알림 주기가 설정 되지 않았습니다.\n";
		} else {
			if(clientService.updateTimeLoop(userId.toString(), timeloop)) {
				 if(timeloop == 0) {
					msg = "시간 알림이 전송되지 않습니다.\n";
				 } else {
					msg = "시간 알림이 " +  timeloop + " 시간 주기로 전송됩니다.\n";
				 }
			} else {
				msg = "시간 알림 주기가 설정 되지 않았습니다.\n";
			}
		}
		
		msg += MSG.TO_MAIN;
		
		sendMessage(userId, messageId, msg, mainKeyboard);
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	private void handleSetExchange(Integer userId, Integer messageId, String cmd) {
		String exchange = null;
		String msg = "거래소가 설정되지 않았습니다.\n";
		
		if(SET.ENABLED_COINONE && cmd.equals(CMD.SET_EXCHANGE_COINONE)) {
			exchange = ID.EXCHANGE_COINONE;
			msg = "거래소가 코인원으로 설정 되었습니다.\n";
		}
		
		if(SET.ENABLED_BITHUMB && cmd.equals(CMD.SET_EXCHANGE_BITHUMB)) {
			exchange = ID.EXCHANGE_BITHUMB;
			msg = "거래소가 빗썸으로 설정 되었습니다.\n";
		}
		
		if(SET.ENABLED_UPBIT && cmd.equals(CMD.SET_EXCHANGE_UPBIT)) {
			exchange = ID.EXCHANGE_UPBIT;
			msg = "거래소가 업비트로 설정 되었습니다.\n";
		}
		
		if(SET.ENABLED_COINNEST && cmd.equals(CMD.SET_EXCHANGE_COINNEST)) {
			exchange = ID.EXCHANGE_COINNEST;
			msg = "거래소가 코인네스트로 설정 되었습니다.\n";
		}
		
		if(SET.ENABLED_KORBIT && cmd.equals(CMD.SET_EXCHANGE_KORTBIT)) {
			exchange = ID.EXCHANGE_KORBIT;
			msg = "거래소가 코빗으로 설정 되었습니다.\n";
		}
		
		if(exchange != null) {
			if(clientService.updateExchange(userId.toString(), exchange)) {
				//Success Update
			} else {
				msg = "거래소가 설정되지 않았습니다.\n";
			}
		} else {
			msg = "거래소가 설정되지 않았습니다.\n";
		}
		
		msg += MSG.TO_MAIN;
		
		sendMessage(userId, messageId, msg, mainKeyboard);
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	private void handleSetTarget(Integer userId, Integer messageId, String cmd) {
		ClientVo client = clientService.get(userId);
		String exchange = client.getExchange();
		String msg = "";
		boolean valid = false;
		
		double currentValue = -1;
		try {
			JSONObject coinObj = coinManager.getCoin(SET.MY_COIN, exchange);
			currentValue = coinObj.getDouble("last");
			
			if(SET.ISIN_BTCMARKET) {
				currentValue = this.getKRW(coinObj, exchange).getDouble("last");
			}
			
		}
		catch (ServerErrorException e) {
			Log.i(e.log());
			e.printStackTrace();
			msg = MSG.WAIT_SECONDS + e.getTelegramMsg();
		}
		
		if(currentValue != -1) {
			String priceStr = cmd.trim();
			double targetPrice = -1;

			if(priceStr.matches("^\\d*(\\.?\\d*)$")) {
				targetPrice = Double.valueOf(priceStr);
				
				if (targetPrice == 0) {  // case4. 초기화
					if(clientService.clearTargetPrice(userId.toString())) {
						msg = "목표가격이 초기화 되었습니다.\n";
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
					msg = "목표가격 백분율을 -100% 이하로 설정 할 수 없습니다.\n";
				}
				
			} else {
				msg = "목표가격을 숫자 또는 백분율로 입력해주세요.\n";
			}
			
			if(valid) {
				msg += "목표가격 " + toKRWStr(targetPrice) + "원으로 설정되었습니다.\n";
				msg += "------------------------\n";
				msg += "목표가격 : " + toKRWStr(targetPrice) + " 원\n";
				msg += "현재가격 : " + toKRWStr(currentValue) + " 원\n";
				msg += "가격차이 : " + toSignKRWStr(targetPrice - currentValue) + " 원 (" + toSignPercent(targetPrice, currentValue) + " )\n";
				if(targetPrice >= currentValue) {
					if(!clientService.updateTargetUpPrice(userId.toString(), targetPrice)){
						msg = MSG.NEED_TO_START;
					}
				} else {
					if(!clientService.updateTargetDownPrice(userId.toString(), targetPrice)){
						msg = MSG.NEED_TO_START;
					}
				}
			}
			
		}
		
		msg += MSG.TO_MAIN;
		sendMessage(userId, messageId, msg, mainKeyboard);
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	private void handleSetPrice(Integer userId, Integer messageId, String cmd) {
		String priceStr = cmd;
		String msg = "";
		int price = -1;

		try { // case1. 평균단가에 문자가 포함될때
			price = Integer.parseInt(priceStr);
		} catch (NumberFormatException e) {
			msg = "투자금액은 숫자로만 입력해주세요.\n";
		}

		if(price != -1) {
			if(clientService.updatePrice(userId.toString(), price)){
				if (price == 0) { msg = "투자금액이 초기화 되었습니다.\n";} // case2. 초기화
				else {msg = "투자금액이 " + toKRWStr(price) + " 원으로 설정되었습니다.\n";} // case3.설정완료
			} else{
				msg = MSG.NEED_TO_START;
			}
		}
		
		msg += MSG.TO_MAIN;
		
		sendMessage(userId, messageId, msg, mainKeyboard);
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	private void handleSetNumber(Integer userId, Integer messageId, String cmd) {
		String numberStr = cmd;
		String msg = "";
		double number = -1;

		try { // case1. 코인개수에 문자가 포함될때
			number = Double.parseDouble(numberStr);
		} catch (NumberFormatException e) {
			msg = "코인개수는 숫자로만 입력해주세요.\n";
		}

		if(number != -1) {
			if(clientService.updateNumber(userId.toString(), number)){
				if (number == 0) { msg = "코인개수가 초기화 되었습니다.\n";} // case2. 초기화
				else {msg = "코인개수가 " + toCoinCntStr(number) + " 개로 설정되었습니다.\n";} // case3.설정완료
			} else{
				msg = MSG.NEED_TO_START;
			}
		}
		
		msg += MSG.TO_MAIN;
		
		sendMessage(userId, messageId, msg, mainKeyboard);
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	private void handleSendMsg(Integer userId, String username, Integer messageId, String message) {
		switch(message) {
		case CMD.SEND_MSG_OUT :
			sendMessage(userId, messageId, MSG.TO_MAIN, mainKeyboard);
			break;
		case CMD.OUT :
			sendMessage(userId, messageId, MSG.TO_MAIN, mainKeyboard);
			break;
		default:
			clientSuggestService.insert(userId, username, message);
			sendMessage(userId, messageId, "의견 감사드립니다.\n성투하세요!", mainKeyboard);
			
			//To Developer
			String msg = "";
			msg += "메세지가 도착했습니다!\n------------------\n\n";
			msg += message;
			msg += "\n\n------------------\n";
			msg += " By ";
			msg += username + " [" + userId + " ]";
			
			sendMessage(this.creatorId(), null, msg, mainKeyboard);
			break;
		}
		
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	
	private void handleConfirmStop(Integer userId, String username, Integer messageId, String cmd) {
		
		String msg = "";
		switch(cmd) {
		case CMD.CONFIRM_STOP_YES :
			if (clientService.stopChat(userId)) {
				msg = SET.MY_COIN + " 모든알림(시간알림, 일일알림, 목표가격알림)이 중지되었습니다.\n";
			} else {
				msg = MSG.NEED_TO_START;
			}
			break;
		case CMD.CONFIRM_STOP_NO:
		default:
			msg = "\n";
			break;
		}
		
		msg += MSG.TO_MAIN;
		
		sendMessage(userId, messageId, msg, mainKeyboard);
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
	}
	

	private String checkHappyLine(Integer userId, Integer messageId) {
		String state = ID.STATE_MAIN;
		String msg = "";
		ClientVo client = clientService.get(userId);
		if(client != null){
			if( client.getPrice() == null){ 
				msg = "먼저 투자금액을 설정해주세요.\n메뉴에서 '" + CMD.MAIN_SET_PRICE  + "'을 클릭해주세요.";
				sendMessage(userId, messageId, msg, mainKeyboard);
			} else if( client.getCoinCount() == null){ 
				msg = "먼저 코인개수를 설정해주세요.\n메뉴에서 '" + CMD.MAIN_SET_NUMBER  + "'을 클릭해주세요.";
				sendMessage(userId, messageId, msg, mainKeyboard);
			} else {
				msg = exp.explainHappyLine();
				sendMessage(userId, messageId, msg, defaultKeyboard);
				state = ID.STATE_HAPPY_LINE;
			}
		} 
		
		return state;
	}
	
	private void handleHappyLine(Integer userId, String username, Integer messageId, String cmd) {
		String msg = "";
		String priceStr = cmd;
		double happyPrice = -1;

		try { // case1. 평균단가에 문자가 포함될때
			happyPrice = Double.parseDouble(priceStr);
		} catch (NumberFormatException e) {
			msg = "코인가격은 숫자로만 입력해주세요.\n";
		}

		if(happyPrice != -1) {
			ClientVo client = clientService.get(userId);
			int price = client.getPrice();
			double coinCnt = client.getCoinCount();
			double avgPrice = (double) price / coinCnt;
			
			msg = "";
			msg += "평균단가 : " + toKRWStr(avgPrice) + " 원\n";;
			msg += "희망가격 : " + toKRWStr(happyPrice) + " 원\n";; 
			msg += "코인개수 : " + toCoinCntStr(coinCnt) + " 개\n";
			msg += "---------------------\n";
			msg += "투자금액 : " + toKRWStr(price) + " 원\n"; 
			msg += "희망금액 : " + toKRWStr((long)(happyPrice * coinCnt)) + " 원\n";
			msg += "손익금액 : " + toSignKRWStr((long)((happyPrice * coinCnt) - (price))) + " 원 (" + toSignPercent((int)(happyPrice * coinCnt), price) + ")\n";
			msg += "\n";
		}
		
		msg += MSG.TO_MAIN;
		
		sendMessage(userId, messageId, msg, mainKeyboard);
		clientService.updateState(userId.toString(), ID.STATE_MAIN);
		
	}
	
	private String messageCurrentPrice(Integer userId) {
		String date = TimeStamper.getDateTime();
		
		JSONObject coinObj = null;
		double currentValue = 0;
		String exchange = clientService.getExchange(userId);
		
		try {
			coinObj = coinManager.getCoin(SET.MY_COIN, exchange);
		} catch (ServerErrorException e) {
			Log.i(e.log());
			e.printStackTrace();
			return MSG.WAIT_SECONDS + e.getTelegramMsg();
		}
		
		if(coinObj == null) {
			Log.i("가격정보를 보낼 수 없습니다. : return NULL");
			return MSG.WAIT_SECONDS + "Coin NULL";
		}
		
		currentValue = coinObj.getDouble("last");
		String msg = "";
		msg += "현재시각 : " + date + "\n";
			
		if(SET.ISIN_BTCMARKET) {
			JSONObject coinKRWObj = this.getKRW(coinObj, exchange);
			double currentKRW = coinKRWObj.getDouble("last");
			double currentBTC = currentValue;
			msg += "현재가격 : " + toKRWStr(currentKRW) + " 원 [" + toBTCStr(currentBTC) + " BTC]\n";
		} else {
			msg += "현재가격 : " + toKRWStr(currentValue) + " 원\n";
		}
		
		return msg;
	}

	public String messageInfo(Integer userId) {
		ClientVo client = clientService.get(userId);
		if(client == null){
			return MSG.NEED_TO_START;
		}
		
		String msg = "";
		msg += "현재 설정은 다음과 같습니다.\n";
		msg += "-----------------\n";
		
		if(client.getExchange().equals(ID.EXCHANGE_COINONE)){ msg += "거래소     = 코인원\n";} 
		else if(client.getExchange().equals(ID.EXCHANGE_BITHUMB)){ msg += "거래소     = 빗썸\n";}
		else if(client.getExchange().equals(ID.EXCHANGE_UPBIT)){ msg += "거래소     = 업비트\n";}
		else if(client.getExchange().equals(ID.EXCHANGE_COINNEST)){ msg += "거래소     = 코인네스트\n";}
		else if(client.getExchange().equals(ID.EXCHANGE_KORBIT)){ msg += "거래소     = 코빗\n";}
		
		if(client.getDayLoop() != 0){ msg += "일일알림 = 매 " + client.getDayLoop() + " 일 주기 알림\n";} 
		else{ msg += "일일알림 = 알람 없음\n";}
		
		if(client.getTimeLoop() != 0){ msg += "시간알림 = 매 " + client.getTimeLoop() + " 시간 주기 알림\n";} 
		else{ msg += "시간알림 = 알람 없음\n";}
		
		if(client.getTargetUpPrice() != null){msg += "목표가격 = " + toKRWStr(client.getTargetUpPrice()) + " 원 \n";}
		else if(client.getTargetDownPrice() != null){msg += "목표가격 = " + toKRWStr(client.getTargetDownPrice()) + " 원 \n";}
		else { msg += "목표가격 = 입력되어있지 않음.\n";}
		
		if(client.getPrice() != null){msg += "투자금액 = " + toKRWStr(client.getPrice()) + " 원 \n";}
		else { msg += "투자금액 = 입력되어있지 않음.\n";}
		
		if(client.getCoinCount() != null){msg += "코인개수 = " + toCoinCntStr(client.getCoinCount()) + " 개 \n"; }
		else { msg += "코인개수 = 입력되어있지 않음.\n";}
		
		return msg;
	}
	
	public String messageKimp(Integer userId) {
		String date = TimeStamper.getDateTime();

		JSONObject coinObj = null;
		try {
			String exchange = clientService.getExchange(userId);
			coinObj = coinManager.getCoinWithKimp(SET.MY_COIN, exchange);
			if(SET.ISIN_BTCMARKET) {
				coinObj.put("last", this.getKRW(coinObj, exchange).getDouble("last"));
			}
		} catch (ServerErrorException e) {
			Log.i(e.log());
			e.printStackTrace();
			return MSG.WAIT_SECONDS + e.getTelegramMsg();
		} 
		
		if(coinObj != null){
			double krw = coinObj.getDouble("last");
			double usd = coinObj.getDouble("usd2krw");
			double kimp = ((krw - usd) / usd) * 100;
			
			String msg = "";
			msg += "현재시각 : " + date + "\n";
			msg += "달러환율 : $ 1 = " + toKRWStr(coinObj.getInt("rate")) +" 원 \n";
			msg += "--------------------\n";
			msg += "현재가격 : " + toKRWStr(krw) + " 원\n";
			msg += "미국가격 : " + toKRWStr(usd) + " 원 ($ " + toUSDStr(coinObj.getDouble("usd")) + ")\n";
			msg += "프리미엄 : " + toSignKimpStr(kimp) + "% \n";
			
			return msg;
		} else {
			return MSG.WAIT_SECONDS + "Coin NULL";
		}
	}
	
	public String messageBtc(Integer userId) {
		String date = TimeStamper.getDateTime();
		JSONObject coin = null;
		JSONObject btc = null;
		String msg = "";
		
		String exchange = clientService.getExchange(userId);
		if(exchange.equals(ID.EXCHANGE_COINNEST) || exchange.equals(ID.EXCHANGE_KORBIT)) {
			if(exchange.equals(ID.EXCHANGE_COINNEST)) { msg += "코인네스트 API는 해당 정보를 제공하지 않습니다.\n"; }
			if(exchange.equals(ID.EXCHANGE_KORBIT)) { msg += "코빗  API는 해당 정보를 제공하지 않습니다.\n"; }
			
			if(SET.ENABLED_COINONE) {
				msg += "코인원 기준 정보로 대체합니다.\n";
				exchange = ID.EXCHANGE_COINONE;
			} else if(SET.ENABLED_BITHUMB) {
				msg += "빗썸 기준 정보로 대체합니다.\n";
				exchange = ID.EXCHANGE_BITHUMB;
			} else if(SET.ENABLED_UPBIT) {
				msg += "업비트 기준 정보로 대체합니다.\n";
				exchange = ID.EXCHANGE_UPBIT;
			} else {
				msg += MSG.TO_MAIN;
				return msg;
			}
			
			msg += "\n";
		}
		
		try {
			coin = coinManager.getCoin(SET.MY_COIN, exchange);
			btc = coinManager.getCoin(ID.COIN_BTC, exchange);
		} catch (ServerErrorException e) {
			Log.i(e.log());
			e.printStackTrace();			
			return MSG.WAIT_SECONDS + e.getTelegramMsg();
		}
	
		if(coin != null && btc != null){
			double coinCV = coin.getDouble("last");
			double coinBV = coin.getDouble("first");
			double btcCV = btc.getDouble("last");
			double btcBV = btc.getDouble("first");
			
			msg += "현재시각 : " + date + "\n";
			msg += "-------------------------\n";
			
			msg += "BTC 현재 시각 가격 : " + toKRWStr(btcCV) +" 원 \n";
			msg += "BTC 24시간전 가격 : " + toKRWStr(btcBV) +" 원 \n";
			msg += "\n";
			
			if(SET.ISIN_BTCMARKET) {
				JSONObject coinKRWObj = null;
				coinKRWObj = this.getKRW(coin, exchange);
				msg += SET.MY_COIN + " 현재 시각 가격 : " + toKRWStr(coinKRWObj.getDouble("last")) + " 원 [" + toBTCStr(coinCV) + " BTC]\n";
				msg += SET.MY_COIN + " 24시간전 가격 : " + toKRWStr(coinKRWObj.getDouble("first")) + " 원 [" + toBTCStr(coinBV) + " BTC]\n";
			} else {
				msg += SET.MY_COIN + " 현재 시각 가격 : " + toKRWStr(coinCV) + " 원\n";
				msg += SET.MY_COIN + " 24시간전 가격 : " + toKRWStr(coinBV) + " 원\n";
			}
			msg += "\n";
			
			msg += "BTC 24시간 변화량 : " + toSignPercent(btcCV, btcBV) + "\n";
			msg += SET.MY_COIN + " 24시간 변화량 : " + toSignPercent(coinCV, coinBV) + "\n";
			
			return msg;
		} else {
			return MSG.WAIT_SECONDS + "Coin NULL";
		}
		
	}
	
	public String messageCalc(Integer userId) {
		ClientVo client = clientService.get(userId);
		if(client != null){
			if( client.getPrice() == null){ return "먼저 투자금액을 설정해주세요.\n메뉴에서 '" + CMD.MAIN_SET_PRICE  + "'을 클릭해주세요.";}
			else if( client.getCoinCount() == null){ return "먼저 코인개수를 설정해주세요.\n메뉴에서 '" + CMD.MAIN_SET_NUMBER  + "'을 클릭해주세요.";}
			else {
				try {
					JSONObject coin = coinManager.getCoin(SET.MY_COIN, client.getExchange());
					return calcResult(client, coin.getDouble("last"));
				} catch (ServerErrorException e) {
					Log.i(e.log());
					e.printStackTrace();
					return MSG.WAIT_SECONDS + e.getTelegramMsg();
				}
			}
		} else{
			 return MSG.NEED_TO_START;
		}
		
	}
	
	public String calcResult(ClientVo client, Double coinValue) {
		double cnt = client.getCoinCount();
		int price = client.getPrice();
		double avgPrice = (double)((double)price / cnt);
		double coinKRW = -1;
		String msg = "";
		
		if(SET.ISIN_BTCMARKET) {
			JSONObject btcObj = null;
			double btcKRW = -1;
			double avgBTC = -1;
			double coinBTC = coinValue;
			
			try {
				btcObj = coinManager.getCoin(ID.COIN_BTC, client.getExchange());
			} catch (ServerErrorException e) {
				Log.i(e.log());
				e.printStackTrace();
				return MSG.WAIT_SECONDS + e.getTelegramMsg();
			}
			
			btcKRW = btcObj.getDouble("last");
			avgBTC = avgPrice / btcKRW;
			coinKRW = coinValue * btcKRW;
			
			msg += "평균단가 : " + toKRWStr(avgPrice) + " 원  [ " + toBTCStr(avgBTC) + " BTC]\n";
			msg += "현재가격 : " + toKRWStr(coinKRW) + " 원  [ " + toBTCStr(coinBTC) + " BTC]\n";
//			msg += "평균단가 : " + toBTCStr(avgBTC) + " BTC [ " + toKRWStr(avgPrice) + " 원  ]\n";
//			msg += "현재가격 : " + toBTCStr(coinBTC) + " BTC [ " + toKRWStr(coinKRW) + " 원  ]\n";
		} else {
			coinKRW = coinValue;
			msg += "평균단가 : " + toKRWStr(avgPrice) + " 원\n";
			msg += "현재가격 : " + toKRWStr(coinKRW)+ " 원\n";
		}
		
		
		msg += "코인개수 : " + toCoinCntStr(cnt) + " 개\n";
		msg += "---------------------\n";
		msg += "투자금액 : " + toKRWStr(price) + "원\n"; 
		msg += "현재금액 : " + toKRWStr((int)(coinKRW * cnt)) + "원\n";
		msg += "손익금액 : " + toSignKRWStr((int)((coinKRW * cnt) - (cnt * avgPrice))) + "원 (" + toSignPercent((int)(coinKRW * cnt), (int)(cnt * avgPrice)) + ")\n";
		msg += "\n";
		return msg;
	}
	
	/* Send Message */
	
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
	
	public void sendTargetPriceMessage(List<ClientVo> clients, String exchange, JSONObject coinObj) {
		String date = TimeStamper.getDateTime();
		double currentValue = coinObj.getDouble("last");
		if(SET.ISIN_BTCMARKET) {
			currentValue = this.getKRW(coinObj, exchange).getDouble("last");
		}

		String msg = "";		
		msg += "목표가격에 도달하였습니다!\n";
		msg += "현재시각 : " + date + "\n";
		
		ClientVo client = null;
		int clientLength = clients.size();
		for(int i = 0; i < clientLength; i++){
			client = clients.get(i);
			if(client.getTargetUpPrice() != null) { msg += "목표가격 : " + toKRWStr(client.getTargetUpPrice()) + " 원\n"; }
			if(client.getTargetDownPrice() != null) { msg += "목표가격 : " + toKRWStr(client.getTargetDownPrice()) + " 원\n"; }
			msg += "현재가격 : " + toKRWStr(currentValue) + " 원\n";

			sendMessage(client.getUserId(), null, msg, null);
			if(clientService.clearTargetPrice(client.getUserId())) {
				sendMessage(client.getUserId(), null, "목표가격이 초기화되었습니다.", null);
			}
		}
	}
	
	
	public void sendTimelyMessage(List<ClientVo> clients, String exchange, TimelyInfoVo coinCurrent, TimelyInfoVo coinBefore, int timeLoop){
		int clientLength = clients.size();
		ClientVo client = null;
		
		double currentValue = coinCurrent.getLast();
		double beforeValue = coinBefore.getLast();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = format.format(new Date());
		
		String msg = "";
		msg += "현재시각: " + date + "\n";
		
		if(!coinCurrent.getResult().equals("success")){
			String currentErrorMsg = coinCurrent.getErrorMsg();
			String currentErrorCode = coinCurrent.getErrorCode();
			msg += "에러발생: " + currentErrorMsg +"\n";
			msg += "에러코드: " + currentErrorCode +"\n";
			
			if(SET.ISIN_BTCMARKET) {
				JSONObject coinBeforeKRWObj = this.getKRW(coinBefore, exchange);
				double beforeBTC = beforeValue;
				double beforeKRW = coinBeforeKRWObj.getDouble("last");
				
				msg += timeLoop + " 시간 전: " + toKRWStr(beforeKRW) + " 원 [" + toBTCStr(beforeBTC) + " BTC]\n";
			} else {
				msg += timeLoop + " 시간 전: " + toKRWStr(beforeValue) + " 원\n";
			}
		} else{
			if(SET.ISIN_BTCMARKET) {
				JSONObject coinCurrentKRWObj = this.getKRW(coinCurrent, exchange);
				JSONObject coinBeforeKRWObj = this.getKRW(coinBefore, exchange);
				
				double currentBTC = currentValue;
				double beforeBTC = beforeValue;
				double currentKRW = coinCurrentKRWObj.getDouble("last");
				double beforeKRW = coinBeforeKRWObj.getDouble("last");

				msg += "현재가격: " + toKRWStr(currentKRW) + " 원 [" + toBTCStr(currentBTC)+ " BTC]\n";
				msg += timeLoop + " 시간 전: " + toKRWStr(beforeKRW) + " 원 [" + toBTCStr(beforeBTC) + " BTC]\n";
				msg += "가격차이: " + toSignKRWStr(currentKRW - beforeKRW) + " 원 (" + toSignPercent(currentKRW, beforeKRW) + ")\n";
			} else {
				msg += "현재가격: " + toKRWStr(currentValue) + " 원\n";
				msg += timeLoop + " 시간 전: " + toKRWStr(beforeValue) + " 원\n";
				msg += "가격차이: " + toSignKRWStr(currentValue - beforeValue) + " 원 (" + toSignPercent(currentValue, beforeValue) + ")\n";
			}
		}
		
		for (int i = 0; i < clientLength; i++) {
			client = clients.get(i);
			sendMessage(client.getUserId(), null, msg, null);
		}
	}
	
	public void sendDailyMessage(List<ClientVo> clients, String exchange, DailyInfoVo coinCurrent, DailyInfoVo coinBefore, int dayLoop){
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
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일");
		String date = format.format(new Date());

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
		
		String msg = "";
		msg += date + "\n";
		
		if(SET.ISIN_BTCMARKET) {
			currentHighBTC	= currentHigh;
			beforeHighBTC	= beforeHigh;
			currentLowBTC	= currentLow;
			beforeLowBTC	= beforeLow;
			currentLastBTC 	= currentLast;
			beforeLastBTC	= beforeLast;
			
			JSONObject coinCurrentKRWObj = this.getKRW(coinCurrent, exchange);
			currentLast = coinCurrentKRWObj.getDouble("last");
			currentHigh = coinCurrentKRWObj.getDouble("high");
			currentLow = coinCurrentKRWObj.getDouble("low");
			
			JSONObject coinBeforeKRWObj = this.getKRW(coinBefore, exchange);
			beforeLast = coinBeforeKRWObj.getDouble("last");
			beforeHigh = coinBeforeKRWObj.getDouble("high");
			beforeLow = coinBeforeKRWObj.getDouble("low");
			
			msg += "---------------------\n";
			msg += "금일의 거래량 : " + toVolumeStr(currentVolume) + " \n";
			msg += dayLoopStr + "전 거래량 : " + toVolumeStr(beforeVolume) + " \n";
			msg += "거래량의 차이 : " + toSignVolumeStr(currentVolume - beforeVolume) + " (" + toSignPercent(currentVolume, beforeVolume) + ")\n";
			msg += "\n";
			
			msg += "금일의 상한가 : " + toKRWStr(currentHigh) + " 원 ["+ toBTCStr(currentHighBTC) + " BTC]\n";
			msg += dayLoopStr + "전 상한가 : " + toKRWStr(beforeHigh) + " 원 ["+ toBTCStr(beforeHighBTC) + " BTC]\n";
			msg += "상한가의 차이 : " + toSignKRWStr(currentHigh - beforeHigh) + "원 (" + toSignPercent(currentHigh, beforeHigh) + ")\n";
			msg += "\n";
			
			msg += "금일의 하한가 : " + toKRWStr(currentLow) + " 원 ["+ toBTCStr(currentLowBTC) + " BTC]\n";
			msg += dayLoopStr + "전 하한가 : " + toKRWStr(beforeLow) + " 원 ["+ toBTCStr(beforeLowBTC) + " BTC]\n";
			msg += "하한가의 차이 : " + toSignKRWStr(currentLow - beforeLow) + " 원 (" + toSignPercent(currentLow, beforeLow) + ")\n";
			msg += "\n";
			
			
			msg += "금일의 종가 : " + toKRWStr(currentLast) + " 원 ["+ toBTCStr(currentLastBTC) + " BTC]\n";
			msg += dayLoopStr + "전 종가 : " + toKRWStr(beforeLast) + " 원 ["+ toBTCStr(beforeLastBTC) + " BTC]\n";
			msg += "종가의 차이 : " + toSignKRWStr(currentLast - beforeLast) + " 원 (" + toSignPercent(currentLast, beforeLast) + ")\n";
			msg += "\n";
		} else {
			msg += "---------------------\n";
			msg += "금일의 거래량 : " + toVolumeStr(currentVolume) + " \n";
			msg += dayLoopStr + "전 거래량 : " + toVolumeStr(beforeVolume) + " \n";
			msg += "거래량의 차이 : " + toSignVolumeStr(currentVolume - beforeVolume) + " (" + toSignPercent(currentVolume, beforeVolume) + ")\n";
			msg += "\n";
			
			msg += "금일의 상한가 : " + toKRWStr(currentHigh) + " 원\n";
			msg += dayLoopStr + "전 상한가 : " + toKRWStr(beforeHigh) + " 원\n";
			msg += "상한가의 차이 : " + toSignKRWStr(currentHigh - beforeHigh) + "원 (" + toSignPercent(currentHigh, beforeHigh) + ")\n";
			msg += "\n";
			
			msg += "금일의 하한가 : " + toKRWStr(currentLow) + " 원\n";
			msg += dayLoopStr + "전 하한가 : " + toKRWStr(beforeLow) + " 원\n";
			msg += "하한가의 차이 : " + toSignKRWStr(currentLow - beforeLow) + " 원 (" + toSignPercent(currentLow, beforeLow) + ")\n";
			msg += "\n";
			
			
			msg += "금일의 종가 : " + toKRWStr(currentLast) + " 원\n";
			msg += dayLoopStr + "전 종가 : " + toKRWStr(beforeLast) + " 원\n";
			msg += "종가의 차이 : " + toSignKRWStr(currentLast - beforeLast) + " 원 (" + toSignPercent(currentLast, beforeLast) + ")\n";
			msg += "\n";
		}
		
		ClientVo client = null;
		int clientLength = clients.size();
		for(int i = 0; i < clientLength; i++){
			client = clients.get(i);
			sendMessage(client.getUserId(), null, msg, null);
			
			if(client.getCoinCount() != null && client.getPrice() != null){
				sendMessage(client.getUserId(), null, calcResult(client, coinCurrent.getLast()), null);
			}
		}
	}
	
	private JSONObject getKRW(TimelyInfoVo timelyInfo, String exchange){
		JSONObject coinObj = new JSONObject();
		coinObj.put("last", timelyInfo.getLast());
		coinObj.put("first", 0);
		coinObj.put("high", timelyInfo.getHigh());
		coinObj.put("low", timelyInfo.getLow());
		return this.getKRW(coinObj, exchange);
	}
	
	private JSONObject getKRW(DailyInfoVo dailyInfo, String exchange){
		JSONObject coinObj = new JSONObject();
		coinObj.put("last", dailyInfo.getLast());
		coinObj.put("first", 0);
		coinObj.put("high", dailyInfo.getHigh());
		coinObj.put("low", dailyInfo.getLow());
		return this.getKRW(coinObj, exchange);
	}
	
	private JSONObject getKRW(JSONObject coinObj, String exchange){
		JSONObject btcObj;
		try {
			btcObj = coinManager.getCoin(ID.COIN_BTC, exchange);
		} catch (ServerErrorException e) {
			Log.i(e.log());
			e.printStackTrace();
			return null;
		}
		
		double last = coinObj.getDouble("last") * btcObj.getDouble("last");
		double first = coinObj.getDouble("first") * btcObj.getDouble("last");
		double high = coinObj.getDouble("high") * btcObj.getDouble("last");
		double low = coinObj.getDouble("low") * btcObj.getDouble("last");
		
		JSONObject coinKRW = new JSONObject();
		coinKRW.put("last", last);
		coinKRW.put("first", first);
		coinKRW.put("high", high);
		coinKRW.put("low", low);
		
		return coinKRW;
	}
	
	/*
	 * Formatting Str
	 */
	private String toBTCStr(double i) {
		DecimalFormat df = new DecimalFormat("#.#");
		df.setMinimumFractionDigits(8);
		df.setMaximumFractionDigits(8);
		return df.format(i);
	}
	
	private String toKRWStr(double i){
		DecimalFormat df = new DecimalFormat("#,###.#"); 
		return df.format(i);
	}
	
	private String toSignKRWStr(double i) {
		DecimalFormat df = new DecimalFormat("#,###.#");
		df.setPositivePrefix("+");
		df.setNegativePrefix("-");
		return df.format(i);
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
	
	private String toCoinCntStr(double i) {
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(i);
	}
	
	
	private String toUSDStr(double d){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(d);
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
}


