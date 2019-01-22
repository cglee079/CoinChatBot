package com.cglee079.coinchatbot.coin;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.coinchatbot.constants.ID;
import com.cglee079.coinchatbot.exception.ServerErrorException;

public class BinancePooler extends ApiPooler{
	private JSONArray coinObjs;
	private String errMessage;
	
	public BinancePooler() throws ServerErrorException {
		getCoins();
	}
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		String param = coinParam.get(coin);
		
		if(coinObjs != null) {
			JSONObject coinObj = null; 
			for(int i = 0; i < coinObjs.length(); i++) {
				coinObj = coinObjs.getJSONObject(i);
				if(coinObj.getString("symbol").equals(param)) {
					break;
				}
			}
			JSONObject newCoinObj = new JSONObject();
			newCoinObj.put("volume",  coinObj.getDouble("quoteVolume"));
			newCoinObj.put("first",  coinObj.getDouble("openPrice"));
			newCoinObj.put("last",  coinObj.getDouble("lastPrice"));
			newCoinObj.put("low",  coinObj.getDouble("lowPrice"));
			newCoinObj.put("high",  coinObj.getDouble("highPrice"));
			newCoinObj.put("errorCode", 0);
			newCoinObj.put("errorMsg", "");
			newCoinObj.put("result", "success");
			return newCoinObj;
		} else {
			throw new ServerErrorException("Binance Server Error : " + errMessage);
		}
	}
	
	@Scheduled(cron = "00 0/1 * * * *")
	public void getCoins() throws ServerErrorException {
		String url = "https://api.binance.com/api/v1/ticker/24hr";
		HttpClient httpClient = new HttpClient();
		String response = "";
		try {
			response = httpClient.get(url);
			coinObjs = new JSONArray(response);
		} catch (Exception e) {
			coinObjs 	= null;
			errMessage 	= e.getMessage();
		}
	}
}
