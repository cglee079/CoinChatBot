package com.cglee079.coinchatbot.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cglee079.coinchatbot.coin.CoinManager;
import com.cglee079.coinchatbot.exception.ServerErrorException;
import com.cglee079.coinchatbot.log.Log;
import com.cglee079.coinchatbot.model.ClientVo;
import com.cglee079.coinchatbot.model.CoinMarketConfigVo;
import com.cglee079.coinchatbot.model.TimelyInfoVo;
import com.cglee079.coinchatbot.service.ClientService;
import com.cglee079.coinchatbot.service.CoinMarketConfigService;
import com.cglee079.coinchatbot.service.TimelyInfoService;
import com.cglee079.coinchatbot.telegram.TelegramBot;

@Component
public class TimelyScheduler {
	@Autowired
	private ClientService clientService;

	@Autowired
	private TimelyInfoService timelyInfoService;
	
	@Autowired
	private CoinManager coinManager;
	
	@Autowired
	private CoinMarketConfigService coinMarketConfigService;
	
	private String myCoin;
	private TelegramBot telegramBot;
	private HashMap<String, Boolean> inBtcs;
	private List<String> enabledMarkets;
	
	public TimelyScheduler(String myCoin, TelegramBot telegramBot) {
		this.myCoin 		= myCoin;
		this.telegramBot	= telegramBot;
	}
	
	@PostConstruct
	public void init() {
		List<CoinMarketConfigVo> configMarkets = coinMarketConfigService.list(myCoin);
		CoinMarketConfigVo configMarket;
		
		inBtcs 			= new HashMap<>();
		enabledMarkets	= new ArrayList<String>();
		
		for(int i = 0; i < configMarkets.size(); i++) {
			configMarket = configMarkets.get(i);
			inBtcs.put(configMarket.getMarketId(), configMarket.isInBtc());
			enabledMarkets.add(configMarket.getMarketId());
		}
	}
	
	@Scheduled(cron = "02 00 0/1 * * *")
	public void loadTimelyCoins(){
		Date dateCurrent = new Date();
		
		for(int i = 0; i < enabledMarkets.size(); i++) {
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
			coinObj = coinManager.getCoin(myCoin, market);
		} catch (ServerErrorException e) {
			Log.i("ERROR loadDailyCoin : " + e.getMessage());
			e.printStackTrace();
			
			coinObj = timelyInfoService.getBefore(myCoin, dateCurrent, market);
			coinObj.put("result", "error");
			coinObj.put("errorCode", e.getErrCode());
			coinObj.put("errorMsg", e.getMessage());
		} finally {
			timelyInfoService.insert(myCoin, dateCurrent, market, coinObj);
		}
	}
	
	/************************/
	/** Timely Send Message **/
	/************************/
	public void sendTimelyInfo(Date dateCurrent, String market, int timeLoop){
		List<ClientVo> clients = clientService.list(myCoin, market, timeLoop, null);
		if(clients.size() > 0) {
			Date dateBefore = null;
			dateBefore = new Date();
			long time = dateCurrent.getTime();
			time = time - (60 * 60 * 1000 * timeLoop);
			dateBefore.setTime(time);
			
			TimelyInfoVo coinCurrent = timelyInfoService.get(myCoin, dateCurrent, market);
			TimelyInfoVo coinBefore = timelyInfoService.get(myCoin, dateBefore, market);
			telegramBot.sendTimelyMessage(clients, market, coinCurrent, coinBefore);
		}
	}
	
	/************************/
	/** Daily Send Message **/
	/************************/
	public void sendDailyInfo(Date dateCurrent, String market, int dayLoop) {
		List<ClientVo> clients = clientService.listAtMidnight(myCoin, market, null, dayLoop, dateCurrent);
		if(clients.size() > 0 ) {
			Date dateBefore = null;
			dateBefore = new Date();
			long time = dateCurrent.getTime();
			time = time -(1 * 24 * 60 * 60 * 1000 * dayLoop);
			dateBefore.setTime(time);
			
			TimelyInfoVo coinCurrent  = timelyInfoService.get(myCoin, dateCurrent, market);
			TimelyInfoVo coinBefore = timelyInfoService.get(myCoin, dateBefore, market);
			telegramBot.sendDailyMessage(clients, market, coinCurrent, coinBefore);
		}
	}
}
