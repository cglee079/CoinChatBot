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
import com.cglee079.cointelebot.model.DailyInfoVo;
import com.cglee079.cointelebot.service.ClientService;
import com.cglee079.cointelebot.service.DailyInfoService;
import com.cglee079.cointelebot.service.TimelyInfoService;
import com.cglee079.cointelebot.telegram.TelegramBot;

public class DailyScheduler {

	@Autowired
	private TelegramBot telegramBot;

	@Autowired
	private ClientService clientService;

	@Autowired
	private DailyInfoService dailyInfoService;
	
	@Autowired
	private TimelyInfoService timelyInfoService;
	
	@Autowired
	private CoinManager coinManager;
	
	@Scheduled(cron = "00 58 23 * * *")
	@Scheduled(cron = "0/10 * * * * *")
	public void loadDailyCoins(){
		Date dateCurrent = new Date();
		
		if(SET.ENABLED_COINONE) { loadDailyCoin(dateCurrent, ID.EXCHANGE_COINONE);}
		if(SET.ENABLED_BITHUMB) { loadDailyCoin(dateCurrent, ID.EXCHANGE_BITHUMB);}
		if(SET.ENABLED_UPBIT) { loadDailyCoin(dateCurrent, ID.EXCHANGE_UPBIT);}
		if(SET.ENABLED_COINNEST) { loadDailyCoin(dateCurrent, ID.EXCHANGE_COINNEST);}
		if(SET.ENABLED_KORBIT) { loadDailyCoin(dateCurrent, ID.EXCHANGE_KORBIT);}
				
		SimpleDateFormat formatter = new SimpleDateFormat("dd");
		String dayStr = formatter.format(dateCurrent);
		Integer day = Integer.valueOf(dayStr);
		for(int i = 1; i <= 7; i++) {
			if(day % i == 0) {
				sendDailyInfos(dateCurrent, i);
			}
		}
	}

	public void loadDailyCoin(Date dateCurrent, String exchange) {
		JSONObject coin = null;
		try {
			coin = coinManager.getCoin(SET.MY_COIN, exchange);
		} catch (ServerErrorException e) {
			Log.i("ERROR loadDailyCoin : " + e.getMessage());
			Log.i(e.getStackTrace());
			
			coin = timelyInfoService.getBefore(dateCurrent, exchange);
			coin.put("result", "error");
			coin.put("errorCode", e.getErrCode());
			coin.put("errorMsg", e.getMessage());
		} finally {
			dailyInfoService.insert(dateCurrent, exchange, coin);
		}
	}
	
	public void sendDailyInfos(Date dateCurrent, int dayLoop) {
		if(SET.ENABLED_COINONE){sendDailyInfo(dateCurrent, ID.EXCHANGE_COINONE, dayLoop);}
		if(SET.ENABLED_BITHUMB){sendDailyInfo(dateCurrent, ID.EXCHANGE_BITHUMB, dayLoop);}
		if(SET.ENABLED_UPBIT){sendDailyInfo(dateCurrent, ID.EXCHANGE_UPBIT, dayLoop);}
		if(SET.ENABLED_COINNEST){sendDailyInfo(dateCurrent, ID.EXCHANGE_COINNEST, dayLoop);}
		if(SET.ENABLED_KORBIT){sendDailyInfo(dateCurrent, ID.EXCHANGE_KORBIT, dayLoop);}
	}
	
	public void sendDailyInfo(Date dateCurrent, String exchange, int dayLoop) {
		List<ClientVo> clients = clientService.list(exchange, null, dayLoop, null);
		if(clients.size() > 0 ) {
			Date dateBefore = null;
			dateBefore = new Date();
			long time = dateCurrent.getTime();
			time = time -(1 * 24 * 60 * 60 * 1000 * dayLoop);
			dateBefore.setTime(time);
			
			DailyInfoVo coinCurrent  = dailyInfoService.get(dateCurrent, exchange);
			DailyInfoVo coinBefore = dailyInfoService.get(dateBefore, exchange);
			telegramBot.sendDailyMessage(clients, coinCurrent, coinBefore, dayLoop);
		}
	}
}
