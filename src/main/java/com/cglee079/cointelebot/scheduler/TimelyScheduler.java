package com.cglee079.cointelebot.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.cointelebot.coin.CoinManager;
import com.cglee079.cointelebot.constants.ID;
import com.cglee079.cointelebot.constants.SET;
import com.cglee079.cointelebot.exception.ServerErrorException;
import com.cglee079.cointelebot.log.Log;
import com.cglee079.cointelebot.model.ClientVo;
import com.cglee079.cointelebot.model.TimelyInfoVo;
import com.cglee079.cointelebot.service.ClientService;
import com.cglee079.cointelebot.service.TimelyInfoService;
import com.cglee079.cointelebot.telegram.TelegramBot;

public class TimelyScheduler {

	private List<String> enabledMarkets;
	
	@Autowired
	private TelegramBot telegramBot;

	@Autowired
	private ClientService clientService;

	@Autowired
	private TimelyInfoService timelyInfoService;
	
	@Autowired
	private CoinManager coinManager;
	
	@PostConstruct
	public void init() {
		enabledMarkets = SET.getEnabledMarkets();
	}
	
	@Scheduled(cron = "10 00 0/1 * * *")
	public void loadTimelyCoins(){
		Date dateCurrent = new Date();
		
		for(int i = 0; i <enabledMarkets.size(); i++) {
			loadTimelyCoin(dateCurrent, enabledMarkets.get(i));
		}
		
		/* Send Timely Message */
		SimpleDateFormat formatter = new SimpleDateFormat("HH");
		String hourStr = formatter.format(dateCurrent);
		Integer hour = Integer.valueOf(hourStr);
		for(int timeloop = 1; timeloop <= 12; timeloop++) {
			if(hour % timeloop == 0) {
				for(int i = 0; i <enabledMarkets.size(); i++) {
					sendTimelyInfo(dateCurrent, enabledMarkets.get(i), timeloop);
				}
			}
		}
		
		/* Send Daily Message */
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd");
		String dayStr = formatter2.format(dateCurrent);
		Integer day = Integer.valueOf(dayStr);
		for(int dayloop = 1; dayloop <= 7; dayloop++) {
			if(day % dayloop == 0) {
				for(int i = 0; i <enabledMarkets.size(); i++) {
					sendDailyInfo(dateCurrent, enabledMarkets.get(i), dayloop);
				}
			}
		}
		
		
	}
	
	public void loadTimelyCoin(Date dateCurrent, String market) {
		JSONObject coinObj = null;
		try {
			coinObj = coinManager.getCoin(SET.MY_COIN, market);
		} catch (ServerErrorException e) {
			Log.i("ERROR loadDailyCoin : " + e.getMessage());
			e.printStackTrace();
			
			coinObj = timelyInfoService.getBefore(dateCurrent, market);
			coinObj.put("result", "error");
			coinObj.put("errorCode", e.getErrCode());
			coinObj.put("errorMsg", e.getMessage());
		} finally {
			timelyInfoService.insert(dateCurrent, market, coinObj);
		}
	}
	
	/************************/
	/** Timely Send Message **/
	/************************/
	public void sendTimelyInfo(Date dateCurrent, String market, int timeLoop){
		List<ClientVo> clients = clientService.list(market, timeLoop, null);
		if(clients.size() > 0) {
			Date dateBefore = null;
			dateBefore = new Date();
			long time = dateCurrent.getTime();
			time = time - (60 * 60 * 1000 * timeLoop);
			dateBefore.setTime(time);
			
			TimelyInfoVo coinCurrent = timelyInfoService.get(dateCurrent, market);
			TimelyInfoVo coinBefore = timelyInfoService.get(dateBefore, market);
			telegramBot.sendTimelyMessage(clients, market, coinCurrent, coinBefore);
		}
	}
	
	/************************/
	/** Daily Send Message **/
	/************************/
	public void sendDailyInfo(Date dateCurrent, String market, int dayLoop) {
		List<ClientVo> clients = clientService.listAtMidnight(market, null, dayLoop, dateCurrent);
		if(clients.size() > 0 ) {
			Date dateBefore = null;
			dateBefore = new Date();
			long time = dateCurrent.getTime();
			time = time -(1 * 24 * 60 * 60 * 1000 * dayLoop);
			dateBefore.setTime(time);
			
			TimelyInfoVo coinCurrent  = timelyInfoService.get(dateCurrent, market);
			TimelyInfoVo coinBefore = timelyInfoService.get(dateBefore, market);
			telegramBot.sendDailyMessage(clients, market, coinCurrent, coinBefore);
		}
	}
}
