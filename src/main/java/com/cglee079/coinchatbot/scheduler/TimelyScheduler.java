package com.cglee079.coinchatbot.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cglee079.coinchatbot.coin.CoinManager;
import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.config.id.Market;
import com.cglee079.coinchatbot.exception.ServerErrorException;
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
	
	private Coin myCoinId;
	private TelegramBot telegramBot;
	private HashMap<Market, Boolean> inBtcs;
	private List<Market> enabledMarketIds;
	
	public TimelyScheduler(Coin myCoinId, TelegramBot telegramBot) {
		this.myCoinId 		= myCoinId;
		this.telegramBot	= telegramBot;
	}
	
	@PostConstruct
	public void init() {
		List<CoinMarketConfigVo> configMarkets = coinMarketConfigService.list(myCoinId);
		CoinMarketConfigVo configMarket;
		
		inBtcs 			= new HashMap<>();
		enabledMarketIds	= new ArrayList<Market>();
		
		for(int i = 0; i < configMarkets.size(); i++) {
			configMarket = configMarkets.get(i);
			inBtcs.put(configMarket.getMarketId(), configMarket.isInBtc());
			enabledMarketIds.add(configMarket.getMarketId());
		}
	}
	
	/* 매 시간 주기로 코인 가격정보를 가져옴  -> DB 삽입*/
	@Scheduled(cron = "02 00 0/1 * * *")
	public void loadTimelyCoins(){
		Date dateCurrent = new Date();
		
		for(int i = 0; i < enabledMarketIds.size(); i++) {
			loadTimelyCoin(dateCurrent, enabledMarketIds.get(i));
		}
		
		/* 사용자에게 시간 알림 메시지 전송 */
		SimpleDateFormat formatter = new SimpleDateFormat("HH");
		String hourStr = formatter.format(dateCurrent);
		Integer hour = Integer.valueOf(hourStr);
		for(int timeloop = 1; timeloop <= 12; timeloop++) {
			if(hour % timeloop == 0) {
				for(int i = 0; i <enabledMarketIds.size(); i++) {
					sendTimelyInfo(dateCurrent, enabledMarketIds.get(i), timeloop);
				}
			}
		}
		
		/* 사용자에게 일일 알림 메시지 전송 */
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd");
		String dayStr = formatter2.format(dateCurrent);
		Integer day = Integer.valueOf(dayStr);
		for(int dayloop = 1; dayloop <= 7; dayloop++) {
			if(day % dayloop == 0) {
				for(int i = 0; i <enabledMarketIds.size(); i++) {
					sendDailyInfo(dateCurrent, enabledMarketIds.get(i), dayloop);
				}
			}
		}
	}
	
	
	public void loadTimelyCoin(Date dateCurrent, Market marketId) {
		JSONObject coinObj = null;
		try {
			coinObj = coinManager.getCoin(myCoinId, marketId);
		} catch (ServerErrorException e) {
			e.printStackTrace();
			
			coinObj = timelyInfoService.getBefore(myCoinId, dateCurrent, marketId);
			coinObj.put("result", "error");
			coinObj.put("errorCode", e.getErrCode());
			coinObj.put("errorMsg", e.getMessage());
		} finally {
			timelyInfoService.insert(myCoinId, dateCurrent, marketId, coinObj);
		}
	}
	
	/************************/
	/** Timely Send Message **/
	/************************/
	public void sendTimelyInfo(Date dateCurrent, Market marketId, int timeLoop){
		List<ClientVo> clients = clientService.list(myCoinId, marketId, timeLoop, null);
		if(clients.size() > 0) {
			Date dateBefore = null;
			dateBefore = new Date();
			long time = dateCurrent.getTime();
			time = time - (60 * 60 * 1000 * timeLoop);
			dateBefore.setTime(time);
			
			TimelyInfoVo coinCurrent = timelyInfoService.get(myCoinId, dateCurrent, marketId);
			TimelyInfoVo coinBefore = timelyInfoService.get(myCoinId, dateBefore, marketId);
			telegramBot.sendTimelyMessage(clients, marketId, coinCurrent, coinBefore);
		}
	}
	
	/************************/
	/** Daily Send Message **/
	/************************/
	public void sendDailyInfo(Date dateCurrent, Market marketId, int dayLoop) {
		List<ClientVo> clients = clientService.listAtMidnight(myCoinId, marketId, null, dayLoop, dateCurrent);
		if(clients.size() > 0 ) {
			Date dateBefore = null;
			dateBefore = new Date();
			long time = dateCurrent.getTime();
			time = time -(1 * 24 * 60 * 60 * 1000 * dayLoop);
			dateBefore.setTime(time);
			
			TimelyInfoVo coinCurrent  = timelyInfoService.get(myCoinId, dateCurrent, marketId);
			TimelyInfoVo coinBefore = timelyInfoService.get(myCoinId, dateBefore, marketId);
			telegramBot.sendDailyMessage(clients, marketId, coinCurrent, coinBefore);
		}
	}
}
