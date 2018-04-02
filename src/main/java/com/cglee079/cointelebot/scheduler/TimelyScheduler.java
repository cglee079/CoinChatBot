package com.cglee079.cointelebot.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.cointelebot.coin.CoinManager;
import com.cglee079.cointelebot.constants.SET;
import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.exception.ServerErrorException;
import com.cglee079.cointelebot.log.Log;
import com.cglee079.cointelebot.model.ClientVo;
import com.cglee079.cointelebot.model.TimelyInfoVo;
import com.cglee079.cointelebot.service.ClientService;
import com.cglee079.cointelebot.service.TimelyInfoService;
import com.cglee079.cointelebot.telegram.TelegramBot;

public class TimelyScheduler {

	@Autowired
	private TelegramBot telegramBot;

	@Autowired
	private ClientService clientService;

	@Autowired
	private TimelyInfoService timelyInfoService;
	
	@Autowired
	private CoinManager coinManager;
	
	@Scheduled(cron = "10 00 0/1 * * *")
	public void loadTimelyCoins(){
		Date dateCurrent = new Date();
		
		if(SET.ENABLED_COINONE) { loadTimelyCoin(dateCurrent, ID.EXCHANGE_COINONE); }
		if(SET.ENABLED_BITHUMB) { loadTimelyCoin(dateCurrent, ID.EXCHANGE_BITHUMB); }
		if(SET.ENABLED_UPBIT) { loadTimelyCoin(dateCurrent, ID.EXCHANGE_UPBIT); }
		if(SET.ENABLED_COINNEST) { loadTimelyCoin(dateCurrent, ID.EXCHANGE_COINNEST); }
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH");
		String hourStr = formatter.format(dateCurrent);
		Integer hour = Integer.valueOf(hourStr);
		for(int i = 1; i <= 12; i++) {
			if(hour % i == 0) {
				sendCoinInfos(dateCurrent, i);
			}
		}
	}
	
	public void loadTimelyCoin(Date dateCurrent, String exchange) {
		JSONObject coinObj = null;
		try {
			coinObj = coinManager.getCoin(SET.MY_COIN, exchange);
		} catch (ServerErrorException e) {
			Log.i("ERROR loadDailyCoin : " + e.getMessage());
			Log.i(e.getStackTrace());
			coinObj = timelyInfoService.getBefore(dateCurrent, exchange);
			coinObj.put("result", "error");
			coinObj.put("errorCode", e.getErrCode());
			coinObj.put("errorMsg", e.getMessage());
		} finally {
			timelyInfoService.insert(dateCurrent, exchange, coinObj);
		}
	}
	
	public void sendCoinInfos(Date dateCurrent, int timeLoop) {
		if(SET.ENABLED_COINONE){sendCoinInfo(dateCurrent, ID.EXCHANGE_COINONE, timeLoop);}
		if(SET.ENABLED_BITHUMB){sendCoinInfo(dateCurrent, ID.EXCHANGE_BITHUMB, timeLoop);}
		if(SET.ENABLED_UPBIT){sendCoinInfo(dateCurrent, ID.EXCHANGE_UPBIT, timeLoop);}
		if(SET.ENABLED_COINNEST){sendCoinInfo(dateCurrent, ID.EXCHANGE_COINNEST, timeLoop);}
	}
	
	public void sendCoinInfo(Date dateCurrent, String exchange, int timeLoop){
		List<ClientVo> clients = clientService.list(exchange, timeLoop, null, null);
		if(clients.size() > 0) {
			Date dateBefore = null;
			dateBefore = new Date();
			long time = dateCurrent.getTime();
			time = time -(60 * 60 * 1000 * timeLoop);
			dateBefore.setTime(time);
			
			TimelyInfoVo coinCurrent = timelyInfoService.get(dateCurrent, exchange);
			TimelyInfoVo coinBefore = timelyInfoService.get(dateBefore, exchange);
			telegramBot.sendTimelyMessage(clients, coinCurrent, coinBefore, timeLoop);
		}
	}
}
