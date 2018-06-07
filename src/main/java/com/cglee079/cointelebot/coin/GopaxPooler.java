package com.cglee079.cointelebot.coin;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;

import com.cglee079.cointelebot.exception.ServerErrorException;

public class GopaxPooler extends ApiPooler{
	private JSONArray coinObjs = null;
	private String errMessage;
	
	public GopaxPooler() throws ServerErrorException {
		this.getCoins();
	}
	
	public JSONObject getCoin(String coin) throws ServerErrorException {
		if(coinObjs != null) {
			JSONObject coinObj = null;
			String param = coinParam.get(coin);
			for(int i = 0; i < coinObjs.length(); i++) {
				coinObj = coinObjs.getJSONObject(i);
				if(coinObj.getString("name").equals(param)) {
					JSONObject newCoinObj = new JSONObject();
					newCoinObj.put("last", coinObj.get("close"));
					newCoinObj.put("first", coinObj.get("open"));
					newCoinObj.put("high", coinObj.get("high"));
					newCoinObj.put("low", coinObj.get("low"));
					newCoinObj.put("volume", coinObj.get("volume"));
					newCoinObj.put("errorCode", 0);
					newCoinObj.put("errorMsg", "");
					newCoinObj.put("result", "success");
					return newCoinObj;
				}
			}
			
			return null;
		} else {
			throw new ServerErrorException("Gopax Server Error : " + errMessage);
		}
	}
	
	@Scheduled(cron = "00 0/1 * * * *")
	public void getCoins() throws ServerErrorException{
		if(coinParam != null) {
			String url = "https://api.gopax.co.kr/trading-pairs/stats";
			HttpClient httpClient = new HttpClient();
			String response;
			try {
				response = httpClient.get(url);
				coinObjs = new JSONArray(response);
			} catch (Exception e) {
				coinObjs 	= null;
				errMessage 	= e.getMessage();
			}
		}
	}
	
}
