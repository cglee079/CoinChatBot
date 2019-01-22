package com.cglee079.coinchatbot.coin;

import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.exception.ServerErrorException;

public class PoloniexPooler extends ApiPooler{
	private JSONObject coinObjs;
	private String errMessage = "";
	
	public PoloniexPooler() {
		getCoins();
	}

	public JSONObject getCoin(Coin coin) throws ServerErrorException {
		String param = coinParam.get(coin);
		
		if(coinObjs != null) {
			JSONObject coinObj = coinObjs.getJSONObject(param);
			JSONObject newCoinObj = new JSONObject();
			newCoinObj.put("last", coinObj.getDouble("last"));
			newCoinObj.put("first", coinObj.getDouble("last") / ( 1 + coinObj.getDouble("percentChange")));
			newCoinObj.put("high", coinObj.getDouble("high24hr"));
			newCoinObj.put("low", coinObj.getDouble("low24hr"));
			newCoinObj.put("volume", coinObj.getDouble("quoteVolume"));
			newCoinObj.put("errorCode", 0);
			newCoinObj.put("errorMsg", "");
			newCoinObj.put("result", "success");
			return newCoinObj;
		} else {
			throw new ServerErrorException("Poloniex Server Error : " + errMessage );
		}
		
	}
	
	@Scheduled(cron = "00 0/1 * * * *")
	public void getCoins() {
		String url = "https://poloniex.com/public?command=returnTicker";
		HttpClient httpClient = new HttpClient();
		try {
			coinObjs = new JSONObject(httpClient.get(url));
		} catch (Exception e) {
			coinObjs 	= null;
			errMessage	= e.getMessage();
		}
	}
}
