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
		
		double currentPrice = -1;
		try {
			currentPrice = coinManager.getCoin(SET.MY_COIN, exchange).getDouble("last");
		}
		catch (ServerErrorException e) {
			Log.i(e.log());
			e.printStackTrace();
			msg = MSG.WAIT_SECONDS + e.getTelegramMsg();
		}
		
		if(currentPrice != -1) {
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
					targetPrice =  currentPrice;
				} else if(percent > 0) {
					valid = true;
					targetPrice = currentPrice + currentPrice * percent;
				}  else if( percent < 0 && percent >= -100) {
					valid = true;
					double a = (currentPrice * percent) * -1;
					targetPrice = currentPrice - a;
				} else if( percent < -100) {
					msg = "목표가격 백분율을 -100% 이하로 설정 할 수 없습니다.\n";
				}
				
			} else {
				msg = "목표가격을 숫자 또는 백분율로 입력해주세요.\n";
			}
			
			if(valid) {
				msg += "목표가격 " + toCommaStr(targetPrice) + "원으로 설정되었습니다.\n";
				msg += "------------------------\n";
				msg += "목표가격 : " + toCommaStr(targetPrice) + " 원\n";
				msg += "현재가격 : " + toCommaStr(currentPrice) + " 원\n";
				msg += "가격차이 : " + toCommaStr(targetPrice - currentPrice) + " 원 (" + getPercent(targetPrice, currentPrice) + " )\n";
				if(targetPrice >= currentPrice) {
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
				else {msg = "투자금액이 " + price + " 원으로 설정되었습니다.\n";} // case3.설정완료
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
				else {msg = "코인개수가 " + number + " 개로 설정되었습니다.\n";} // case3.설정완료
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
	
	private String messageCurrentPrice(Integer userId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = format.format(new Date());

		JSONObject coin = null;
		double currentValue = 0;
		String exchange = clientService.getExchange(userId);
		try {
			coin = coinManager.getCoin(SET.MY_COIN, exchange);
		} catch (ServerErrorException e) {
			Log.i(e.log());
			e.printStackTrace();
			return MSG.WAIT_SECONDS + e.getTelegramMsg();
		}
		
		
		if(coin == null) {
			Log.i("가격정보를 보낼 수 없습니다. : return NULL");
			return MSG.WAIT_SECONDS + "Coin NULL";
		}
		
		currentValue = coin.getDouble("last");
		String msg = "";
		msg += "현재시각 : " + date + "\n";
		msg += "현재가격 : " + toCommaStr(currentValue) + " 원\n";
		
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
		
		if(client.getTargetUpPrice() != null){msg += "목표가격 = " + toCommaStr(client.getTargetUpPrice()) + " 원 \n";}
		else if(client.getTargetDownPrice() != null){msg += "목표가격 = " + toCommaStr(client.getTargetDownPrice()) + " 원 \n";}
		else { msg += "목표가격 = 입력되어있지 않음.\n";}
		
		if(client.getPrice() != null){msg += "투자금액 = " + toCommaStr(client.getPrice()) + " 원 \n";}
		else { msg += "투자금액 = 입력되어있지 않음.\n";}
		
		if(client.getCoinCount() != null){msg += "코인개수 = " + toStr(client.getCoinCount()) + " 개 \n"; }
		else { msg += "코인개수 = 입력되어있지 않음.\n";}
		
		return msg;
	}
	
	public String messageKimp(Integer userId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = format.format(new Date());

		JSONObject coin = null;
		double currentValue = 0;
		try {
			String exchange = clientService.getExchange(userId);
			coin = coinManager.getCoinWithKimp(SET.MY_COIN, exchange);
		} catch (ServerErrorException e) {
			e.printStackTrace();
			Log.i(e.log());
			e.printStackTrace();
			return MSG.WAIT_SECONDS + e.getTelegramMsg();
		} 
		
		if(coin != null){
			currentValue = coin.getDouble("last");
			
			String msg = "";
			msg += "현재시각 : " + date + "\n";
			msg += "달러환율 : $ 1 = " + toCommaStr(coin.getInt("rate")) +" 원 \n";
			msg += "--------------------\n";
			msg += "현재가격 : " + toCommaStr(currentValue) + " 원\n";
			msg += "미국가격 : " + toCommaStr(coin.getDouble("usd2krw")) + " 원 ($ " + toStr(coin.getDouble("usd")) + ")\n";
			msg += "프리미엄 : " + toSignStr(coin.getDouble("kimp")) + " %\n";
			
			return msg;
		} else {
			return MSG.WAIT_SECONDS + "Coin NULL";
		}
	}
	
	public String messageBtc(Integer userId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = format.format(new Date());
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
			msg += "BTC 가격 : " + toCommaStr(btcCV) +" 원 \n";
			msg += SET.MY_COIN + " 가격 : " + toCommaStr(coinCV) +" 원 \n";
			msg += "BTC 24시간 변화량 : " + getPercent(btcCV, btcBV) + "\n";
			msg += SET.MY_COIN + " 24시간 변화량 : " + getPercent(coinCV, coinBV) + "\n";
			
			return msg;
		} else {
			return MSG.WAIT_SECONDS + "Coin NULL";
		}
		
	}
	
	public String messageCalc(Integer userId) {
		ClientVo client = clientService.get(userId);
		if(client != null){
			if( client.getPrice() == null){ return "먼저 투자금액을 설정해주세요.\n메뉴에서 '" + CMD.MAIN_SET_PRICE  + "'을 클릭해주세요.";}
			if( client.getCoinCount() == null){ return "먼저 코인개수를 설정해주세요.\n메뉴에서 '" + CMD.MAIN_SET_NUMBER  + "'을 클릭해주세요.";}
			if( client.getPrice() != null && client.getCoinCount() != null){
				try {
					JSONObject coin = coinManager.getCoin(SET.MY_COIN,client.getExchange());
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
		
		return "temp";
	}
	

	public String calcResult(ClientVo client, double coinVal){
		double cnt = client.getCoinCount();
		int price = client.getPrice();
		double avgPrice = (double)((double)price / cnt);
		
		String msg = "";
		msg += "평균단가 : " + toCommaStr(avgPrice) + " 원\n";
		msg += "현재가격 : " + toCommaStr(coinVal)+ " 원\n";
		msg += "코인개수 : " + toStr(cnt) + " 개\n";
		msg += "---------------------\n";
		msg += "투자금액 : " + toCommaStr(price) + "원\n"; 
		msg += "현재금액 : " + toCommaStr((int)(coinVal * cnt)) + "원\n";
		msg += "손익금액 : " + toSignStr((int)((coinVal * cnt) - (cnt * avgPrice))) + "원 (" + getPercent((int)(coinVal * cnt), (int)(cnt * avgPrice)) + ")\n";
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
	
	public void sendTargetPriceMessage(List<ClientVo> clients, JSONObject coinObj) {
		double currentPrice = coinObj.getDouble("last");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = format.format(new Date());
		
		String msg = "";
		msg += "목표가격에 도달하였습니다!\n";
		msg += "현재시각 : " + date + "\n";

		
		ClientVo client = null;
		int clientLength = clients.size();
		for(int i = 0; i < clientLength; i++){
			client = clients.get(i);
			if(client.getTargetUpPrice() != null) { msg += "목표가격 : " + toCommaStr(client.getTargetUpPrice()) + " 원\n"; }
			if(client.getTargetDownPrice() != null) { msg += "목표가격 : " + toCommaStr(client.getTargetDownPrice()) + " 원\n"; }
			msg += "현재가격 : " + toCommaStr(currentPrice) + " 원\n";

			sendMessage(client.getUserId(), null, msg, null);
			if(clientService.clearTargetPrice(client.getUserId())) {
				sendMessage(client.getUserId(), null, "목표가격이 초기화되었습니다.", null);
			}
		}
	}
	
	
	public void sendTimelyMessage(List<ClientVo> clients, TimelyInfoVo coinCurrent, TimelyInfoVo coinBefore, int timeLoop){
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
			msg += timeLoop + " 시간 전: " + toCommaStr(beforeValue) + " 원\n";
		} else{
			msg += "현재가격: " + toCommaStr(currentValue) + " 원\n";
			msg += timeLoop + " 시간 전: " + toCommaStr(beforeValue) + " 원\n";
			msg += "가격차이: " + toSignStr(currentValue - beforeValue) + " 원 (" + getPercent(currentValue, beforeValue) + ")\n";
		}
		
		for (int i = 0; i < clientLength; i++) {
			client = clients.get(i);
			sendMessage(client.getUserId(), null, msg, null);
		}
	}
	
	public void sendDailyMessage(List<ClientVo> clients, DailyInfoVo coinCurrent, DailyInfoVo coinBefore, int dayLoop){
		long currentVolume = coinCurrent.getVolume();
		long beforeVolume  = coinBefore.getVolume();
		
		double currentHigh = coinCurrent.getHigh();
		double beforeHigh = coinBefore.getHigh();

		double currentLow = coinCurrent.getLow();
		double beforeLow = coinBefore.getLow();

		double currentLast = coinCurrent.getLast();
		double beforeLast = coinBefore.getLast();

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
		msg += "---------------------\n";
		msg += "금일의 거래량 : " + toCommaStr(currentVolume) + " \n";
		msg += dayLoopStr + "전 거래량 : " + toCommaStr(beforeVolume) + " \n";
		msg += "거래량의 차이 : " + toSignStr(currentVolume - beforeVolume) + " (" + getPercent(currentVolume, beforeVolume) + ")\n";
		msg += "\n";
		msg += "금일의 상한가 : " + toCommaStr(currentHigh) + " 원\n";
		msg += dayLoopStr + "전 상한가 : " + toCommaStr(beforeHigh) + " 원\n";
		msg += "상한가의 차이 : " + toSignStr(currentHigh - beforeHigh) + "원 (" + getPercent(currentHigh, beforeHigh) + ")\n";
		msg += "\n";
		msg += "금일의 하한가 : " + toCommaStr(currentLow) + " 원\n";
		msg += dayLoopStr + "전 하한가 : " + toCommaStr(beforeLow) + " 원\n";
		msg += "하한가의 차이 : " + toSignStr(currentLow - beforeLow) + " 원 (" + getPercent(currentLow, beforeLow) + ")\n";
		msg += "\n";
		msg += "금일의 종가 : " + toCommaStr(currentLast) + " 원\n";
		msg += dayLoopStr + "전 종가 : " + toCommaStr(beforeLast) + " 원\n";
		msg += "종가의 차이 : " + toSignStr(currentLast - beforeLast) + " 원 (" + getPercent(currentLast, beforeLast) + ")\n";
		msg += "\n";
		
		ClientVo client = null;
		int clientLength = clients.size();
		for(int i = 0; i < clientLength; i++){
			client = clients.get(i);
			sendMessage(client.getUserId(), null, msg, null);
			
			if(client.getCoinCount() != null && client.getPrice() != null){
				sendMessage(client.getUserId(), null, calcResult(client, currentLast), null);
			}
		}
	}
	
	/*
	 * Formatting Str
	 */
	private DecimalFormat df = new DecimalFormat("#,###.#");
	private String toCommaStr(double i){
			return df.format(i);
	}
	
	private String toCommaStr(long i){
		return String.format("%,d", i);
	}
	
	private String toStr(double d){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(d);
	}
	
	private String toSignStr(long i){
		String prefix = "";
		if(i > 0){ prefix = "+";}
		return prefix + toCommaStr(i);
	}
	
	public String toSignStr(double d){
		String prefix = "";
		if(d > 0){ prefix = "+";}
		return prefix + toStr(d);
	}
	
	public String getPercent(double c, double b){
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


