package com.cglee079.coinchatbot.coin;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.coinchatbot.config.id.Coin;
import com.cglee079.coinchatbot.exception.ServerErrorException;

public class OkexPooler extends ApiPooler{
	private JSONArray coinObjs;
	private String errMessage;
	
	
	public OkexPooler() throws ServerErrorException {
		getCoins();
	}
	
	public JSONObject getCoin(Coin coin) throws ServerErrorException {
		String param = coinParam.get(coin);
		
		JSONObject coinObj = getCurrentCoin(param);
		
		return coinObj;
	}
	
	public JSONObject getCurrentCoin(String param) throws ServerErrorException {
		try {
			JSONObject coinObj = null; 
			JSONObject newCoinObj 	= new JSONObject();
			for(int i = 0; i < coinObjs.length(); i++) {
				coinObj = coinObjs.getJSONObject(i);
				if(coinObj.getString("product_id").equals(param)) {
					break;
				}
			}
			
			newCoinObj.put("first", coinObj.getDouble("open_24h"));
			newCoinObj.put("last", coinObj.getDouble("last"));
			newCoinObj.put("high", coinObj.getDouble("high_24h"));
			newCoinObj.put("low", coinObj.getDouble("low_24h"));
			newCoinObj.put("volume", coinObj.getDouble("quote_volume_24h"));
			newCoinObj.put("errorCode", 0);
			newCoinObj.put("errorMsg", "");
			newCoinObj.put("result", "success");
			
			return newCoinObj;
		} catch (Exception e) {
			throw new ServerErrorException("OKEx Server Error : " + errMessage);
		}
	}
	
	@Scheduled(cron = "00 0/1 * * * *")
	public void getCoins() throws ServerErrorException {
		String url = "https://www.okex.com/api/spot/v3/instruments/ticker";
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
