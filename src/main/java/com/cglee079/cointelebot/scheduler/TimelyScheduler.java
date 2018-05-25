package com.cglee079.cointelebot.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
		
		if(SET.ENABLED_COINONE) { loadTimelyCoin(dateCurrent, ID.MARKET_COINONE); }
		if(SET.ENABLED_BITHUMB) { loadTimelyCoin(dateCurrent, ID.MARKET_BITHUMB); }
		if(SET.ENABLED_UPBIT) { loadTimelyCoin(dateCurrent, ID.MARKET_UPBIT); }
		if(SET.ENABLED_COINNEST) { loadTimelyCoin(dateCurrent, ID.MARKET_COINNEST); }
		if(SET.ENABLED_KORBIT) { loadTimelyCoin(dateCurrent, ID.MARKET_KORBIT); }
		if(SET.ENABLED_BITFINEX) { loadTimelyCoin(dateCurrent, ID.MARKET_BITFINNEX); }
		if(SET.ENABLED_BITTREX) { loadTimelyCoin(dateCurrent, ID.MARKET_BITTREX); }
		if(SET.ENABLED_POLONIEX) { loadTimelyCoin(dateCurrent, ID.MARKET_POLONIEX); }
		if(SET.ENABLED_BINANCE) { loadTimelyCoin(dateCurrent, ID.MARKET_BINANCE); }
		
		/* Send Timely Message */
		SimpleDateFormat formatter = new SimpleDateFormat("HH");
		String hourStr = formatter.format(dateCurrent);
		Integer hour = Integer.valueOf(hourStr);
		for(int timeloop = 1; timeloop <= 12; timeloop++) {
			if(hour % timeloop == 0) {
				sendTimelyInfos(dateCurrent, timeloop);
			}
		}
		
		/* Send Daily Message */
		SimpleDateFormat formatter2 = new SimpleDateFormat("dd");
		String dayStr = formatter2.format(dateCurrent);
		Integer day = Integer.valueOf(dayStr);
		for(int dayloop = 1; dayloop <= 7; dayloop++) {
			if(day % dayloop == 0) {
				sendDailyInfos(dateCurrent, dayloop);
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
	public void sendTimelyInfos(Date dateCurrent, int timeLoop) {
		if(SET.ENABLED_COINONE){ sendTimelyInfo(dateCurrent, ID.MARKET_COINONE, timeLoop); }
		if(SET.ENABLED_BITHUMB){ sendTimelyInfo(dateCurrent, ID.MARKET_BITHUMB, timeLoop); }
		if(SET.ENABLED_UPBIT){ sendTimelyInfo(dateCurrent, ID.MARKET_UPBIT, timeLoop); }
		if(SET.ENABLED_COINNEST){ sendTimelyInfo(dateCurrent, ID.MARKET_COINNEST, timeLoop); }
		if(SET.ENABLED_KORBIT){ sendTimelyInfo(dateCurrent, ID.MARKET_KORBIT, timeLoop); }
		if(SET.ENABLED_BITFINEX){ sendTimelyInfo(dateCurrent, ID.MARKET_BITFINNEX, timeLoop); }
		if(SET.ENABLED_BITTREX){ sendTimelyInfo(dateCurrent, ID.MARKET_BITTREX, timeLoop); }
		if(SET.ENABLED_POLONIEX){ sendTimelyInfo(dateCurrent, ID.MARKET_POLONIEX, timeLoop); }
		if(SET.ENABLED_BINANCE){ sendTimelyInfo(dateCurrent, ID.MARKET_BINANCE, timeLoop); }
	}
	
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
	public void sendDailyInfos(Date dateCurrent, int dayLoop) {
		if(SET.ENABLED_COINONE){ sendDailyInfo(dateCurrent, ID.MARKET_COINONE, dayLoop); }
		if(SET.ENABLED_BITHUMB){ sendDailyInfo(dateCurrent, ID.MARKET_BITHUMB, dayLoop); }
		if(SET.ENABLED_UPBIT){ sendDailyInfo(dateCurrent, ID.MARKET_UPBIT, dayLoop); }
		if(SET.ENABLED_COINNEST){ sendDailyInfo(dateCurrent, ID.MARKET_COINNEST, dayLoop); }
		if(SET.ENABLED_KORBIT){ sendDailyInfo(dateCurrent, ID.MARKET_KORBIT, dayLoop); }
		if(SET.ENABLED_BITFINEX){ sendDailyInfo(dateCurrent, ID.MARKET_BITFINNEX, dayLoop); }
		if(SET.ENABLED_BITTREX){ sendDailyInfo(dateCurrent, ID.MARKET_BITTREX, dayLoop); }
		if(SET.ENABLED_POLONIEX){ sendDailyInfo(dateCurrent, ID.MARKET_POLONIEX, dayLoop); }
		if(SET.ENABLED_BINANCE){ sendDailyInfo(dateCurrent, ID.MARKET_BINANCE, dayLoop); }
	}
	
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
