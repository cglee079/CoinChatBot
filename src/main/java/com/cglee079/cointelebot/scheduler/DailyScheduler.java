//package com.cglee079.cointelebot.scheduler;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import com.cglee079.cointelebot.coin.CoinManager;
//import com.cglee079.cointelebot.constants.SET;
//import com.cglee079.cointelebot.constants.ID;
//import com.cglee079.cointelebot.exception.ServerErrorException;
//import com.cglee079.cointelebot.log.Log;
//import com.cglee079.cointelebot.model.ClientVo;
//import com.cglee079.cointelebot.model.DailyInfoVo;
//import com.cglee079.cointelebot.service.ClientService;
//import com.cglee079.cointelebot.service.DailyInfoService;
//import com.cglee079.cointelebot.service.TimelyInfoService;
//import com.cglee079.cointelebot.telegram.TelegramBot;
//
//public class DailyScheduler {
//
//	@Autowired
//	private TelegramBot telegramBot;
//
//	@Autowired
//	private ClientService clientService;
//
//	@Autowired
//	private DailyInfoService dailyInfoService;
//	
//	@Autowired
//	private TimelyInfoService timelyInfoService;
//	
//	@Autowired
//	private CoinManager coinManager;
//	
//	@Scheduled(cron = "00 58 23 * * *")
//	public void loadDailyCoins(){
//		Date dateCurrent = new Date();
//		
//		if(SET.ENABLED_COINONE) { loadDailyCoin(dateCurrent, ID.MARKET_COINONE);}
//		if(SET.ENABLED_BITHUMB) { loadDailyCoin(dateCurrent, ID.MARKET_BITHUMB);}
//		if(SET.ENABLED_UPBIT) { loadDailyCoin(dateCurrent, ID.MARKET_UPBIT);}
//		if(SET.ENABLED_COINNEST) { loadDailyCoin(dateCurrent, ID.MARKET_COINNEST);}
//		if(SET.ENABLED_KORBIT) { loadDailyCoin(dateCurrent, ID.MARKET_KORBIT);}
//				
//		SimpleDateFormat formatter = new SimpleDateFormat("dd");
//		String dayStr = formatter.format(dateCurrent);
//		Integer day = Integer.valueOf(dayStr);
//		for(int i = 1; i <= 7; i++) {
//			if(day % i == 0) {
//				sendDailyInfos(dateCurrent, i);
//			}
//		}
//	}
//
//	public void loadDailyCoin(Date dateCurrent, String market) {
//		JSONObject coin = null;
//		try {
//			coin = coinManager.getCoin(SET.MY_COIN, market);
//		} catch (ServerErrorException e) {
//			Log.i("ERROR loadDailyCoin : " + e.getMessage());
//			e.printStackTrace();
//			
//			coin = timelyInfoService.getBefore(dateCurrent, market);
//			coin.put("result", "error");
//			coin.put("errorCode", e.getErrCode());
//			coin.put("errorMsg", e.getMessage());
//		} finally {
//			dailyInfoService.insert(dateCurrent, market, coin);
//		}
//	}
//	
//	
//}
